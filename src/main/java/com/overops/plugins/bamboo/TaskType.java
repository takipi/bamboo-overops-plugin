package com.overops.plugins.bamboo;

import java.io.PrintWriter;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Properties;


import com.overops.report.service.model.HtmlParts;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.configuration.ConfigurationMap;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.overops.plugins.bamboo.configuration.Const;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.overops.plugins.bamboo.service.impl.BambooPrintWriter;
import com.overops.report.service.QualityReportParams;
import com.overops.report.service.ReportService;
import com.overops.report.service.ReportService.Requestor;
import com.overops.report.service.model.QualityReport;
import com.overops.report.service.model.QualityReport.ReportStatus;

import org.jetbrains.annotations.NotNull;

@Scanned
public class TaskType implements com.atlassian.bamboo.task.TaskType {

    @ComponentImport
    private PluginSettingsFactory pluginSettingsFactory;

    private ReportService overOpsService;
    private ObjectMapper objectMapper;

    public TaskType(PluginSettingsFactory pluginSettingsFactory) {
        this.overOpsService = new ReportService();
        this.objectMapper = new ObjectMapper();
        this.pluginSettingsFactory = pluginSettingsFactory;
    }

    @NotNull
    @Override
    public TaskResult execute(@NotNull TaskContext context) throws TaskException {

        final BuildLogger logger = context.getBuildLogger();

        logger.addBuildLogEntry("Generating OverOps Quality Report");

        PluginSettings globalSettings = pluginSettingsFactory.createGlobalSettings();

        String endPoint = (String)globalSettings.get(Const.GLOBAL_API_URL);
        String apiKey = (String)globalSettings.get(Const.GLOBAL_API_TOKEN);
        
        QualityReportParams query = getQualityReportParams(context.getConfigurationMap());
        String envId = query.getServiceId();
        if (StringUtils.isBlank(envId)) {
            query.setServiceId((String)globalSettings.get(Const.GLOBAL_ENV_ID));
        }

        TaskResultBuilder resultBuilder = TaskResultBuilder.newBuilder(context);

        try {
            logger.addBuildLogEntry("[" + Utils.getArtifactId() + " v" + Utils.getVersion() + "]");

            boolean isDebug = Boolean.parseBoolean(context.getConfigurationMap().get(Const.DEBUG));
            String appUrl = (String)globalSettings.get(Const.GLOBAL_APP_URL);

            Properties props = Utils.getOverOpsProperties();
            PrintStream printStream = null;
            if (isDebug)
            {
                printStream = new BambooPrintWriter(System.out, logger);
                logger.addBuildLogEntry(props.get("com.overops.plugins.bamboo.task.config.apiUrl") + ": " + endPoint);
                logger.addBuildLogEntry(props.get("com.overops.plugins.bamboo.task.config.appUrl") + ": " + appUrl);
                logger.addBuildLogEntry(props.get("com.overops.plugins.bamboo.task.config.envId") + ": " + query.getServiceId());
                logger.addBuildLogEntry(props.get("com.overops.plugins.bamboo.task.config.applicationName") + ": " + query.getApplicationName());
                logger.addBuildLogEntry(props.get("com.overops.plugins.bamboo.task.config.deploymentName") + ": " + query.getDeploymentName());
            }

            Boolean displayLink = Boolean.parseBoolean(context.getConfigurationMap().get(Const.LINK));
            if (displayLink)
            {
                HtmlParts htmlParts = new HtmlParts(overOpsService.generateReportLinkHtml(appUrl, query), "");
                context.getBuildContext().getBuildResult().getCustomBuildData().put("overOpsReport", objectMapper.writeValueAsString(htmlParts));
                context.getBuildContext().getBuildResult().getCustomBuildData().put("isOverOpsStep", "true");
                return resultBuilder.success().build();
            } else
            {
                boolean showAllEvents = Boolean.parseBoolean(context.getConfigurationMap().get(Const.SHOW_ALL_EVENTS));
                ReportService.pauseForTheCause();
                QualityReport reportModel = overOpsService.runQualityReport(endPoint, apiKey, query, Requestor.BAMBOO, printStream, isDebug);

                context.getBuildContext().getBuildResult().getCustomBuildData().put("overOpsReport", objectMapper.writeValueAsString(reportModel.getHtmlParts(showAllEvents)));
                context.getBuildContext().getBuildResult().getCustomBuildData().put("isOverOpsStep", "true");

                if (reportModel.getStatusCode() == ReportStatus.FAILED)
                {
                    if ((reportModel.getExceptionDetails() != null) && Boolean.parseBoolean(context.getConfigurationMap().get(Const.PASS_BUILD_ON_QR_EXCEPTION)))
                    {
                        return resultBuilder.success().build();
                    }
                    return resultBuilder.failed().build();
                }
                else
                {
                    return resultBuilder.success().build();
                }
            }
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
    
            e.printStackTrace(pw);
    
            // stack trace as a string
            logger.addErrorLogEntry(sw.toString());

            return resultBuilder.failed().build();
        }
    }

    private QualityReportParams getQualityReportParams(ConfigurationMap params) {
        QualityReportParams qrp = new QualityReportParams();


        qrp.setServiceId((String)params.get(Const.ENV_ID));
        qrp.setApplicationName(params.get(Const.APP_NAME));
        qrp.setDeploymentName(params.get(Const.DEP_NAME));

        qrp.setRegexFilter(params.get(Const.REGEX_FILTER));
        qrp.setMarkUnstable(Boolean.parseBoolean(params.get(Const.MARK_UNSTABLE)));
        qrp.setPrintTopIssues(NumberUtils.toInt(params.get(Const.TOP_ERROR_COUNT), 0));
        qrp.setShowEventsForPassedGates(Boolean.parseBoolean(params.get(Const.SHOW_ALL_EVENTS)));

        qrp.setNewEvents(Boolean.parseBoolean(params.get(Const.CHECK_NEW_ERRORS)));
        qrp.setResurfacedErrors(Boolean.parseBoolean(params.get(Const.CHECK_RESURFACED_ERRORS)));

        if (Boolean.parseBoolean(params.get(Const.CHECK_VOLUME_ERRORS))) {
            qrp.setMaxErrorVolume(Math.max(1, NumberUtils.toInt(params.get(Const.MAX_ERROR_VOLUME), 1)));
        } else {
            qrp.setMaxErrorVolume(0);
        }

        if (Boolean.parseBoolean(params.get(Const.CHECK_UNIQUE_ERRORS))) {
            qrp.setMaxUniqueErrors(Math.max(1, NumberUtils.toInt(params.get(Const.MAX_UNIQUE_ERRORS), 1)));    
        } else {
            qrp.setMaxUniqueErrors(0);    
        }

        if (Boolean.parseBoolean(params.get(Const.CHECK_CRITICAL_ERRORS))) {
            qrp.setCriticalExceptionTypes(params.getOrDefault(Const.CRITICAL_EXCEPTION_TYPES, ""));
        } else {
            qrp.setCriticalExceptionTypes("");
        }

        qrp.setActiveTimespan("0");
        qrp.setBaselineTimespan("0");
        qrp.setMinVolumeThreshold(0);
        qrp.setMinErrorRateThreshold(0);
        qrp.setRegressionDelta(0);
        qrp.setCriticalRegressionDelta(0);
        qrp.setApplySeasonality(false);

        qrp.setDebug(Boolean.parseBoolean(params.get(Const.DEBUG)));
        return qrp;
    }
}

package com.overops.plugins.bamboo;

import java.io.PrintWriter;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.configuration.ConfigurationMap;
import com.atlassian.bamboo.process.ProcessService;
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

import com.overops.plugins.bamboo.configuration.Const;
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
            PrintStream printStream = isDebug ? new BambooPrintWriter(System.out, logger) : null;

            QualityReport reportModel = overOpsService.runQualityReport(endPoint, apiKey, query, Requestor.BAMBOO, printStream, isDebug);

            context.getBuildContext().getBuildResult().getCustomBuildData().put("overOpsReport", objectMapper.writeValueAsString(reportModel.getHtmlParts()));
            context.getBuildContext().getBuildResult().getCustomBuildData().put("isOverOpsStep", "true");

            if (reportModel.getStatusCode() == ReportStatus.FAILED) {
                return resultBuilder.failed().build();
            } else {
                return resultBuilder.success().build();
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

        qrp.setNewEvents(Boolean.parseBoolean(params.get(Const.CHECK_NEW_ERRORS)));
        qrp.setResurfacedErrors(Boolean.parseBoolean(params.get(Const.CHECK_RESURFACED_ERRORS)));

        qrp.setMaxErrorVolume(NumberUtils.toInt(params.get(Const.MAX_ERROR_VOLUME), 0));
        qrp.setMaxUniqueErrors(NumberUtils.toInt(params.get(Const.MAX_UNIQUE_ERRORS), 0));

        qrp.setCriticalExceptionTypes(params.getOrDefault(Const.CRITICAL_EXCEPTION_TYPES, ""));

        qrp.setActiveTimespan(params.get(Const.ACTIVE_TIMESPAN));
        qrp.setBaselineTimespan(params.get(Const.BASELINE_TIMESPAN));
        qrp.setMinVolumeThreshold(NumberUtils.toInt(params.get(Const.MIN_VOLUME_THRESHOLD), 0));
        qrp.setMinErrorRateThreshold(NumberUtils.toDouble(params.get(Const.MIN_RATE_THRESHOLD), 0));
        qrp.setRegressionDelta(NumberUtils.toDouble(params.get(Const.REGRESSION_DELTA), 0));
        qrp.setCriticalRegressionDelta(NumberUtils.toDouble(params.get(Const.CRITICAL_REGRESSION_THRESHOLD), 0));
        qrp.setApplySeasonality(Boolean.parseBoolean(params.get(Const.APPLY_SEASONALITY)));

        qrp.setDebug(Boolean.parseBoolean(params.get(Const.DEBUG)));
        return qrp;
    }
}

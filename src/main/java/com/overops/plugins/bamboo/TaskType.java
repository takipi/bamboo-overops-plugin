package com.overops.plugins.bamboo;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;

import com.overops.plugins.bamboo.model.OverOpsReportModel;
import com.overops.plugins.bamboo.model.QueryOverOps;
import com.overops.plugins.bamboo.service.OverOpsService;
import com.overops.plugins.bamboo.service.impl.OverOpsServiceImpl;
import com.overops.plugins.bamboo.service.impl.ReportBuilder;
import com.overops.plugins.bamboo.utils.ReportUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.IOException;

// debugging
import static com.overops.plugins.bamboo.configuration.Const.GLOBAL_API_ENV_ID;
import static com.overops.plugins.bamboo.configuration.Const.GLOBAL_API_TOKEN;
import static com.overops.plugins.bamboo.configuration.Const.GLOBAL_API_URL;

@Component
public class TaskType implements com.atlassian.bamboo.task.TaskType {

    private ProcessService processService;
    private OverOpsService overOpsService;
    private ObjectMapper objectMapper;

    public TaskType(final ProcessService processService) {
        this.processService = processService;
        this.overOpsService = new OverOpsServiceImpl();
        this.objectMapper = new ObjectMapper();
    }

    @NotNull
    @Override
    public TaskResult execute(@NotNull TaskContext context) throws TaskException {

        TaskResultBuilder resultBuilder = TaskResultBuilder.newBuilder(context);
        final BuildLogger logger = context.getBuildLogger();
        logger.addBuildLogEntry("Executing OverOps task...");
        logger.addBuildLogEntry("OverOpsBamboo plugin v." + Utils.getVersion());
        logger.addBuildLogEntry("-------------"); // debugging
        logger.addBuildLogEntry(context.getConfigurationMap().get(GLOBAL_API_URL));
        logger.addBuildLogEntry(context.getConfigurationMap().get(GLOBAL_API_ENV_ID));
        logger.addBuildLogEntry(context.getConfigurationMap().get(GLOBAL_API_TOKEN));
        logger.addBuildLogEntry(context.getConfigurationMap().toString());
        logger.addBuildLogEntry("-------------");
        QueryOverOps query = QueryOverOps.mapToObject(context.getConfigurationMap());
        try {
            ReportBuilder.QualityReport report = overOpsService.perform(query, logger);
            OverOpsReportModel overOpsReport = ReportUtils.copyResult(report);
            context.getBuildContext().getBuildResult().getCustomBuildData().put("overOpsReport", objectMapper.writeValueAsString(overOpsReport));
            context.getBuildContext().getBuildResult().getCustomBuildData().put("isOverOpsStep", "true");
            if (overOpsReport.isMarkedUnstable() && overOpsReport.isUnstable()) {
                return resultBuilder.failed().build();
            } else {
                return resultBuilder.success().build();
            }
        } catch (Exception e) {
            logger.addErrorLogEntry(e.getMessage());
            return resultBuilder.failed().build();
        }
    }

}

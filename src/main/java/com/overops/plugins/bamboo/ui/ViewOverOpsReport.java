package com.overops.plugins.bamboo.ui;

import com.atlassian.bamboo.build.PlanResultsAction;
import com.atlassian.bamboo.chains.ChainResultsSummaryImpl;
import com.atlassian.bamboo.chains.ChainStageResult;
import com.atlassian.bamboo.resultsummary.BuildResultsSummary;
import com.atlassian.bamboo.resultsummary.BuildResultsSummaryImpl;
import com.atlassian.bamboo.resultsummary.ResultsSummary;
import com.overops.plugins.bamboo.model.OverOpsReportModel;
import com.overops.plugins.bamboo.utils.Util;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ViewOverOpsReport extends PlanResultsAction {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(ViewOverOpsReport.class);

    private OverOpsReportModel report;

    public String execute() throws Exception {
        String result = super.execute();
        ResultsSummary summary = this.getResultsSummary();
        if (summary instanceof ChainResultsSummaryImpl) {
            ChainResultsSummaryImpl chainResults = (ChainResultsSummaryImpl) summary;
            log.debug("Try to get report link for ChainResultsSummaryImpl");
            List<ChainStageResult> resultList = chainResults.getStageResults();
            for (ChainStageResult chainResult : resultList) {
                Set<BuildResultsSummary> resultSet = chainResult.getBuildResults();
                for (BuildResultsSummary sum : resultSet) {
                    Map<String, String> customBuildData = sum.getCustomBuildData();
                    for (String key : customBuildData.keySet()) {
                        if (key.startsWith("overOpsReport")) {
                            log.debug("Found report link for master =" + key);
                            report = Util.stringToObject(customBuildData.get(key), OverOpsReportModel.class);
                        }
                    }
                }
            }
        } else if (summary instanceof BuildResultsSummaryImpl) {
            BuildResultsSummaryImpl buildResultsSummary = (BuildResultsSummaryImpl) summary;
            log.debug("Try to get report link for BuildResultsSummaryImpl");
            Map<String, String> customBuildData = buildResultsSummary.getCustomBuildData();
            for (String key : customBuildData.keySet()) {
                if (key.startsWith("overOpsReport")) {
                    log.debug("Found report link for master =" + key);
                    report = Util.stringToObject(customBuildData.get(key), OverOpsReportModel.class);
                }
            }
        }

        return result;
    }

    public OverOpsReportModel getReport() {
        return report;
    }

    public void setReport(OverOpsReportModel report) {
        this.report = report;
    }
}

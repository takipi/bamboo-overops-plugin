package com.overops.plugins.bamboo.service;



import com.atlassian.bamboo.build.logger.BuildLogger;
import com.overops.plugins.bamboo.model.QueryOverOps;
import com.overops.plugins.bamboo.service.impl.ReportBuilder;

import java.io.IOException;

public interface OverOpsService {
    ReportBuilder.QualityReport perform(QueryOverOps queryOverOps, BuildLogger logger) throws IOException, InterruptedException;
}

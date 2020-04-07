package com.overops.plugins.bamboo.model;

import com.atlassian.bamboo.configuration.ConfigurationMap;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static com.overops.plugins.bamboo.configuration.Const.*;

public class QueryOverOps {
    private String overOpsURL;
    private String overOpsSID;
    private String overOpsAPIKey;

    private String applicationName;
    private String deploymentName;
    private String serviceId;
    private String regexFilter;
    private boolean markUnstable = false;
    private Integer printTopIssues = 5;
    private boolean newEvents = false;
    private boolean resurfacedErrors = false;
    private Integer maxErrorVolume = 0;
    private Integer maxUniqueErrors = 0;
    private String criticalExceptionTypes;
    private String activeTimespan = "0";
    private String baselineTimespan = "0";
    private Integer minVolumeThreshold = 0;
    private Double minErrorRateThreshold = 0d;
    private Double regressionDelta = 0d;
    private Double criticalRegressionDelta = 0d;
    private boolean applySeasonality = false;

    private boolean debug = false;

    public String getOverOpsURL() {
        return overOpsURL;
    }

    public void setOverOpsURL(String overOpsURL) {
        this.overOpsURL = overOpsURL;
    }

    public String getOverOpsSID() {
        return overOpsSID;
    }

    public void setOverOpsSID(String overOpsSID) {
        this.overOpsSID = overOpsSID;
    }

    public String getOverOpsAPIKey() {
        return overOpsAPIKey;
    }

    public void setOverOpsAPIKey(String overOpsAPIKey) {
        this.overOpsAPIKey = overOpsAPIKey;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getDeploymentName() {
        return deploymentName;
    }

    public void setDeploymentName(String deploymentName) {
        this.deploymentName = deploymentName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getRegexFilter() {
        return regexFilter;
    }

    public void setRegexFilter(String regexFilter) {
        this.regexFilter = regexFilter;
    }

    public boolean isMarkUnstable() {
        return markUnstable;
    }

    public void setMarkUnstable(boolean markUnstable) {
        this.markUnstable = markUnstable;
    }

    public Integer getPrintTopIssues() {
        return printTopIssues;
    }

    public void setPrintTopIssues(Integer printTopIssues) {
        this.printTopIssues = printTopIssues;
    }

    public boolean isNewEvents() {
        return newEvents;
    }

    public void setNewEvents(boolean newEvents) {
        this.newEvents = newEvents;
    }

    public boolean isResurfacedErrors() {
        return resurfacedErrors;
    }

    public void setResurfacedErrors(boolean resurfacedErrors) {
        this.resurfacedErrors = resurfacedErrors;
    }

    public Integer getMaxErrorVolume() {
        return maxErrorVolume;
    }

    public void setMaxErrorVolume(Integer maxErrorVolume) {
        this.maxErrorVolume = maxErrorVolume;
    }

    public Integer getMaxUniqueErrors() {
        return maxUniqueErrors;
    }

    public void setMaxUniqueErrors(Integer maxUniqueErrors) {
        this.maxUniqueErrors = maxUniqueErrors;
    }

    public String getCriticalExceptionTypes() {
        return criticalExceptionTypes;
    }

    public void setCriticalExceptionTypes(String criticalExceptionTypes) {
        this.criticalExceptionTypes = criticalExceptionTypes;
    }

    public String getActiveTimespan() {
        return activeTimespan;
    }

    public void setActiveTimespan(String activeTimespan) {
        this.activeTimespan = activeTimespan;
    }

    public String getBaselineTimespan() {
        return baselineTimespan;
    }

    public void setBaselineTimespan(String baselineTimespan) {
        this.baselineTimespan = baselineTimespan;
    }

    public Integer getMinVolumeThreshold() {
        return minVolumeThreshold;
    }

    public void setMinVolumeThreshold(Integer minVolumeThreshold) {
        this.minVolumeThreshold = minVolumeThreshold;
    }

    public Double getMinErrorRateThreshold() {
        return minErrorRateThreshold;
    }

    public void setMinErrorRateThreshold(Double minErrorRateThreshold) {
        this.minErrorRateThreshold = minErrorRateThreshold;
    }

    public Double getRegressionDelta() {
        return regressionDelta;
    }

    public void setRegressionDelta(Double regressionDelta) {
        this.regressionDelta = regressionDelta;
    }

    public Double getCriticalRegressionDelta() {
        return criticalRegressionDelta;
    }

    public void setCriticalRegressionDelta(Double criticalRegressionDelta) {
        this.criticalRegressionDelta = criticalRegressionDelta;
    }

    public boolean isApplySeasonality() {
        return applySeasonality;
    }

    public void setApplySeasonality(boolean applySeasonality) {
        this.applySeasonality = applySeasonality;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public static QueryOverOps mapToObject(ConfigurationMap params) {
        QueryOverOps queryOverOps = new QueryOverOps();
        queryOverOps.overOpsURL = params.get(GLOBAL_API_URL);
        queryOverOps.overOpsSID = params.get(GLOBAL_API_ENV_ID);
        queryOverOps.overOpsAPIKey = params.get(GLOBAL_API_TOKEN);
        queryOverOps.applicationName = params.get(APP_NAME);
        queryOverOps.deploymentName = params.get(DEP_NAME);

        queryOverOps.serviceId = Optional.ofNullable(params.get(SERVICE_ID)).filter(StringUtils::hasText).orElse(queryOverOps.overOpsSID);
        queryOverOps.regexFilter = Optional.ofNullable(params.get(REGEX_FILTER)).filter(StringUtils::hasText).orElse("");
        queryOverOps.markUnstable = Boolean.parseBoolean(Optional.ofNullable(params.get(MARK_UNSTABLE)).filter(StringUtils::hasText).orElse("false"));
        queryOverOps.printTopIssues = Integer.parseInt(Optional.ofNullable(params.get(TOP_ISSUE)).filter(StringUtils::hasText).orElse("5"));
        queryOverOps.newEvents = Boolean.parseBoolean(Optional.ofNullable(params.get(NEW_EVENTS)).filter(StringUtils::hasText).orElse("false"));
        queryOverOps.resurfacedErrors = Boolean.parseBoolean(Optional.ofNullable(params.get(SURFACED_ERROR)).filter(StringUtils::hasText).orElse("false"));
        queryOverOps.maxErrorVolume = Integer.parseInt(Optional.ofNullable(params.get(NEW_ERROR_VOLUME)).filter(StringUtils::hasText).orElse("0"));
        queryOverOps.maxUniqueErrors = Integer.parseInt(Optional.ofNullable(params.get(MAX_UNIQUE_ERROR)).filter(StringUtils::hasText).orElse("0"));
        queryOverOps.criticalExceptionTypes = params.getOrDefault(CRITICAL_EXCEPTION_TYPE, "");
        queryOverOps.activeTimespan = Optional.ofNullable(params.get(ACTIVE_TIMESPAN)).filter(StringUtils::hasText).orElse("0");
        queryOverOps.baselineTimespan = Optional.ofNullable(params.get(BASELINE_TIMESPAN)).filter(StringUtils::hasText).orElse("0");
        queryOverOps.minVolumeThreshold = Integer.parseInt(Optional.ofNullable(params.get(MIN_VOLUME_THRESHOLD)).filter(StringUtils::hasText).orElse("0"));
        queryOverOps.minErrorRateThreshold = Double.parseDouble(Optional.ofNullable(params.get(MIN_ERROR_RATE_THRESHOLD)).filter(StringUtils::hasText).orElse("0"));
        queryOverOps.regressionDelta = Double.parseDouble(Optional.ofNullable(params.get(REGRESSION_DELTA)).filter(StringUtils::hasText).orElse("0"));
        queryOverOps.criticalRegressionDelta = Double.parseDouble(Optional.ofNullable(params.get(CRITICAL_REGRESSION_DELTA)).filter(StringUtils::hasText).orElse("0"));
        queryOverOps.applySeasonality = Boolean.parseBoolean(Optional.ofNullable(params.get(APPLY_SEASONALITY)).filter(StringUtils::hasText).orElse("false"));
        queryOverOps.debug = Boolean.parseBoolean(Optional.ofNullable(params.get(DEBUG)).filter(StringUtils::hasText).orElse("false"));
        return queryOverOps;
    }

    @Override
    public String toString() {
        return "QueryOverOps{" +
                "overOpsURL='" + overOpsURL + '\'' +
                ", overOpsSID='" + overOpsSID + '\'' +
                ", overOpsAPIKey='" + overOpsAPIKey + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", deploymentName='" + deploymentName + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", regexFilter='" + regexFilter + '\'' +
                ", markUnstable=" + markUnstable +
                ", printTopIssues=" + printTopIssues +
                ", newEvents=" + newEvents +
                ", resurfacedErrors=" + resurfacedErrors +
                ", maxErrorVolume=" + maxErrorVolume +
                ", maxUniqueErrors=" + maxUniqueErrors +
                ", criticalExceptionTypes='" + criticalExceptionTypes + '\'' +
                ", activeTimespan='" + activeTimespan + '\'' +
                ", baselineTimespan='" + baselineTimespan + '\'' +
                ", minVolumeThreshold=" + minVolumeThreshold +
                ", minErrorRateThreshold=" + minErrorRateThreshold +
                ", regressionDelta=" + regressionDelta +
                ", criticalRegressionDelta=" + criticalRegressionDelta +
                ", applySeasonality=" + applySeasonality +
                ", debug=" + debug +
                '}';
    }
}

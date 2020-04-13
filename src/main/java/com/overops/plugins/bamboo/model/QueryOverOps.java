package com.overops.plugins.bamboo.model;

import java.util.Map;
import com.atlassian.bamboo.configuration.ConfigurationMap;
import org.apache.commons.lang3.StringUtils;

import static com.overops.plugins.bamboo.configuration.Const.*;

public class QueryOverOps {
    private String apiUrl;
    private String envId;
    private String apiToken;

    private String applicationName;
    private String deploymentName;
    private String regexFilter;
    private boolean markUnstable;
    private Integer topErrorCount;

    private boolean newEvents;
    private boolean resurfacedErrors;
    private Integer maxErrorVolume;
    private Integer maxUniqueErrors;
    private String criticalExceptionTypes;
    private String activeTimespan;
    private String baselineTimespan;
    private Integer minVolumeThreshold;
    private Double minRateThreshold;
    private Double regressionDelta;
    private Double criticalRegressionThreshold;
    private boolean applySeasonality;

    private boolean debug;

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getEnvId() {
        return envId;
    }

    public void setEnvId(String envId) {
        this.envId = envId.toUpperCase(); // must be S12345, not s12345
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
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

    public Integer getTopErrorCount() {
        return topErrorCount;
    }

    public void setTopErrorCount(Integer topErrorCount) {
        this.topErrorCount = topErrorCount;
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

    public Double getMinRateThreshold() {
        return minRateThreshold;
    }

    public void setMinRateThreshold(Double minRateThreshold) {
        this.minRateThreshold = minRateThreshold;
    }

    public Double getRegressionDelta() {
        return regressionDelta;
    }

    public void setRegressionDelta(Double regressionDelta) {
        this.regressionDelta = regressionDelta;
    }

    public Double getCriticalRegressionThreshold() {
        return criticalRegressionThreshold;
    }

    public void setCriticalRegressionThreshold(Double criticalRegressionThreshold) {
        this.criticalRegressionThreshold = criticalRegressionThreshold;
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

    public static QueryOverOps mapToObject(ConfigurationMap params, Map<String, String> globalSettings) {

        QueryOverOps queryOverOps = new QueryOverOps();

        // api info always comes from global settings page
        queryOverOps.apiUrl = globalSettings.get(GLOBAL_API_URL);
        queryOverOps.apiToken = globalSettings.get(GLOBAL_API_TOKEN);

        // use default env ID from global settings page if missing in the task settings
        queryOverOps.envId = (StringUtils.isBlank(params.get(ENV_ID)) ? globalSettings.get(GLOBAL_ENV_ID) : params.get(ENV_ID));

        queryOverOps.applicationName = params.get(APP_NAME);
        queryOverOps.deploymentName = params.get(DEP_NAME);

        queryOverOps.regexFilter = params.get(REGEX_FILTER);
        queryOverOps.markUnstable = Boolean.parseBoolean(params.get(MARK_UNSTABLE));
        queryOverOps.topErrorCount = Integer.parseInt(params.getOrDefault(TOP_ERROR_COUNT, "0"));

        queryOverOps.newEvents = Boolean.parseBoolean(params.get(CHECK_NEW_ERRORS));
        queryOverOps.resurfacedErrors = Boolean.parseBoolean(params.get(CHECK_RESURFACED_ERRORS));

        queryOverOps.maxErrorVolume = Integer.parseInt(params.getOrDefault(MAX_ERROR_VOLUME, "0"));
        queryOverOps.maxUniqueErrors = Integer.parseInt(params.getOrDefault(MAX_UNIQUE_ERRORS, "0"));

        queryOverOps.criticalExceptionTypes = params.getOrDefault(CRITICAL_EXCEPTION_TYPES, "");

        queryOverOps.activeTimespan = params.get(ACTIVE_TIMESPAN);
        queryOverOps.baselineTimespan = params.get(BASELINE_TIMESPAN);
        queryOverOps.minVolumeThreshold = Integer.parseInt(params.getOrDefault(MIN_VOLUME_THRESHOLD, "0"));
        queryOverOps.minRateThreshold = Double.parseDouble(params.getOrDefault(MIN_RATE_THRESHOLD, "0"));
        queryOverOps.regressionDelta = Double.parseDouble(params.getOrDefault(REGRESSION_DELTA, "0"));
        queryOverOps.criticalRegressionThreshold = Double.parseDouble(params.getOrDefault(CRITICAL_REGRESSION_THRESHOLD, "0"));
        queryOverOps.applySeasonality = Boolean.parseBoolean(params.get(APPLY_SEASONALITY));

        queryOverOps.debug = Boolean.parseBoolean(params.get(DEBUG));
        return queryOverOps;
    }

    @Override
    public String toString() {
        return "QueryOverOps{" +
                "apiUrl='" + apiUrl + '\'' +
                ", envId='" + envId + '\'' +
                ", apiToken='" + apiToken + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", deploymentName='" + deploymentName + '\'' +
                ", regexFilter='" + regexFilter + '\'' +
                ", markUnstable=" + markUnstable +
                ", topErrorCount=" + topErrorCount +
                ", newEvents=" + newEvents +
                ", resurfacedErrors=" + resurfacedErrors +
                ", maxErrorVolume=" + maxErrorVolume +
                ", maxUniqueErrors=" + maxUniqueErrors +
                ", criticalExceptionTypes='" + criticalExceptionTypes + '\'' +
                ", activeTimespan='" + activeTimespan + '\'' +
                ", baselineTimespan='" + baselineTimespan + '\'' +
                ", minVolumeThreshold=" + minVolumeThreshold +
                ", minRateThreshold=" + minRateThreshold +
                ", regressionDelta=" + regressionDelta +
                ", criticalRegressionThreshold=" + criticalRegressionThreshold +
                ", applySeasonality=" + applySeasonality +
                ", debug=" + debug +
                '}';
    }
}

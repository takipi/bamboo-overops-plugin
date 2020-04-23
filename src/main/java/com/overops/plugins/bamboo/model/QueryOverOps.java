package com.overops.plugins.bamboo.model;

import java.util.Map;

import com.atlassian.bamboo.configuration.ConfigurationMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

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

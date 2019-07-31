package com.overops.plugins.bamboo.configuration;

import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.task.AbstractTaskConfigurator;
import com.atlassian.bamboo.task.BuildTaskRequirementSupport;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import org.springframework.util.StringUtils;

import java.util.*;

public class ConfigTask extends AbstractTaskConfigurator implements BuildTaskRequirementSupport {
    PluginSettingsFactory pluginSettingsFactory;

    public ConfigTask(PluginSettingsFactory pluginSettingsFactory) {
        this.pluginSettingsFactory = pluginSettingsFactory;
    }

    /**
     * first create
     */
    public void populateContextForCreate(Map<String, Object> context) {
        super.populateContextForCreate(context);

        PluginSettings pluginSettings = this.pluginSettingsFactory.createGlobalSettings();
        String env = (String) pluginSettings.get(Const.API_ENV);
        String url = (String) pluginSettings.get(Const.API_URL);
        String token = (String) pluginSettings.get(Const.API_TOKEN);
        context.put(Const.API_URL, url);
        context.put(Const.API_TOKEN, token);
        context.put(Const.SERVICE_ID, env);

        context.put(Const.APP_NAME, Const.DEFAULT_JOB_NAME);
        context.put(Const.DEP_NAME, Const.DEFAULT_DEPLOYMENT_NAME);

        context.put(Const.FIELD_CHECK_NEW_ERROR, Const.DEFAULT_CHECK_NEW_ERROR);
        context.put(Const.FIELD_CHECK_RESURFACED_ERRORS, Const.DEFAULT_CHECK_RESURFACED_ERRORS);
        context.put(Const.FIELD_VOLUME_ERRORS, Const.DEFAULT_VOLUME_ERRORS);
        context.put(Const.FIELD_UNIQUE_ERRORS, Const.DEFAULT_UNIQUE_ERRORS);
        context.put(Const.FIELD_CRITICAL_ERRORS, Const.DEFAULT_CRITICAL_ERRORS);
        context.put(Const.FIELD_REGRESSIONS_ERROR, Const.DEFAULT_REGRESSIONS_ERROR);

        context.put(Const.MARK_UNSTABLE, Const.DEFAULT_MARK_UNSTABLE);
        context.put(Const.TOP_ISSUE, Const.DEFAULT_PRINT_TOP_ISSUE);
        context.put(Const.NEW_EVENTS, Const.DEFAULT_NEW_EVENTS);
        context.put(Const.SURFACED_ERROR, Const.DEFAULT_RESURFACED_ERRORS);
        context.put(Const.NEW_ERROR_VOLUME, Const.DEFAULT_MAX_ERROR_VOLUME);
        context.put(Const.MAX_UNIQUE_ERROR, Const.DEFAULT_MAX_UNIQUE_ERRORS);
        context.put(Const.MIN_VOLUME_THRESHOLD, Const.DEFAULT_MIN_VOLUME_THRESHOLD);
        context.put(Const.MIN_ERROR_RATE_THRESHOLD, Const.DEFAULT_MIN_ERROR_RATE_THRESHOLD);
        context.put(Const.REGRESSION_DELTA, Const.DEFAULT_REGRESSION_DELTA);
        context.put(Const.CRITICAL_REGRESSION_DELTA, Const.DEFAULT_CRITICAL_REGRESSION_DELTA);
        context.put(Const.APPLY_SEASONALITY, Const.DEFAULT_APPLY_SEASONALITY);
        context.put(Const.DEBUG, Const.DEFAULT_DEBUG);
    }

    /**
     * from backend to view
     */
    public void populateContextForEdit(Map<String, Object> context, TaskDefinition taskDefinition) {
        super.populateContextForEdit(context, taskDefinition);
        PluginSettings pluginSettings = this.pluginSettingsFactory.createGlobalSettings();
        String env = (String) pluginSettings.get(Const.API_ENV);
        String url = (String) pluginSettings.get(Const.API_URL);
        String token = (String) pluginSettings.get(Const.API_TOKEN);
        Map<String, String> config = taskDefinition.getConfiguration();

        context.put(Const.API_URL, Optional.ofNullable(config.get(Const.API_URL)).filter(StringUtils::hasText).orElse(url));
        context.put(Const.API_TOKEN, Optional.ofNullable(config.get(Const.API_TOKEN)).filter(StringUtils::hasText).orElse(token));

        context.put(Const.APP_NAME, Optional.ofNullable(config.get(Const.APP_NAME)).filter(StringUtils::hasText).orElse(Const.DEFAULT_JOB_NAME));
        context.put(Const.DEP_NAME, Optional.ofNullable(config.get(Const.DEP_NAME)).filter(StringUtils::hasText).orElse(Const.DEFAULT_DEPLOYMENT_NAME));

        context.put(Const.SERVICE_ID, Optional.ofNullable(config.get(Const.SERVICE_ID)).filter(StringUtils::hasText).orElse(env));
        context.put(Const.REGEX_FILTER, config.get(Const.REGEX_FILTER));
        context.put(Const.MARK_UNSTABLE, config.get(Const.MARK_UNSTABLE));
        context.put(Const.TOP_ISSUE, config.get(Const.TOP_ISSUE));
        context.put(Const.NEW_EVENTS, config.get(Const.NEW_EVENTS));
        context.put(Const.SURFACED_ERROR, config.get(Const.SURFACED_ERROR));
        context.put(Const.NEW_ERROR_VOLUME, config.get(Const.NEW_ERROR_VOLUME));
        context.put(Const.MAX_UNIQUE_ERROR, config.get(Const.MAX_UNIQUE_ERROR));
        context.put(Const.CRITICAL_EXCEPTION_TYPE, config.get(Const.CRITICAL_EXCEPTION_TYPE));
        context.put(Const.ACTIVE_TIMESPAN, config.get(Const.ACTIVE_TIMESPAN));
        context.put(Const.BASELINE_TIMESPAN, config.get(Const.BASELINE_TIMESPAN));
        context.put(Const.MIN_VOLUME_THRESHOLD, config.get(Const.MIN_VOLUME_THRESHOLD));
        context.put(Const.MIN_ERROR_RATE_THRESHOLD, config.get(Const.MIN_ERROR_RATE_THRESHOLD));
        context.put(Const.REGRESSION_DELTA, config.get(Const.REGRESSION_DELTA));
        context.put(Const.CRITICAL_REGRESSION_DELTA, config.get(Const.CRITICAL_REGRESSION_DELTA));
        context.put(Const.APPLY_SEASONALITY, config.get(Const.APPLY_SEASONALITY));
        context.put(Const.DEBUG, config.get(Const.DEBUG));

        context.put(Const.FIELD_CHECK_NEW_ERROR, Optional.ofNullable(config.get(Const.FIELD_CHECK_NEW_ERROR)).filter(StringUtils::hasText).orElse(null));
        context.put(Const.FIELD_CHECK_RESURFACED_ERRORS, Optional.ofNullable(config.get(Const.FIELD_CHECK_RESURFACED_ERRORS)).filter(StringUtils::hasText).orElse(null));
        context.put(Const.FIELD_VOLUME_ERRORS, Optional.ofNullable(config.get(Const.FIELD_VOLUME_ERRORS)).filter(StringUtils::hasText).orElse(null));
        context.put(Const.FIELD_UNIQUE_ERRORS, Optional.ofNullable(config.get(Const.FIELD_UNIQUE_ERRORS)).filter(StringUtils::hasText).orElse(null));
        context.put(Const.FIELD_CRITICAL_ERRORS, Optional.ofNullable(config.get(Const.FIELD_CRITICAL_ERRORS)).filter(StringUtils::hasText).orElse(null));
        context.put(Const.FIELD_REGRESSIONS_ERROR, Optional.ofNullable(config.get(Const.FIELD_REGRESSIONS_ERROR)).filter(StringUtils::hasText).orElse(null));
    }

//
//    /**
//     * from gui to backend
//     */
    @Override
    public Map<String, String> generateTaskConfigMap(ActionParametersMap params, TaskDefinition previousTaskDefinition) {
        PluginSettings pluginSettings = this.pluginSettingsFactory.createGlobalSettings();
        final Map<String, String> config = super.generateTaskConfigMap(params, previousTaskDefinition);

        config.put(Const.API_URL, Optional.ofNullable(params.getString(Const.API_URL)).filter(StringUtils::hasText).orElse((String) pluginSettings.get(Const.API_URL)));
        config.put(Const.API_TOKEN, Optional.ofNullable(params.getString(Const.API_TOKEN)).filter(StringUtils::hasText).orElse((String) pluginSettings.get(Const.API_TOKEN)));

        config.put(Const.APP_NAME, Optional.ofNullable(params.getString(Const.APP_NAME)).filter(StringUtils::hasText).orElse(Const.DEFAULT_JOB_NAME));
        config.put(Const.DEP_NAME, Optional.ofNullable(params.getString(Const.DEP_NAME)).filter(StringUtils::hasText).orElse(Const.DEFAULT_DEPLOYMENT_NAME));
        config.put(Const.SERVICE_ID, Optional.ofNullable(params.getString(Const.SERVICE_ID)).filter(StringUtils::hasText).orElse(null));
        config.put(Const.REGEX_FILTER, Optional.ofNullable(params.getString(Const.REGEX_FILTER)).filter(StringUtils::hasText).orElse(null));
        config.put(Const.MARK_UNSTABLE, Optional.ofNullable(params.getString(Const.MARK_UNSTABLE)).filter(StringUtils::hasText).orElse(null));
        config.put(Const.TOP_ISSUE, Optional.ofNullable(params.getString(Const.TOP_ISSUE)).filter(StringUtils::hasText).orElse(null));
        config.put(Const.NEW_EVENTS, Optional.ofNullable(params.getString(Const.NEW_EVENTS)).filter(StringUtils::hasText).orElse(null));
        config.put(Const.SURFACED_ERROR, Optional.ofNullable(params.getString(Const.SURFACED_ERROR)).filter(StringUtils::hasText).orElse(null));
        config.put(Const.NEW_ERROR_VOLUME, Optional.ofNullable(params.getString(Const.NEW_ERROR_VOLUME)).filter(StringUtils::hasText).orElse(null));
        config.put(Const.MAX_UNIQUE_ERROR, Optional.ofNullable(params.getString(Const.MAX_UNIQUE_ERROR)).filter(StringUtils::hasText).orElse(null));
        config.put(Const.CRITICAL_EXCEPTION_TYPE, Optional.ofNullable(params.getString(Const.CRITICAL_EXCEPTION_TYPE)).filter(StringUtils::hasText).orElse(null));
        config.put(Const.ACTIVE_TIMESPAN, Optional.ofNullable(params.getString(Const.ACTIVE_TIMESPAN)).filter(StringUtils::hasText).orElse(null));
        config.put(Const.BASELINE_TIMESPAN, Optional.ofNullable(params.getString(Const.BASELINE_TIMESPAN)).filter(StringUtils::hasText).orElse(null));
        config.put(Const.MIN_VOLUME_THRESHOLD, Optional.ofNullable(params.getString(Const.MIN_VOLUME_THRESHOLD)).filter(StringUtils::hasText).orElse(null));
        config.put(Const.MIN_ERROR_RATE_THRESHOLD, Optional.ofNullable(params.getString(Const.MIN_ERROR_RATE_THRESHOLD)).filter(StringUtils::hasText).orElse(null));
        config.put(Const.REGRESSION_DELTA, Optional.ofNullable(params.getString(Const.REGRESSION_DELTA)).filter(StringUtils::hasText).orElse(null));
        config.put(Const.CRITICAL_REGRESSION_DELTA, Optional.ofNullable(params.getString(Const.CRITICAL_REGRESSION_DELTA)).filter(StringUtils::hasText).orElse(null));
        config.put(Const.APPLY_SEASONALITY, Optional.ofNullable(params.getString(Const.APPLY_SEASONALITY)).filter(StringUtils::hasText).orElse(null));
        config.put(Const.DEBUG, Optional.ofNullable(params.getString(Const.DEBUG)).filter(StringUtils::hasText).orElse(null));


        config.put(Const.FIELD_CHECK_NEW_ERROR, Optional.ofNullable(params.getString(Const.FIELD_CHECK_NEW_ERROR)).filter(StringUtils::hasText).orElse(null));
        config.put(Const.FIELD_CHECK_RESURFACED_ERRORS, Optional.ofNullable(params.getString(Const.FIELD_CHECK_RESURFACED_ERRORS)).filter(StringUtils::hasText).orElse(null));
        config.put(Const.FIELD_VOLUME_ERRORS, Optional.ofNullable(params.getString(Const.FIELD_VOLUME_ERRORS)).filter(StringUtils::hasText).orElse(null));
        config.put(Const.FIELD_UNIQUE_ERRORS, Optional.ofNullable(params.getString(Const.FIELD_UNIQUE_ERRORS)).filter(StringUtils::hasText).orElse(null));
        config.put(Const.FIELD_CRITICAL_ERRORS, Optional.ofNullable(params.getString(Const.FIELD_CRITICAL_ERRORS)).filter(StringUtils::hasText).orElse(null));
        config.put(Const.FIELD_REGRESSIONS_ERROR, Optional.ofNullable(params.getString(Const.FIELD_REGRESSIONS_ERROR)).filter(StringUtils::hasText).orElse(null));


        config.put(Const.API_ENV,
                (String) pluginSettings.get(Const.API_ENV));
        return config;
    }
}

package com.overops.plugins.bamboo.configuration;

import java.util.Map;
import java.util.function.Consumer;

import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.task.AbstractTaskConfigurator;
import com.atlassian.bamboo.task.BuildTaskRequirementSupport;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.utils.error.ErrorCollection;

import org.apache.commons.lang3.StringUtils;


public class TaskConfiguration extends AbstractTaskConfigurator implements BuildTaskRequirementSupport {

    private ActionParametersMap params;
    private ErrorCollection errorCollection;

    /**
     * first create
     */
    public void populateContextForCreate(Map<String, Object> context) {
        super.populateContextForCreate(context);

        // set minimal defaults
        context.put(Const.TOP_ERROR_COUNT, Const.DEFAULT_TOP_ERROR_COUNT);
        context.put(Const.MARK_UNSTABLE, Const.DEFAULT_MARK_UNSTABLE);
    }

    /**
     * from backend to view
     */
    public void populateContextForEdit(Map<String, Object> context, TaskDefinition taskDefinition) {

        super.populateContextForEdit(context, taskDefinition);

        Map<String, String> config = taskDefinition.getConfiguration();

        // general settings
        context.put(Const.ENV_ID, config.get(Const.ENV_ID));
        context.put(Const.APP_NAME, config.get(Const.APP_NAME));
        context.put(Const.DEP_NAME, config.get(Const.DEP_NAME));
        context.put(Const.REGEX_FILTER, config.get(Const.REGEX_FILTER));
        context.put(Const.TOP_ERROR_COUNT, config.get(Const.TOP_ERROR_COUNT));
        context.put(Const.MARK_UNSTABLE, config.get(Const.MARK_UNSTABLE));

        // new and resurfaced quality gates
        context.put(Const.CHECK_NEW_ERRORS, config.get(Const.CHECK_NEW_ERRORS));
        context.put(Const.CHECK_RESURFACED_ERRORS, config.get(Const.CHECK_RESURFACED_ERRORS));

        // total errors quality gate
        context.put(Const.CHECK_VOLUME_ERRORS, config.get(Const.CHECK_VOLUME_ERRORS));
        context.put(Const.MAX_ERROR_VOLUME, config.get(Const.MAX_ERROR_VOLUME));

        // unique errors quality gate
        context.put(Const.CHECK_UNIQUE_ERRORS, config.get(Const.CHECK_UNIQUE_ERRORS));
        context.put(Const.MAX_UNIQUE_ERRORS, config.get(Const.MAX_UNIQUE_ERRORS));

        // critical errors quality gate
        context.put(Const.CHECK_CRITICAL_ERRORS, config.get(Const.CHECK_CRITICAL_ERRORS));
        context.put(Const.CRITICAL_EXCEPTION_TYPES, config.get(Const.CRITICAL_EXCEPTION_TYPES));

        // increasing errors quality gate
        context.put(Const.CHECK_INCREASING_ERRORS, config.get(Const.CHECK_INCREASING_ERRORS));
        context.put(Const.ACTIVE_TIMESPAN, config.get(Const.ACTIVE_TIMESPAN));
        context.put(Const.BASELINE_TIMESPAN, config.get(Const.BASELINE_TIMESPAN));
        context.put(Const.MIN_VOLUME_THRESHOLD, config.get(Const.MIN_VOLUME_THRESHOLD));
        context.put(Const.MIN_RATE_THRESHOLD, config.get(Const.MIN_RATE_THRESHOLD));
        context.put(Const.REGRESSION_DELTA, config.get(Const.REGRESSION_DELTA));
        context.put(Const.CRITICAL_REGRESSION_THRESHOLD, config.get(Const.CRITICAL_REGRESSION_THRESHOLD));
        context.put(Const.APPLY_SEASONALITY, config.get(Const.APPLY_SEASONALITY));

        // advanced settings
        context.put(Const.DEBUG, config.get(Const.DEBUG));
    }

    /**
     * from gui to backend
     */
    @Override
    public Map<String, String> generateTaskConfigMap(ActionParametersMap params, TaskDefinition previousTaskDefinition) {

        final Map<String, String> config = super.generateTaskConfigMap(params, previousTaskDefinition);

        // general settings
        config.put(Const.ENV_ID, params.getString(Const.ENV_ID).toUpperCase()); // s -> S
        config.put(Const.APP_NAME, params.getString(Const.APP_NAME));
        config.put(Const.DEP_NAME, params.getString(Const.DEP_NAME));
        config.put(Const.REGEX_FILTER, params.getString(Const.REGEX_FILTER));
        config.put(Const.TOP_ERROR_COUNT, params.getString(Const.TOP_ERROR_COUNT));
        config.put(Const.MARK_UNSTABLE, params.getString(Const.MARK_UNSTABLE));

        // new and resurfaced quality gates
        config.put(Const.CHECK_NEW_ERRORS, params.getString(Const.CHECK_NEW_ERRORS));
        config.put(Const.CHECK_RESURFACED_ERRORS, params.getString(Const.CHECK_RESURFACED_ERRORS));

        // total errors quality gate
        config.put(Const.CHECK_VOLUME_ERRORS, params.getString(Const.CHECK_VOLUME_ERRORS));
        config.put(Const.MAX_ERROR_VOLUME, params.getString(Const.MAX_ERROR_VOLUME));

        // unique errors quality gate
        config.put(Const.CHECK_UNIQUE_ERRORS, params.getString(Const.CHECK_UNIQUE_ERRORS));
        config.put(Const.MAX_UNIQUE_ERRORS, params.getString(Const.MAX_UNIQUE_ERRORS));

        // critical errors quality gate
        config.put(Const.CHECK_CRITICAL_ERRORS, params.getString(Const.CHECK_CRITICAL_ERRORS));
        config.put(Const.CRITICAL_EXCEPTION_TYPES, params.getString(Const.CRITICAL_EXCEPTION_TYPES));

        // increasing errors quality gate
        config.put(Const.CHECK_INCREASING_ERRORS, params.getString(Const.CHECK_INCREASING_ERRORS));
        config.put(Const.ACTIVE_TIMESPAN, params.getString(Const.ACTIVE_TIMESPAN).toLowerCase()); // D,H,M -> d,h,m
        config.put(Const.BASELINE_TIMESPAN, params.getString(Const.BASELINE_TIMESPAN).toLowerCase());
        config.put(Const.MIN_VOLUME_THRESHOLD, params.getString(Const.MIN_VOLUME_THRESHOLD));
        config.put(Const.MIN_RATE_THRESHOLD, params.getString(Const.MIN_RATE_THRESHOLD));
        config.put(Const.REGRESSION_DELTA, params.getString(Const.REGRESSION_DELTA));
        config.put(Const.CRITICAL_REGRESSION_THRESHOLD, params.getString(Const.CRITICAL_REGRESSION_THRESHOLD));
        config.put(Const.APPLY_SEASONALITY, params.getString(Const.APPLY_SEASONALITY));

        // advanced settings
        config.put(Const.DEBUG, params.getString(Const.DEBUG));

        return config;
    }

    @Override
    public void validate(ActionParametersMap params, ErrorCollection errorCollection) {

        this.params = params;
        this.errorCollection = errorCollection;

        String envId = params.getString(Const.ENV_ID).toUpperCase(); // s -> S

        // valid env id: must be a number starting with "S"; blank is ok
        if (!StringUtils.isBlank(envId) && !envId.matches("[S]\\d+")) {
            errorCollection.addError(Const.ENV_ID, "Invalid environment ID");
        }

        // validate total errors
        validNumber(Const.TOP_ERROR_COUNT);
        validNumber(Const.MAX_ERROR_VOLUME, Const.CHECK_VOLUME_ERRORS);
        validNumber(Const.MAX_UNIQUE_ERRORS, Const.CHECK_UNIQUE_ERRORS);

        // validate critical errors
        validString(Const.CRITICAL_EXCEPTION_TYPES, Const.CHECK_CRITICAL_ERRORS);

        // validate increasing errors
        validTimeWindow(Const.ACTIVE_TIMESPAN, Const.CHECK_INCREASING_ERRORS);
        validTimeWindow(Const.BASELINE_TIMESPAN, Const.CHECK_INCREASING_ERRORS);
        validNumber(Const.MIN_VOLUME_THRESHOLD, Const.CHECK_INCREASING_ERRORS);
        validRange(Const.MIN_RATE_THRESHOLD, Const.CHECK_INCREASING_ERRORS);
        validRange(Const.REGRESSION_DELTA, Const.CHECK_INCREASING_ERRORS);
        validRange(Const.CRITICAL_REGRESSION_THRESHOLD, Const.CHECK_INCREASING_ERRORS);
    }

    // must be a number
    private void validNumber(String key) {
        String value = this.params.getString(key);
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            this.errorCollection.addError(key, "Enter a number");
        }
    }

    // must be a number if related quality gate is enabled
    private void validNumber(String key, String conditionalKey) {
        conditional(key, conditionalKey, validNumber);
    }

    // must not be blank string if related quality gate is enabled
    private void validString(String key, String conditionalKey) {
        conditional(key, conditionalKey, validString);
    }

    // must be 0 or a number followed by d, m, or h if related quality gate is enabled
    private void validTimeWindow(String key, String conditionalKey) {
        conditional(key, conditionalKey, validTimeWindow);
    }

    // must be a number contained in the range [0,1]
    private void validRange(String key, String conditionalKey) {
        conditional(key, conditionalKey, validRange);
    }

    // only validate a field if the quality gate is enabled
    private void conditional(String key, String conditionalKey, Consumer<String> validFunction) {
        // checkbox states are "true" and null
        if (!StringUtils.isBlank(this.params.getString(conditionalKey))) {
            this.params.replace(conditionalKey, "true"); // re-check the conditional's checkbox
            validFunction.accept(key);
        }
    }

    // reuse validateNumber() in conditionals
    private Consumer<String> validNumber = key -> validNumber(key);

    // string must not be blank
    private Consumer<String> validString = key -> {
        String value = this.params.getString(key);

        if (StringUtils.isBlank(value)) {
            this.errorCollection.addError(key, "Enter critical exception types");
        }
    };

    // must be a number followed by d, h or m, or be 0
    private Consumer<String> validTimeWindow = key -> {
        String value = this.params.getString(key).toLowerCase();

        if (StringUtils.isBlank(value) || !value.matches("\\d+[dhm]")) {
            this.errorCollection.addError(key, "Enter time window in days, hours, or minutes");
        }
    };

    // must be a number contained in the range [0,1]
    private Consumer<String> validRange = key -> {
        String value = this.params.getString(key);
        double floor = 0d;
        double ceiling = 1d;

        try {
            double number = Double.parseDouble(value);
            if (number < floor || number > ceiling) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            this.errorCollection.addError(key, "Enter a number between " + floor + " and " + ceiling);
        }
    };
}

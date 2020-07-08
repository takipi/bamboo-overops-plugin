package com.overops.plugins.bamboo.configuration;

public class Const {

    /**
     *  keys for PluginSettingsFactory.createGlobalSettings()
     *  plugins are advised to namespace the key with something unique to the plugin
     *  (for example "com.example.plugin:key-I-would-like-to-use" ) to avoid clashes with other keys
     */

    // PluginSettingsFactory global settings
    public static final String GLOBAL_ENV_ID = "com.overops.plugins.bamboo:global-api-env-id";
    public static final String GLOBAL_API_URL = "com.overops.plugins.bamboo:global-api-url";
    public static final String GLOBAL_APP_URL = "com.overops.plugins.bamboo:global-app-url";
    public static final String GLOBAL_API_TOKEN = "com.overops.plugins.bamboo:global-api-token";


    // config template vars
    public static final String ENV_ID = "envId";
    public static final String APP_NAME = "applicationName";
    public static final String DEP_NAME = "deploymentName";
    public static final String REGEX_FILTER = "regexFilter";
    public static final String TOP_ERROR_COUNT = "topErrorCount";
    public static final String MARK_UNSTABLE = "markUnstable";
    public static final String LINK = "link";
    public static final String SHOW_ALL_EVENTS = "showAllEvents";
    public static final String PASS_BUILD_ON_QR_EXCEPTION = "passOnQRException";


    public static final String CHECK_NEW_ERRORS = "checkNewErrors";
    public static final String CHECK_RESURFACED_ERRORS = "checkResurfacedErrors";

    public static final String CHECK_VOLUME_ERRORS = "checkVolumeErrors";
    public static final String MAX_ERROR_VOLUME = "maxErrorVolume";

    public static final String CHECK_UNIQUE_ERRORS = "checkUniqueErrors";
    public static final String MAX_UNIQUE_ERRORS = "maxUniqueErrors";

    public static final String CHECK_CRITICAL_ERRORS = "checkCriticalErrors";
    public static final String CRITICAL_EXCEPTION_TYPES = "criticalExceptionTypes";

    public static final String DEBUG = "debug";

    // default values
    public static final String DEFAULT_API_URL = "https://api.overops.com/";
    public static final String DEFAULT_APP_URL = "https://app.overops.com/";

    public static final String DEFAULT_TOP_ERROR_COUNT = "10";
    public static final String DEFAULT_MARK_UNSTABLE = "true";
    public static final String DEFAULT_LINK = "false";
}

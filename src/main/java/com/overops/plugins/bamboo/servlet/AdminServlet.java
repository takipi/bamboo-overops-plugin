package com.overops.plugins.bamboo.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atlassian.templaterenderer.TemplateRenderer;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

import com.takipi.api.client.ApiClient;
import com.takipi.api.client.RemoteApiClient;
import com.takipi.api.client.util.client.ClientUtil;
import com.takipi.api.client.data.service.SummarizedService;

import com.overops.plugins.bamboo.configuration.Const;

import org.apache.commons.lang3.StringUtils;

@Scanned
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // velocity variables
    private enum VM {
        template("default-settings.vm"),
        envId("envId"),
        apiUrl("apiUrl"),
        apiToken("apiToken"),
        save("save"),
        testConnection("testConnection"),
        isError("isError"),
        isSuccess("isSuccess"),
        message("message");

        private final String var;

        VM(String var) {
            this.var = var;
        }

        public String get() {
            return var;
        }

    }

    @ComponentImport
    private final PluginSettingsFactory pluginSettingsFactory;

    @ComponentImport
    private final TemplateRenderer renderer;

    public AdminServlet(PluginSettingsFactory pluginSettingsFactory, TemplateRenderer renderer) {
        this.pluginSettingsFactory = pluginSettingsFactory;
        this.renderer = renderer;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // get/set global plugin settings
        PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();

        // velocity template context
        Map<String, Object> context = new HashMap<String, Object>();

        // API URL: if blank, set default value
        String globalApiUrl = (String) pluginSettings.get(Const.GLOBAL_API_URL);
        String url = StringUtils.isBlank(globalApiUrl) ? Const.DEFAULT_API_URL : globalApiUrl;

        // ENV, TOKEN: blank is default value (replace null, whitespace with blank)
        String globalEnvId = (String) pluginSettings.get(Const.GLOBAL_ENV_ID);
        String env = StringUtils.isBlank(globalEnvId) ? "" : globalEnvId;

        String globalApiToken = (String) pluginSettings.get(Const.GLOBAL_API_TOKEN);
        String token = StringUtils.isBlank(globalApiToken) ? "" : globalApiToken;

        // set velocity context
        context.put(VM.apiUrl.get(), url);
        context.put(VM.envId.get(), env);
        context.put(VM.apiToken.get(), token);

        // render page
        resp.setContentType("text/html;charset=utf-8");
        renderer.render(VM.template.get(), context, resp.getWriter());
    }

    @Override
    protected void doPost(final HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // velocity template context
        Map<String, Object> context = new HashMap<String, Object>();

        // populate values from form
        String env = req.getParameter(VM.envId.get()).trim().toUpperCase();
        String url = req.getParameter(VM.apiUrl.get()).trim();
        String token = req.getParameter(VM.apiToken.get()).trim();

        // check if 'save' or 'test connection' was submitted
        boolean isSave = StringUtils.isNotBlank(req.getParameter(VM.save.get()));
        boolean isTestConnection = StringUtils.isNotBlank(req.getParameter(VM.testConnection.get()));

        if (isSave) {

            // save form
            PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();
            pluginSettings.put(Const.GLOBAL_ENV_ID, env);
            pluginSettings.put(Const.GLOBAL_API_URL, url);
            pluginSettings.put(Const.GLOBAL_API_TOKEN, token);

            context.put(VM.isSuccess.get(), "true");
            context.put(VM.message.get(), "Settings saved");

        } else if (isTestConnection) {

            if (StringUtils.isBlank(url)) {

                // URL can't be blank
                context.put(VM.isError.get(), "true");
                context.put(VM.message.get(), "Unable to connect. API URL cannot be blank.");

            } else if (StringUtils.isBlank(token)) {

                // API Token can't be blank
                context.put(VM.isError.get(), "true");
                context.put(VM.message.get(), "Unable to connect. API Token cannot be blank.");

            } else {

                // test credentials
                RemoteApiClient apiClient =
                    (RemoteApiClient) RemoteApiClient.newBuilder()
                    .setHostname(url)
                    .setApiKey(token)
                    .build();

                if (!apiClient.validateConnection()) {
                    // error - can't connect to host
                    context.put(VM.isError.get(), "true");
                    context.put(VM.message.get(), "Unable to connect. Check API URL.");
                } else if (!hasAccessToEnvironment(apiClient, env)) {
                    // error - no access to env
                    context.put(VM.isError.get(), "true");
                    context.put(VM.message.get(), "Permission denied. Check Environment ID and API Token.");
                } else {
                    // success
                    context.put(VM.isSuccess.get(), "true");
                    context.put(VM.message.get(), "Connection successful. Click 'save' to save settings.");
                }
            }
        }

        // set velocity context
        context.put(VM.apiUrl.get(), url);
        context.put(VM.envId.get(), env);
        context.put(VM.apiToken.get(), token);

        // render template
        resp.setContentType("text/html;charset=utf-8");
        renderer.render(VM.template.get(), context, resp.getWriter());
    }

    private static boolean hasAccessToEnvironment(ApiClient apiClient, String envId) {

        List<SummarizedService> environments;

        try {
            environments = ClientUtil.getEnvironments(apiClient);
        } catch (Exception e) {
            return false;
        }

        for (SummarizedService env : environments) {
            if (env.id.equals(envId)) {
                return true;
            }
        }

        return false;

    }

}

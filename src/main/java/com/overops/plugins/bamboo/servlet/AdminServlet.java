package com.overops.plugins.bamboo.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atlassian.templaterenderer.TemplateRenderer;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.atlassian.sal.api.transaction.TransactionCallback;

import com.overops.plugins.bamboo.model.TestServiceResponse;
import com.overops.plugins.bamboo.service.SettingService;
import com.overops.plugins.bamboo.service.impl.SettingServiceImpl;
import com.overops.plugins.bamboo.configuration.Const;

import org.springframework.util.StringUtils;

public class AdminServlet extends HttpServlet {
    public static final String OVEROPS_ADMIN_VM = "overopsadmin.vm";
    private static final long serialVersionUID = 1L;

    private final TransactionTemplate transactionTemplate;
    private final PluginSettingsFactory pluginSettingsFactory;
    private final TemplateRenderer renderer;

    private SettingService settingService;

    public AdminServlet(PluginSettingsFactory pluginSettingsFactory, TemplateRenderer renderer, TransactionTemplate transactionTemplate) {
        this.pluginSettingsFactory = pluginSettingsFactory;
        this.renderer = renderer;
        this.transactionTemplate = transactionTemplate;
        this.settingService = new SettingServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // get/set global plugin settings
        PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();

        // velocity template context
        Map<String, Object> context = new HashMap<String, Object>();

        // if blank, set default value
        String url = Optional.ofNullable(pluginSettings.get(Const.GLOBAL_API_URL)).map(u -> (String) u).filter(StringUtils::hasLength).map(String::trim)
                .orElse(Const.DEFAULT_API_URL);

        context.put("url", url);

        String env = Optional.ofNullable(pluginSettings.get(Const.GLOBAL_API_ENV_ID)).map(e -> (String) e).map(String::trim).orElse("");

        context.put(Const.GLOBAL_API_ENV_ID, env);

        String token = Optional.ofNullable(pluginSettings.get(Const.GLOBAL_API_TOKEN)).map(t -> (String) t).map(String::trim).orElse("");

        context.put(Const.GLOBAL_API_TOKEN, token);

        context.put(Const.ERROR, "");
        context.put(Const.INFO, "");
        context.put("newEventsGate", false);

        resp.setContentType("text/html;charset=utf-8");
        renderer.render(OVEROPS_ADMIN_VM, context, resp.getWriter());
    }

    @Override
    protected void doPost(final HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> context = new HashMap<String, Object>();
        resp.setContentType("text/html;charset=utf-8");

        String url = req.getParameter(Const.GLOBAL_API_URL).trim();
        String env = req.getParameter(Const.GLOBAL_API_ENV_ID).trim();
        String token = req.getParameter(Const.GLOBAL_API_TOKEN).trim();

        context.put(Const.GLOBAL_API_URL, url);
        context.put(Const.GLOBAL_API_ENV_ID, env);
        context.put(Const.GLOBAL_API_TOKEN, token);

        try {
            TestServiceResponse response = settingService.testConnection(url, env, token);

            if (response.isStatus()) {
                transactionTemplate.execute((TransactionCallback) () -> {
                    PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();
                    pluginSettings.put(Const.GLOBAL_API_URL, url);
                    pluginSettings.put(Const.GLOBAL_API_ENV_ID, env);
                    pluginSettings.put(Const.GLOBAL_API_TOKEN, token);
                    return pluginSettings;
                });
                context.put(Const.INFO, "OverOps settings are updated. Check that jobs are configured properly");
                context.put(Const.ERROR, "");
            } else {
                context.put(Const.ERROR, response.getMessage());
                context.put(Const.INFO, "");
            }
        } catch (Exception e) {
            context.put(Const.ERROR, "OverOps settings were not saved! Check server url, environment id or token.");
            context.put(Const.INFO, "");
        } finally {
            renderer.render(OVEROPS_ADMIN_VM, context, resp.getWriter());
        }
    }
}

package com.overops.plugins.bamboo.servlet;

import com.atlassian.sal.api.transaction.TransactionCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.templaterenderer.TemplateRenderer;
import com.atlassian.sal.api.transaction.TransactionTemplate;

import com.overops.plugins.bamboo.model.TestServiceResponse;
import com.overops.plugins.bamboo.service.SettingService;
import com.overops.plugins.bamboo.service.impl.SettingServiceImpl;
import com.overops.plugins.bamboo.configuration.Const;
import org.springframework.util.StringUtils;

public class AdminServlet extends HttpServlet {
public static final String OVEROPS_ADMIN_VM = "overopsadmin.vm";
    private final TransactionTemplate transactionTemplate;
    private final PluginSettingsFactory pluginSettingsFactory;
    private static final long serialVersionUID = 1L;
    private final String URL_REGEX = "^https://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    private final TemplateRenderer renderer;
    private SettingService settingService;


    public AdminServlet(PluginSettingsFactory pluginSettingsFactory, TemplateRenderer renderer,
                        TransactionTemplate transactionTemplate) {
        this.pluginSettingsFactory = pluginSettingsFactory;
        this.renderer = renderer;
        this.transactionTemplate = transactionTemplate;
        this.settingService = new SettingServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> context = new HashMap<String, Object>();
        resp.setContentType("text/html;charset=utf-8");

        PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();
        String url = Optional.ofNullable(pluginSettings.get(Const.API_URL)).map(u -> (String) u).filter(StringUtils::hasLength).map(String::trim)
                .orElse(Const.DEFAULT_URL);
        context.put(Const.API_URL, url.trim());

        String env = Optional.ofNullable(pluginSettings.get(Const.API_ENV)).map(e -> (String) e).map(String::trim).orElse("");

        context.put(Const.API_ENV, env);

        String token = Optional.ofNullable(pluginSettings.get(Const.API_TOKEN)).map(t -> (String) t).map(String::trim).orElse("");
        context.put(Const.API_TOKEN, token);
        context.put(Const.ERROR, "");
        context.put(Const.INFO, "");
        context.put("newEventsGate", false);
        renderer.render(OVEROPS_ADMIN_VM, context, resp.getWriter());
    }

    @Override
    protected void doPost(final HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> context = new HashMap<String, Object>();
        resp.setContentType("text/html;charset=utf-8");

        String url = req.getParameter(Const.API_URL).trim().replaceAll("/+$", "");
        String env = req.getParameter(Const.API_ENV).trim();
        String token = req.getParameter(Const.API_TOKEN).trim();

        context.put(Const.API_URL, url);
        context.put(Const.API_ENV, env);
        context.put(Const.API_TOKEN, token);

        if (!url.matches(URL_REGEX)) {
            context.put(Const.ERROR, "OverOps url is not saved! Invalid format!");
            context.put(Const.INFO, "");

            renderer.render(OVEROPS_ADMIN_VM, context, resp.getWriter());
            return;
        }

        try {
            TestServiceResponse response = settingService.testConnection(url, env, token);

            if (response.isStatus()) {
                transactionTemplate.execute((TransactionCallback) () -> {
                    PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();
                    pluginSettings.put(Const.API_URL, url);
                    pluginSettings.put(Const.API_ENV, env);
                    pluginSettings.put(Const.API_TOKEN, token);
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

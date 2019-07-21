package com.overops.plugins.bamboo;

import com.atlassian.bamboo.chains.StageExecution;
import com.atlassian.bamboo.chains.plugins.PreJobAction;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.v2.build.BuildContext;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.overops.plugins.bamboo.configuration.Const;

import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import static com.overops.plugins.bamboo.configuration.Const.API_ENV;
import static com.overops.plugins.bamboo.configuration.Const.API_TOKEN;
import static com.overops.plugins.bamboo.configuration.Const.API_URL;

public class SettingsProcessor implements PreJobAction {

    PluginSettingsFactory pluginSettingsFactory;

    public SettingsProcessor(PluginSettingsFactory pluginSettingsFactory) {
        this.pluginSettingsFactory = pluginSettingsFactory;
    }

    @Override
    public void execute(@NotNull final StageExecution stageExecution, @NotNull final BuildContext buildContext) {
        PluginSettings pluginSettings = this.pluginSettingsFactory.createGlobalSettings();
        String url = (String) pluginSettings.get(API_URL);
        String env = (String) pluginSettings.get(API_ENV);
        String token = (String) pluginSettings.get(API_TOKEN);
        buildContext.getBuildDefinition().getTaskDefinitions().get(0).getPluginKey();
        List<TaskDefinition> tds = buildContext.getBuildDefinition().getTaskDefinitions();
        for (TaskDefinition d : tds) {
            if (d.getPluginKey().equals(Const.PLUGIN_KEY)) {
                Map<String, String> conf = d.getConfiguration();
                conf.put(API_URL, url);
                conf.put(API_ENV, env);
                conf.put(API_TOKEN, token);
            }
        }
    }
}

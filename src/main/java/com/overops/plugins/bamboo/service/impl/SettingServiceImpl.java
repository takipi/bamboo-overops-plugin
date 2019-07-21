package com.overops.plugins.bamboo.service.impl;

import com.overops.plugins.bamboo.model.TestServiceResponse;
import com.overops.plugins.bamboo.service.SettingService;
import com.takipi.api.client.ApiClient;
import com.takipi.api.client.RemoteApiClient;
import com.takipi.api.client.data.service.SummarizedService;
import com.takipi.api.client.util.client.ClientUtil;
import com.takipi.api.core.url.UrlClient;

import java.util.List;

public class SettingServiceImpl implements SettingService {
    @Override
    public TestServiceResponse testConnection(String url, String env, String token) {
        try {
            RemoteApiClient apiClient = (RemoteApiClient) RemoteApiClient.newBuilder().setHostname(url).setApiKey(token).build();

            UrlClient.Response<String> response = apiClient.testConnection();

            boolean testConnection = (response == null) || (response.isBadResponse());
            boolean testService = ((env == null) || (hasAccessToService(apiClient, env)));

            if (testConnection) {
                int code;

                if (response != null) {
                    code = response.responseCode;
                } else {
                    code = -1;
                }

                return TestServiceResponse.fail("Unable to connect to API server. Code: " + code);
            }

            if (!testService) {
                return TestServiceResponse.fail("API key has no access to environment " + env);
            }

            return TestServiceResponse.ok("Connection Successful.");
        } catch (Exception e) {
            return TestServiceResponse.fail("REST API error : " + e.getMessage());
        }
    }


    private boolean hasAccessToService(ApiClient apiClient, String serviceId) {

        List<SummarizedService> services;

        try {
            services = ClientUtil.getEnvironments(apiClient);
        } catch (Exception e) {
            return false;
        }


        for (SummarizedService service : services) {
            if (service.id.equals(serviceId)) {
                return true;
            }
        }

        return false;

    }
}

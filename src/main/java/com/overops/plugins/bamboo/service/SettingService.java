package com.overops.plugins.bamboo.service;


import com.overops.plugins.bamboo.model.TestServiceResponse;

public interface SettingService {
    TestServiceResponse testConnection(String url, String env, String token);
}

package com.tradiumapp.swingtradealerts.scheduledtasks;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class IexCloudServiceConfig {

    @Bean
    public IexCloudService iexcloudService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cloud.iexapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(IexCloudService.class);
    }
}

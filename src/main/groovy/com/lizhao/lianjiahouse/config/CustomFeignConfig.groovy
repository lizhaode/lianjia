package com.lizhao.lianjiahouse.config

import feign.okhttp.OkHttpClient
import groovy.util.logging.Slf4j
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.context.annotation.Bean

@Slf4j
class CustomFeignConfig {

    @Bean
    OkHttpClient feignClientConfig() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            void log(String s) {
                log.info(s)
            }
        })
        interceptor.level(HttpLoggingInterceptor.Level.BODY)
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder().addInterceptor(interceptor).build()
        return new OkHttpClient(client)
    }
}

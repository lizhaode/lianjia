package com.lizhao.lianjiahouse.config

import feign.Logger
import org.springframework.context.annotation.Bean

class CustomFeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL
    }
}

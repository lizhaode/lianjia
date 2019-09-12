package com.lizhao.lianjiahouse

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class LianjiahouseApplication {

    static void main(String[] args) {
        SpringApplication.run(LianjiahouseApplication, args)
    }

}

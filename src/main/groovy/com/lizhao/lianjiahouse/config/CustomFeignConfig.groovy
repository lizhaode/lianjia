package com.lizhao.lianjiahouse.config

import feign.okhttp.OkHttpClient
import groovy.util.logging.Slf4j
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.annotations.NotNull
import org.springframework.context.annotation.Bean

import java.nio.charset.StandardCharsets

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
        interceptor.level(HttpLoggingInterceptor.Level.NONE)
        HeaderInterceptor header = new HeaderInterceptor()
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder().addInterceptor(header).addNetworkInterceptor(interceptor).build()
        return new OkHttpClient(client)
    }

    class HeaderInterceptor implements Interceptor {

        @Override
        Response intercept(@NotNull Chain chain) throws IOException {
            Request request = chain.request()
            String url = new URLDecoder().decode(request.url().url, StandardCharsets.UTF_8.name())
            Request newReq = request.newBuilder()
                    .addHeader('User-Agent', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36')
                    .addHeader('Accept-Language', 'zh-CN,zh;q=0.9')
                    .addHeader('X-Forwarded-For','224.50.16.43').url(url).build()
            return chain.proceed(newReq)
        }
    }
}

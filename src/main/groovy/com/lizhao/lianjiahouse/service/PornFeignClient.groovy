package com.lizhao.lianjiahouse.service

import com.lizhao.lianjiahouse.config.CustomFeignConfig
import feign.Response
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = '91Porn', url = 'http://91porn.com', configuration = CustomFeignConfig)
interface PornFeignClient {

    @GetMapping('/v.php')
    Response hottestCurMonth(@RequestParam('page') int page
                             , @RequestParam(value = 'viewtype', defaultValue = 'basic', required = false) String view
                             , @RequestParam(value = 'category', required = false, defaultValue = 'top') String cate)

    @GetMapping('/{url}')
    Response videoPage(@PathVariable('url') String url)
}
package com.lizhao.lianjiahouse.service

import com.lizhao.lianjiahouse.config.CustomFeignConfig
import com.lizhao.lianjiahouse.entity.dto.LianjiaHttpResp
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = 'Lianjia',url = 'https://tj.fang.lianjia.com',configuration = CustomFeignConfig)
interface LianjiaFeignClient {

    @GetMapping(value = '/loupan/nht1nhs1/?_t=1')
    LianjiaHttpResp forSaleHouseStart()

    @GetMapping(value = "/loupan/nht1nhs1pg{page}/?_t=1")
    LianjiaHttpResp forSaleHousePage(@PathVariable('page') String page)
}
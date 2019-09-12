package com.lizhao.lianjiahouse.controller

import com.lizhao.lianjiahouse.entity.vo.ResponseBean
import com.lizhao.lianjiahouse.service.ForSaleHouseParse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('spider_start')
class SpiderStart {

    @Autowired
    private ForSaleHouseParse parse

    @GetMapping('/start')
    ResponseBean startUrl() {
        parse.poolingSaveData()
        return ResponseBean.success()
    }
}

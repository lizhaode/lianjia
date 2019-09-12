package com.lizhao.lianjiahouse.entity.dto

import groovy.transform.ToString

@ToString(includeNames = true)
class LianjiaHttpResp {
    int errno

    String error

    ForSaleHouseData data
}

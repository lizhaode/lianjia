package com.lizhao.lianjiahouse.entity.dao

import groovy.transform.ToString

import java.time.LocalDateTime

@ToString(includeNames = true)
class ForSaleHouseDao {

    int id

    String houseName

    String areaScope

    String roomScope

    String district

    String street

    String address

    int price

    int totalPrice

    String totalPriceUnit

    String url

    LocalDateTime createTimestamp

    LocalDateTime updateTimestamp
}

package com.lizhao.lianjiahouse.entity.dao

import groovy.transform.ToString

import java.time.LocalDateTime

@ToString(includeNames = true)
class ForSaleHouseDao {

    int id

    String house_name

    String area_scope

    String room_scope

    String district

    String street

    String address

    int price

    int total_price

    String total_price_unit

    String url

    LocalDateTime create_timestamp

    LocalDateTime update_timestamp
}

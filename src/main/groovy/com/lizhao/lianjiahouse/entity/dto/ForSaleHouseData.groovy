package com.lizhao.lianjiahouse.entity.dto

import groovy.transform.ToString

@ToString(includeNames = true)
class ForSaleHouseData {

//    String fb_query_id

    int no_more_data

    List<HouseList> list

    String total
}

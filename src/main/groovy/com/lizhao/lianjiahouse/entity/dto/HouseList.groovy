package com.lizhao.lianjiahouse.entity.dto

import groovy.transform.ToString

@ToString(includeNames = true)
class HouseList {
    // 小区名字
    String title
    // 标题上的面积范围
    String resblock_frame_area
    // 标题上的居室数量
    String frame_rooms_desc
    // 所在区
    String district
    // 所在区的街道
    String bizcircle_name
    // 标题上的详细地址
    String address
    // 标题上的均价
    String average_price
    // 标题上的总价
    String total_price_start
    String total_price_start_unit
    // 楼板链接
    String url
}

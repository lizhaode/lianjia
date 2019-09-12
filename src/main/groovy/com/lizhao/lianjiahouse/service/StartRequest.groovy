package com.lizhao.lianjiahouse.service

import com.lizhao.lianjiahouse.entity.dto.ForSaleHouseData

interface StartRequest {

    ForSaleHouseData executeForSaleRequest(boolean isStart, String pageNum)
}
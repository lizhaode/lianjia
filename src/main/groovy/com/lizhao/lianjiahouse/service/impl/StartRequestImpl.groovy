package com.lizhao.lianjiahouse.service.impl

import com.lizhao.lianjiahouse.entity.dto.ForSaleHouseData
import com.lizhao.lianjiahouse.entity.dto.LianjiaHttpResp
import com.lizhao.lianjiahouse.service.LianjiaFeignClient
import com.lizhao.lianjiahouse.service.StartRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StartRequestImpl implements StartRequest{

    @Autowired
    private LianjiaFeignClient client

    @Override
    ForSaleHouseData executeForSaleRequest(boolean isStart, String pageNum) {
        if (isStart) {
            LianjiaHttpResp response = client.forSaleHouseStart()
            return response.data
        }
        LianjiaHttpResp response = client.forSaleHousePage(pageNum)
        return response.data
    }
}

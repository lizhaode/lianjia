package com.lizhao.lianjiahouse.mapper

import com.lizhao.lianjiahouse.entity.dao.ForSaleHouseDao
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ForSaleHouseMapper {

    int insertForSaleHouse(ForSaleHouseDao dao)

    ForSaleHouseDao selectByHouseName(String houseName)

    int updateForSaleHouse(ForSaleHouseDao dao)
}
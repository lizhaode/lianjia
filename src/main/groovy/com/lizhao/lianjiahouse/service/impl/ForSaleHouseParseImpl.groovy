package com.lizhao.lianjiahouse.service.impl

import com.lizhao.lianjiahouse.entity.CustomException
import com.lizhao.lianjiahouse.entity.dao.ForSaleHouseDao
import com.lizhao.lianjiahouse.entity.dto.ForSaleHouseData
import com.lizhao.lianjiahouse.entity.dto.HouseList
import com.lizhao.lianjiahouse.mapper.ForSaleHouseMapper
import com.lizhao.lianjiahouse.service.ForSaleHouseParse
import com.lizhao.lianjiahouse.service.StartRequest
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Slf4j
@Service
class ForSaleHouseParseImpl implements ForSaleHouseParse {

    @Autowired
    private StartRequest mStartRequest

    @Autowired
    private ForSaleHouseMapper mapper

    @Override
    void poolingSaveData() {
        log.info('start polling request')
        ForSaleHouseData firstData = mStartRequest.executeForSaleRequest(true, '1')
        log.info("get first page data:$firstData")
        firstData.list.each {
            log.info("pepare to deal with house info:$it")
            ForSaleHouseDao selectResult = mapper.selectByHouseName(it.title)
            if (selectResult) {
                saveToMysql(it, 'update', selectResult.id)
            } else {
                saveToMysql(it, 'save', 0)
            }
        }

        boolean moreDate = true
        int page = 2
        while (moreDate) {
            ForSaleHouseData data = mStartRequest.executeForSaleRequest(false, page as String)
            log.info("get $page page data:$data")
            page++
            data.list.each {
                log.info("pepare to deal with house info:$it")
                ForSaleHouseDao selectResult = mapper.selectByHouseName(it.title)
                if (selectResult) {
                    saveToMysql(it, 'update', selectResult.id)
                } else {
                    saveToMysql(it, 'save', 0)
                }
            }
            if (data.no_more_data == 1) {
                moreDate = false
            }
        }
    }

    private saveToMysql(HouseList data, String saveOrUpdate, int id) {
        ForSaleHouseDao houseDao = new ForSaleHouseDao()
        houseDao.houseName = data.title
        houseDao.areaScope = data.resblock_frame_area
        houseDao.roomScope = data.frame_rooms_desc
        houseDao.district = data.district
        houseDao.street = data.bizcircle_name
        houseDao.address = data.address
        houseDao.price = data.average_price as int
        houseDao.totalPrice = data.total_price_start as int
        houseDao.totalPriceUnit = data.total_price_start_unit
        houseDao.url = data.url
        switch (saveOrUpdate) {
            case 'save':
                houseDao.id = id
                log.info("start insert info:$houseDao")
                mapper.insertForSaleHouse(houseDao)
                break
            case 'update':
                log.info("start update info:$houseDao")
                mapper.updateForSaleHouse(houseDao)
                break
            default:
                throw new CustomException('only can use save or update')
        }
    }
}

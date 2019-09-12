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
class ForSaleHouseParseImpl implements ForSaleHouseParse{

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
                saveToMysql(it, 'update')
            } else {
                saveToMysql(it, 'save')
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
                    saveToMysql(it, 'update')
                } else {
                    saveToMysql(it, 'save')
                }
            }
            if (data.no_more_data == 1) {
                moreDate = false
            }
        }
    }

    private saveToMysql(HouseList data, String saveOrUpdate) {
        ForSaleHouseDao houseDao = new ForSaleHouseDao()
        houseDao.house_name = data.title
        houseDao.area_scope = data.resblock_frame_area
        houseDao.room_scope = data.frame_rooms_desc
        houseDao.district = data.district
        houseDao.street = data.bizcircle_name
        houseDao.address = data.address
        houseDao.price = data.average_price as int
        houseDao.total_price = data.total_price_start as int
        houseDao.total_price_unit = data.total_price_start_unit
        houseDao.url = data.url
        switch (saveOrUpdate) {
            case 'save':
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

package com.lizhao.lianjiahouse.listener

import com.fasterxml.jackson.databind.ObjectMapper
import com.lizhao.lianjiahouse.entity.CustomException
import com.lizhao.lianjiahouse.entity.dao.ForSaleHouseDao
import com.lizhao.lianjiahouse.entity.dto.HouseList
import com.lizhao.lianjiahouse.mapper.ForSaleHouseMapper
import groovy.util.logging.Slf4j
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Slf4j
@Component
class HouseDataLinstener {

    @Autowired
    private ForSaleHouseMapper mapper

    @RabbitListener(queues = 'house')
    void parseHouse(Message message) {
        log.info("parseHouse tag:${message.messageProperties.consumerTag}")
        HouseList house = new ObjectMapper().readValue(message.body, HouseList)
        log.info("receive house data:$house")
        ForSaleHouseDao selectResult = mapper.selectByHouseName(house.title)
        if (selectResult) {
            saveToMysql(house, 'update', selectResult.id)
        } else {
            saveToMysql(house, 'save', 0)
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
        houseDao.price = data.average_price as double
        houseDao.totalPrice = data.total_price_start as double
        houseDao.totalPriceUnit = data.total_price_start_unit
        houseDao.url = data.url
        switch (saveOrUpdate) {
            case 'save':
                log.info("start insert info:$houseDao")
                mapper.insertForSaleHouse(houseDao)
                break
            case 'update':
                houseDao.id = id
                log.info("start update info:$houseDao")
                mapper.updateForSaleHouse(houseDao)
                break
            default:
                throw new CustomException('only can use save or update')
        }
    }
}

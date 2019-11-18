package com.lizhao.lianjiahouse.listener

import com.fasterxml.jackson.databind.ObjectMapper
import com.lizhao.lianjiahouse.entity.dto.LianjiaHttpResp
import com.lizhao.lianjiahouse.service.LianjiaFeignClient
import groovy.util.logging.Slf4j
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Slf4j
@Component
class PageListener {

    @Autowired
    private LianjiaFeignClient httpRequest

    @Autowired
    private RabbitTemplate rabbit

    @Value('${constant.rabbit.receiveResult.routing-key}')
    private String routingKey

    @Value('${constant.rabbit.receiveResult.exchange}')
    private String exchange

    @Value('${constant.rabbit.url.routing-key}')
    private String urlRoutingKey

    @Value('${constant.rabbit.url.exchange}')
    private String urlExchange

    @RabbitListener(queues = 'url')
    void pageListener(Message message) {
        int page = Integer.parseInt(new String(message.body))
        if (page == 1) {
            LianjiaHttpResp resp = httpRequest.forSaleHouseStart()
            log.info("get first page data")
            resp.data.list.each {
                log.info("first data:$it")
                rabbit.convertAndSend(exchange, routingKey, new ObjectMapper().writeValueAsString(it))
            }
            log.info('first data send done')
            rabbit.convertAndSend(urlExchange, urlRoutingKey, '2')
        } else {
            LianjiaHttpResp resp = httpRequest.forSaleHousePage(page as String)
            log.info("get $page page data")
            resp.data.list.each {
                log.info("get $page page mq data:$it")
                rabbit.convertAndSend(exchange, routingKey, new ObjectMapper().writeValueAsString(it))
            }
            if (resp.data.no_more_data == 0) {
                page += 1
                rabbit.convertAndSend(urlExchange, urlRoutingKey, page as String)
            }
        }
    }
}

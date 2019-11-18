package com.lizhao.lianjiahouse.service.impl

import com.lizhao.lianjiahouse.service.ForSaleHouseParse
import groovy.util.logging.Slf4j
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Slf4j
@Service
class ForSaleHouseParseImpl implements ForSaleHouseParse {

    @Autowired
    private RabbitTemplate rabbit

    @Value('${constant.rabbit.url.routing-key}')
    private String urlRoutingKey

    @Value('${constant.rabbit.url.exchange}')
    private String urlExchange

    @Override
    void startRequest() {
        rabbit.convertAndSend(urlExchange, urlRoutingKey, '1')
    }
}

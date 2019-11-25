package com.lizhao.lianjiahouse.service.impl

import com.lizhao.lianjiahouse.service.ForSaleHouseParse
import com.lizhao.lianjiahouse.service.PornFeignClient
import feign.Response
import groovy.util.logging.Slf4j
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.StreamUtils

import java.nio.charset.StandardCharsets

@Slf4j
@Service
class ForSaleHouseParseImpl implements ForSaleHouseParse {

    @Autowired
    private RabbitTemplate rabbit

    @Value('${constant.rabbit.url.routing-key}')
    private String urlRoutingKey

    @Value('${constant.rabbit.url.exchange}')
    private String urlExchange

    @Value('${constant.rabbit.oriHTML.routing-key}')
    private String routingKey

    @Value('${constant.rabbit.oriHTML.exchange}')
    private String exchange

    @Autowired
    private PornFeignClient client

    @Override
    void startRequest() {
        rabbit.convertAndSend(urlExchange, urlRoutingKey, '1')
    }

    @Override
    void porn() {
        Response response = client.hottestCurMonth(3, 'basic', 'top')
        rabbit.convertAndSend(exchange, routingKey, StreamUtils.copyToString(response.body().asInputStream(), StandardCharsets.UTF_8))
    }
}

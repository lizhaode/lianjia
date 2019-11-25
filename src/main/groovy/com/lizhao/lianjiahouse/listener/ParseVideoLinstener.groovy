package com.lizhao.lianjiahouse.listener

import com.lizhao.lianjiahouse.service.PornFeignClient
import feign.Response
import groovy.util.logging.Slf4j
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StreamUtils

import java.nio.charset.StandardCharsets

@Slf4j
@Component
class ParseVideoLinstener {

    @Value('${constant.rabbit.videoHTML.routing-key}')
    private String routingKey

    @Value('${constant.rabbit.oriHTML.exchange}')
    private String exchange

    @Autowired
    private RabbitTemplate rabbit

    @Autowired
    private PornFeignClient client

    @RabbitListener(queues = 'videoHTML')
    void parseRealVideo(Message message) {
        log.info("parseRealVideo tag:${message.messageProperties.consumerTag}")
        String[] url = new String(message.body).split('.com/')
        Response response = client.videoPage(url[1])
        String responseBody = StreamUtils.copyToString(response.body().asInputStream(), StandardCharsets.UTF_8)
        Document dc = Jsoup.parse(responseBody)
        String title = dc.selectFirst('div[id=viewvideo-title]').text()
        log.info("$title")
    }
}

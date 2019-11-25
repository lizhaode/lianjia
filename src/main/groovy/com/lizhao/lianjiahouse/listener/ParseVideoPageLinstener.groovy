package com.lizhao.lianjiahouse.listener


import groovy.util.logging.Slf4j
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Slf4j
@Component
class ParseVideoPageLinstener {

    @Value('${constant.rabbit.videoHTML.routing-key}')
    private String routingKey

    @Value('${constant.rabbit.oriHTML.exchange}')
    private String exchange

    @Autowired
    private RabbitTemplate rabbit

    @RabbitListener(queues = 'oriHTML')
    void parseVideoPage(Message message) {
        log.info("parseVideoPage tag:${message.messageProperties.consumerTag}")
        String html = new String(message.body)
        Document dc = Jsoup.parse(html)
        Elements el = dc.select('div.listchannel')
        el.each {
            String sendPageUrl = it.select('a[target=blank]').get(1).attr('href')
            String title = it.select('a[target=blank]').get(1).attr('title')
//            log.info("get $title")
            rabbit.convertAndSend(exchange, routingKey, sendPageUrl)
        }

    }
}

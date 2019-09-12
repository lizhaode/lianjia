package com.lizhao.lianjiahouse.entity.vo

import groovy.transform.ToString

@ToString(includeNames = true)
class ResponseBean {
    int code

    Object data

    String message

    String extra

    static ResponseBean success() {
        return new ResponseBean(code: 0, data: null, message: 'SUCCESS', extra: null)
    }

    static ResponseBean success(Object data) {
        return new ResponseBean(code: 0, data: data, message: 'SUCCESS', extra: null)
    }
}

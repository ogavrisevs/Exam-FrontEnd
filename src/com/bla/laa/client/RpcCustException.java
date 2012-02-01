package com.bla.laa.client;

import java.io.Serializable;

public class RpcCustException extends Exception implements Serializable{
    String msg = null;

    public RpcCustException() {
    }

    public RpcCustException(String message) {
        super(message);
        this.msg = message;
    }

    public String getMsg() {
        return msg;
    }
}

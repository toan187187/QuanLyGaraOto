package com.example.doan123.Network;

import java.io.Serializable;
import java.util.Objects;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;   // gửi mạng
    private String action;
    private Object data;

    public Request(String action, Object data){
        this.action = action;
        this.data = data;
    }
    public String getAction(){ return action;}
    public Object getData(){ return data;}
}

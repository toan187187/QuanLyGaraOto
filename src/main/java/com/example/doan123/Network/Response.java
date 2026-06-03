package com.example.doan123.Network;

import java.io.Serializable;
import java.util.Objects;

public class Response implements Serializable {
    private static final long serialVersionUID= 1L;
    private boolean success;
    private String message;
    private Object data;

    public Response(boolean success, String message, Object data) {
        this.message = message;
        this.data = data;
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}

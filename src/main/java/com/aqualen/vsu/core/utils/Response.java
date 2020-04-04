package com.aqualen.vsu.core.utils;

import com.aqualen.vsu.core.entity.enums.ResponseStatus;

public class Response {
    private ResponseStatus status;
    private Object data;
    private String error;

    private Response(ResponseStatus status, Object data, String error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public static Response success() {
        return new Response(ResponseStatus.OK, null, null);
    }

    public static Response success(Object data) {
        return new Response(ResponseStatus.OK, data, null);
    }

    public static Response fail(String message) {
        return new Response(ResponseStatus.ERROR, null, message);
    }
}

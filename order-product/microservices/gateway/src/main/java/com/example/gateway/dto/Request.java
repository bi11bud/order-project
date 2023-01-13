package com.example.gateway.dto;

public class Request<Any> {
    private Any content;

    public Any getContent() {
        return content;
    }

    public void setContent(Any content) {
        this.content = content;
    }
}

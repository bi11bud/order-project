package com.example.orderservice.dto;

public class Response<Any> {

    private Status status;
    private Any content;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Any getContent() {
        return content;
    }

    public void setContent(Any content) {
        this.content = content;
    }
}

package com.grupoK.web_service_server.graphql.model;

public class GraphQLResponse<T> {
    private String status;
    private String message; 
    private T data;

    public GraphQLResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    
    public GraphQLResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }

    // getters y setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void Message(String message) { this.message = message; }
    
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}

package com.ilpalazzo.model.dto;

public class OrderNotificationDto {
    private Long orderId;
    private String userId;
    private String status;
    private String message;

    // Constructors
    public OrderNotificationDto() {
    }

    public OrderNotificationDto(Long orderId, String userId, String status, String message) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.message = message;
    }

    // Getters and setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "OrderNotificationDto{" +
                "orderId=" + orderId +
                ", userId='" + userId + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

package entity;

import java.io.Serializable;

public enum Status implements Serializable {
    AVAILABLE("AVAILABLE"),
    IN_USE("IN_USE"),
    UNDER_MAINTENANCE("UNDER_MAINTENANCE"),
    BROKEN("BROKEN");
    private String status;

    Status(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
    @Override
    public String toString() {
        return "Status{" +
                "status='" + status + '\'' +
                '}';
    }
    public static Status fromString(String status) {
        return Status.valueOf(status.toUpperCase());
    }
}

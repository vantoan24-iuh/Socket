package entity;

import java.io.Serializable;

public enum Status implements Serializable {
    PENDING("PENDING"), APPROVED("APPROVED"), REJECTED("REJECTED");

    private String status;

    Status(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return
                ", status='" + status + "' ";
    }
    public static Status fromString(String status) {
        return Status.valueOf(status.toUpperCase());
    }
}

package entity;

import jakarta.persistence.Column;

public enum Size {
    LARGE("LARGE"),
    MEDIUM("MEDIUM"),
    SMALL("SMALL");
    private String size;

    Size(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Size{" +
                "size='" + size + '\'' +
                '}';
    }
}

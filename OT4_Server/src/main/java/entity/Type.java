package entity;

import jakarta.persistence.Column;

public enum Type {
    APPETIZER("APPETIZER"),
    MAIN_COURSE("MAIN_COURSE"),
    DESSERT("DESSERT");
    private String type;

    Type(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Type{" +
                "type='" + type + '\'' +
                '}';
    }
}

package com.example.octi.Models;

public class Action {
    private Integer value;
    private Boolean isEat;

    public Action() {}

    public Action(Integer value, Boolean isEat) {
        this.value = value;
        this.isEat = isEat;
    }

    public Integer getValue() {
        return value;
    }

    public Boolean getEat() {
        return isEat;
    }
}

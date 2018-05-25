package com.example.dchikhaoui.myprojectmvvm.Model;

public class offers {
    String type;
    int value;
    int sliceValue;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getSliceValue() {
        return sliceValue;
    }

    public void setSliceValue(int sliceValue) {
        this.sliceValue = sliceValue;
    }

    @Override
    public String toString() {
        return "offers{" +
                "type='" + type + '\'' +
                ", value=" + value +
                ", sliceValue='" + sliceValue + '\'' +
                '}';
    }
}

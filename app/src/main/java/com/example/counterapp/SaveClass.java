package com.example.counterapp;

public class SaveClass {
    int countC;
    String nameC;

    @Override
    public String toString() {
        return "saveClass{" +
                "countC=" + countC +
                ", nameC='" + nameC + '\'' +
                '}';
    }

    public String getCountC() {
        String a = countC + "";
        return a;
    }

    public void setCountC(int countC) {
        this.countC = countC;
    }

    public String getNameC() {
        return nameC;
    }

    public void setNameC(String nameC) {
        this.nameC = nameC;
    }

    public SaveClass(int countC, String nameC) {
        this.countC = countC;
        this.nameC = nameC;
    }
}

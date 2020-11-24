package com.ivangy.lospaco.model;

public class ServicesScheduled {

    private String empName, servName, dateTimeSched, empImage, servImage;
    private int code;

    public ServicesScheduled(int code, String empName, String servName, String empImage, String servImage, String dateTimeSched) {
        this.empName = empName;
        this.servName = servName;
        this.empImage = empImage;
        this.servImage = servImage;
        this.code = code;
        this.dateTimeSched = dateTimeSched;
    }

    public String getEmpName() {
        return empName;
    }

    public String getServName() {
        return servName;
    }

    public String getEmpImage() {
        return empImage;
    }

    public String getServImage() {
        return servImage;
    }

    public String getDateTimeSched() {
        return dateTimeSched;
    }

    public int getCode() {
        return code;
    }
}

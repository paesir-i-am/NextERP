package com.nexterp.employee.entity;

public enum AttendanceStatus {
    ON_TIME("정상 출근"),
    LATE("지각"),
    LEAVE("휴가"),
    SICK_LEAVE("병가"),
    REMOTE_WORK("재택");

    private final String description;

    AttendanceStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Activity {
    private Integer id;
    private String title;
    private Short quota;
    private LocalDateTime deadline;
    private LocalDate date;
    private LocalTime begin;
    private LocalTime end;
    private String address;
    private int oldId;
    private String phone;
    private String description;
    private Short status;
    private int administratorId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String message;
    private Short remain;
}

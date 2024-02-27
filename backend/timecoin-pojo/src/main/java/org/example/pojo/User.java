package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private short role;
    private String email;
    private short age;
    private String phone;
    private String address;

    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

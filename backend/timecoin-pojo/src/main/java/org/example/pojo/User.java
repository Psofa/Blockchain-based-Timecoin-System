package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private Short role;
    private String email;
    private Short age;
    private String phone;
    private String address;

    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String image;
}

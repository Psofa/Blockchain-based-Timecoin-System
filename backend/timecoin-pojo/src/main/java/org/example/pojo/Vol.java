package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vol {
    Integer id;//用户id
    String name;
    String phone;
    String email;
    Short age;
    Short status;
    Short sign;
}

package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VolActivity {
    Integer id;
    Integer userId;
    Integer volId;
    Integer activityId;
    Short status;
    Short sign;
    LocalDateTime createTime;
    LocalDateTime updateTime;
}

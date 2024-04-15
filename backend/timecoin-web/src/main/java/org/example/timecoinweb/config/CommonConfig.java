package org.example.timecoinweb.config;

import org.example.utils.AliOSSUtils;
import org.example.utils.DistanceUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    @Bean
    public AliOSSUtils aliOSSUtils(){
        return new AliOSSUtils();
    }

    @Bean
    public DistanceUtils distanceUtils(){
        return new DistanceUtils();
    }

}

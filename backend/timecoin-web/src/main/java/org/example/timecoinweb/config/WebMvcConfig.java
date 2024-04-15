package org.example.timecoinweb.config;


import org.example.timecoinweb.interceptor.LoginCheckInterceptor;
<<<<<<< HEAD
import org.example.utils.AliOSSUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
=======
import org.springframework.context.annotation.Bean;
>>>>>>> c09fab713a5f284a2c783aa7635c72ace7d1f39f
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

<<<<<<< HEAD
import java.awt.*;

=======
>>>>>>> c09fab713a5f284a2c783aa7635c72ace7d1f39f
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor()).addPathPatterns("/**");
    }

<<<<<<< HEAD


=======
>>>>>>> c09fab713a5f284a2c783aa7635c72ace7d1f39f
}

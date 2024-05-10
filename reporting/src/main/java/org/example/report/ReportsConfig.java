package org.example.report;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ReportsConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

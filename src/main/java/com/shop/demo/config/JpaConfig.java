package com.shop.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@EnableJpaAuditing(auditorAwareRef = "securityAuditorAware")
@Configuration
public class JpaConfig {
    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer pageableHandlerMethodArgumentResolverCustomizer() {
        return p -> p.setOneIndexedParameters(true);
    }
}

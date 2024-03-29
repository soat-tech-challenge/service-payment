package br.com.grupo63.techchallenge.payment.config;

import br.com.grupo63.techchallenge.common.config.auth.jwt.JwtFilter;
import br.com.grupo63.techchallenge.common.config.auth.jwt.JwtService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class JwtFilterConfig {

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilterFilterRegistrationBean(JwtService jwtService) {
        FilterRegistrationBean<JwtFilter> jwtFilterFilterRegistrationBean = new FilterRegistrationBean<>();
        jwtFilterFilterRegistrationBean.setFilter(new JwtFilter(jwtService));
        jwtFilterFilterRegistrationBean.setUrlPatterns(List.of("/payments/initialize"));
        return jwtFilterFilterRegistrationBean;
    }
}

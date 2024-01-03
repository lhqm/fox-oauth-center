package com.pj.config;

import com.pj.adapter.RedirectInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RedirectInterceptor redirectInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(redirectInterceptor);

    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login/**")
                .setViewName("forward:/index.html");
    }
}
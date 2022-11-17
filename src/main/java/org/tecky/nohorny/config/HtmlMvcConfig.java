package org.tecky.nohorny.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

//@Configuration
//@EnableWebMvc
public class HtmlMvcConfig implements WebMvcConfigurer {

//    @Bean
    public ClassLoaderTemplateResolver yourTemplateResolver() {
        ClassLoaderTemplateResolver yourTemplateResolver = new ClassLoaderTemplateResolver();
        yourTemplateResolver.setPrefix("classpath:templates/");
        yourTemplateResolver.setSuffix(".html");
        yourTemplateResolver.setCacheable(false);
        yourTemplateResolver.setTemplateMode(TemplateMode.HTML);
        yourTemplateResolver.setCharacterEncoding("UTF-8");
        yourTemplateResolver.setOrder(0);
        // this is iportant. This way spring
        //boot will listen to both places 0
        //and 1

        return yourTemplateResolver;
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/nohorny/css/**").addResourceLocations("classpath:/static/nohorny/css");
//        registry.addResourceHandler("/nohorny/images/**").addResourceLocations("classpath:/static/nohorny/images");
//        registry.addResourceHandler("/nohorny/js/**").addResourceLocations("classpath:/static/nohorny/js");
//        registry.addResourceHandler("/nohorny/**").addResourceLocations("classpath:/nohorny/");
//
//        WebMvcConfigurer.super.addResourceHandlers(registry);
//    }



}

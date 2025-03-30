package org.example.locket_clone_backend.config;

import org.example.locket_clone_backend.utils.AppInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AppInterceptor());
//                .addPathPatterns("/api/**") // Chỉ áp dụng cho API `/api/**`
//                .excludePathPatterns("/auth/**"); // Bỏ qua API `/auth/**`
    }
}

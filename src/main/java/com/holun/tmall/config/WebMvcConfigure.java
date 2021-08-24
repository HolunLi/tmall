package com.holun.tmall.config;

import com.holun.tmall.interceptor.ForeInterceptor;
import com.holun.tmall.service.CategoryService;
import com.holun.tmall.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OrderItemService orderItemService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       String[] url_pattern = new String[]{
               "/foreLogin",
               "/foreRegister",
               "/foreLogout",
               "/foreAddCart",
               "/foreDeleteOrderItem",
               "/foreDeleteOrder",
               "/foreChangeOrderItem",
               "/foreCheckLogin",
               "/foreToLoginPage",
               "/foreBuyFromProductPage",
               "/foreBuyFromCart",
               "/foreCreateOrder",
               "/foreDoReview"
        };

        registry.addInterceptor(new ForeInterceptor(categoryService, orderItemService))
                .addPathPatterns("/", "/home", "/fore*/**")
                .excludePathPatterns(url_pattern);
    }
}

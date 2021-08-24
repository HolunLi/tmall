package com.holun.tmall.controller;

import com.holun.tmall.entity.Product;
import com.holun.tmall.entity.PropertyValue;
import com.holun.tmall.service.ProductService;
import com.holun.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class PropertyValueController {
    private PropertyValueService propertyValueService;
    private ProductService productService;

    @Autowired
    public void setPropertyValueService(PropertyValueService propertyValueService) {
        this.propertyValueService = propertyValueService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin_propertyValue_edit/{pid}")
    public String editPropertyValue(@PathVariable("pid")int pid, Model model) {
        Product product = productService.queryProductById(pid);
        //初始化产品的属性值
        propertyValueService.initPropertyValue(product);
        List<PropertyValue> propertyValues = propertyValueService.list(pid);

        model.addAttribute("product", product);
        model.addAttribute("propertyValues", propertyValues);

        return "admin/editPropertyValue";
    }

    @PostMapping("/admin_propertyValue_update")
    @ResponseBody //在控制器方法上使用该注解，可以跳过视图器解析，直接返回数据
    public String updatePropertyValue(PropertyValue propertyValue) {
        propertyValueService.updatePropertyValue(propertyValue);

        return "success";
    }
}

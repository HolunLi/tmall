package com.holun.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.holun.tmall.entity.Category;
import com.holun.tmall.entity.Property;
import com.holun.tmall.service.CategoryService;
import com.holun.tmall.service.PropertyService;
import com.holun.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PropertyController {
    private PropertyService propertyService;
    private CategoryService categoryService;

    @Autowired
    public void setPropertyService(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/admin_property_list/{cid}")
    public String list(@PathVariable("cid")int cid, Page page, Model model) {
        Category category = categoryService.queryCategoryById(cid);

        //分页显示一种分类包含的所有属性（一对多）
        PageHelper.offsetPage(page.getStart(), page.getPageSize());
        List<Property> properties = propertyService.list(cid);
        int total = (int) new PageInfo<>(properties).getTotal();
        page.setTotal(total);

        model.addAttribute("category", category);
        model.addAttribute("properties", properties);
        model.addAttribute("page", page);

        return "admin/listProperty";
    }

    @PostMapping("/admin_property_add")
    public String addProperty(Property property) {
        propertyService.addProperty(property);

        return "redirect:admin_property_list/" + property.getCid();
    }

    @GetMapping("/admin_property_delete/{id}/{cid}")
    public String deleteProperty(@PathVariable("id")int id, @PathVariable("cid")int cid) {
        propertyService.deletePropertyById(id);

        return "redirect:/admin_property_list/" + cid;
    }

    @GetMapping("/admin_property_edit/{id}")
    public String editProperty(@PathVariable("id")int id, Model model) {
        Property property = propertyService.queryPropertyById(id);
        model.addAttribute("property",property);

        return "admin/editProperty";
    }

    @PostMapping("/admin_property_update")
    public String updateProperty(Property property, int start) {
        propertyService.updateProperty(property);

        return "redirect:admin_property_list/"+property.getCid() + "?start="+start;
    }
}

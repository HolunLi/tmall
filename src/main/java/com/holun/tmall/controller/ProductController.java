package com.holun.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.holun.tmall.entity.Category;
import com.holun.tmall.entity.Product;
import com.holun.tmall.service.CategoryService;
import com.holun.tmall.service.ProductService;
import com.holun.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Date;
import java.util.List;

@Controller
public class ProductController {
    private ProductService productService;
    private CategoryService categoryService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/admin_product_list/{cid}")
    public String list(@PathVariable("cid")int cid, Page page, Model model) {
        Category category = categoryService.queryCategoryById(cid);

        //分页显示一种分类包含的所有的产品（一对多）
        PageHelper.offsetPage(page.getStart(), page.getPageSize());
        List<Product> products = productService.queryProductByCid(cid);
        int total = (int) new PageInfo<>(products).getTotal();
        page.setTotal(total);

        model.addAttribute("page", page);
        model.addAttribute("products", products);
        model.addAttribute("category", category);

        return "admin/listProduct";
    }

    @PostMapping("/admin_product_add")
    public String addProduct(Product product) {
        product.setCreateDate(new Date());
        productService.addProduct(product);

        return "redirect:admin_product_list/" + product.getCid();
    }

    @GetMapping("/admin_product_edit/{id}")
    public String editProduct(@PathVariable("id")int id, Model model) {
        Product product = productService.queryProductById(id);
        model.addAttribute("product", product);

        return "admin/editProduct";
    }

    @PostMapping("/admin_product_update")
    public String updateProduct(Product product, int start) {
        productService.updateProduct(product);

        return "redirect:admin_product_list/"+product.getCid() + "?start="+start;
    }

    @GetMapping("/admin_product_delete/{id}/{cid}")
    public String deleteProduct(@PathVariable("id")int id, @PathVariable("cid")int cid) {
        productService.deleteProductById(id);

        return "redirect:/admin_product_list/" + cid;
    }
}

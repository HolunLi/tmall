package com.holun.tmall.controller;

import com.holun.tmall.entity.Product;
import com.holun.tmall.entity.ProductImage;
import com.holun.tmall.service.ProductImageService;
import com.holun.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProductImageController {
    private ProductImageService productImageService;
    private ProductService productService;

    @Autowired
    public void setProductImageService(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin_productImage_list/{pid}")
    public String list(@PathVariable("pid")int pid, Model model) {
        Product product = productService.queryProductById(pid);

        List<ProductImage> singleImages = productImageService.list(pid, ProductImageService.type_single);
        List<ProductImage> detailImages = productImageService.list(pid, ProductImageService.type_detail);

        model.addAttribute("product", product);
        model.addAttribute("singleImages", singleImages);
        model.addAttribute("detailImages", detailImages);

        return "admin/listProductImage";
    }

    @PostMapping("/admin_productImage_add")
    public String addProductImage(ProductImage productImage, MultipartFile[] images, HttpSession session) {
        String imageFolder, imageFolder_small, imageFolder_middle;

        if (ProductImageService.type_single.equals(productImage.getType())) {
            imageFolder = session.getServletContext().getRealPath("img/productimage/single");
            imageFolder_small = session.getServletContext().getRealPath("img/productimage/single_small");
            imageFolder_middle = session.getServletContext().getRealPath("img/productimage/single_middle");
            productImageService.uploadProductImage(productImage, images, imageFolder, imageFolder_small, imageFolder_middle);
        }
        else {
            imageFolder = session.getServletContext().getRealPath("img/productimage/detail");
            productImageService.uploadProductImage(productImage, images, imageFolder);
        }

        return "redirect:admin_productImage_list/" + productImage.getPid();
    }

    @GetMapping("/admin_productImage_delete/{id}")
    public String deleteProductImage(@PathVariable("id")int id, HttpSession session) {
        ProductImage productImage = productImageService.queryProductImageById(id);
        String imageFolder, imageFolder_small, imageFolder_middle;

        if (ProductImageService.type_single.equals(productImage.getType())) {
            imageFolder = session.getServletContext().getRealPath("img/productimage/single");
            imageFolder_small = session.getServletContext().getRealPath("img/productimage/single_small");
            imageFolder_middle = session.getServletContext().getRealPath("img/productimage/single_middle");
            productImageService.deleteUploadProductImage(productImage, imageFolder, imageFolder_small, imageFolder_middle);
        }
        else {
            imageFolder = session.getServletContext().getRealPath("img/productimage/detail");
            productImageService.deleteUploadProductImage(productImage, imageFolder);
        }

        return "redirect:/admin_productImage_list/" + productImage.getPid();
    }

}

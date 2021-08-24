package com.holun.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.holun.tmall.entity.Category;
import com.holun.tmall.service.CategoryService;
import com.holun.tmall.util.Page;
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
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping({"/admin_category_list", "/admin"})
    public String list(Page page, Model model) {
        /*使用pageHelp插件进行分页显示所有的分类，以下三个步骤必须严格按照顺序*/
        //第一步：通过分页插件指定分页参数（从第几条数据开始，每页显示几条数据）
        PageHelper.offsetPage(page.getStart(), page.getPageSize());
        //第二步：查询出category表中所有的数据
        List<Category> cs = categoryService.list();
        //第三步：通过PageInfo计算总共有几条数据
        int total = (int) new PageInfo<>(cs).getTotal();
        page.setTotal(total);
        model.addAttribute("cs", cs);
        model.addAttribute("page", page);

        return "admin/listCategory";
    }

    /**
     *MultipartFile是spring框架提供的类型，代表表单中以"multipart/form-data"方式上传的文件，包含二进制数据+文件名称。
     * 使用org.springframework.mock.web.MockMultipartFile类型 需要导入spring-test.jar
     * 因此在addCategory方法中使用MultipartFile对象用来接收从前端页面上传的文件（图片，文本等类型的文件）
     * 这里需要注意是MultipartFile对象的名字image,必须和页面中的增加分类部分中的type="file"的 name属性值保持一致。
     */
    @PostMapping("/admin_category_add")
    public String addCategory(Category category, MultipartFile image, HttpSession session) {
        //获取分类图片存放的路径（分类图片上传到G:\github_repository\tmall\src\main\resources\static\img\category路径下）
        String path = session.getServletContext().getRealPath("img/category");
        categoryService.uploadCategoryImage(category, image, path);

        return "redirect:admin_category_list";
    }

    @GetMapping("/admin_category_delete/{id}")
    public String deleteCategory(@PathVariable("id")int id, HttpSession session) {
        String path = session.getServletContext().getRealPath("img/category");
        categoryService.deleteUploadCategoryImage(id, path);

        return "redirect:/admin_category_list";
    } 
    
    @GetMapping("/admin_category_edit/{id}")
    public String editCategory(@PathVariable("id")int id, Model model) {
        Category category = categoryService.queryCategoryById(id);
        model.addAttribute("category", category);

        return "admin/editCategory";
    }

    @PostMapping("/admin_category_update")
    public String updateCategory(Category category, int start, MultipartFile image, HttpSession session) {
        String path = session.getServletContext().getRealPath("img/category");
        categoryService.updateCategoryImage(category, image, path);

        return "redirect:admin_category_list?start=" + start;
    }
}

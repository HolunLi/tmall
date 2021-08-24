package com.holun.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.holun.tmall.entity.*;
import com.holun.tmall.service.*;
import com.holun.tmall.util.Page;
import org.apache.commons.lang3.RandomUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 前台控制器
 */
@Controller
public class ForeController {
    private CategoryService categoryService;
    private ProductService productService;
    private ProductImageService productImageService;
    private PropertyValueService propertyValueService;
    private ReviewService reviewService;
    private OrderService orderService;
    private OrderItemService orderItemService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setProductImageService(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @Autowired
    public void setPropertyValueService(PropertyValueService propertyValueService) {
        this.propertyValueService = propertyValueService;
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setOrderItemService(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping({"/", "/foreHome", "fore", "home"})
    public String home (Model model) {
        List<Category> categories = categoryService.list();
        productService.setProducts(categories);
        productService.setProductsByRow(categories);
        model.addAttribute("categories", categories);

        return "fore/home";
    }

    @GetMapping("/foreLogout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        //对已认证的用户,提供注销功能
        if (subject.isAuthenticated())
            subject.logout();
        return "redirect:foreHome";
    }

    @GetMapping("/foreCart")
    public String cart(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItems = orderItemService.listByUid(user.getId());

        model.addAttribute("orderItems", orderItems);

        return "fore/cart";
    }

    @GetMapping("/foreMyOrder")
    public String myOrder(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<Order> orders = orderService.list(user.getId(), OrderService.delete);

        model.addAttribute("orders", orders);

        return "fore/myOrder";
    }

    @GetMapping("/foreProduct/{pid}")
    public String product(@PathVariable("pid")int pid, Model model) {
        Product product = productService.queryProductById(pid);
        List<ProductImage> singleImages = productImageService.list(product.getId(), ProductImageService.type_single);
        List<ProductImage> detailImages = productImageService.list(product.getId(), ProductImageService.type_detail);
        product.setProductSingleImages(singleImages);
        product.setProductDetailImages(detailImages);

        List<PropertyValue> propertyValues = propertyValueService.list(product.getId());
        List<Review> reviews = reviewService.queryReviewByPid(product.getId());

        model.addAttribute("product", product);
        model.addAttribute("propertyValues", propertyValues);
        model.addAttribute("reviews", reviews);

        return "fore/product";
    }

    @GetMapping("/foreCategory/{cid}")
    public String category(@PathVariable("cid")int cid,@RequestParam(value = "sort", defaultValue = "all") String sort, Page page, Model model) {
        //分类页面的产品,每页只显示20个
        page.setPageSize(20);
        PageHelper.offsetPage(page.getStart(), page.getPageSize());
        List<Product> products = productService.queryProductByCid(cid);
        int total = (int) new PageInfo<>(products).getTotal();
        page.setTotal(total);
        page.setParam("&sort=" + sort);

        categoryService.sort(products, sort);

        model.addAttribute("page", page);
        model.addAttribute("sort", sort);
        model.addAttribute("products", products);
        model.addAttribute("cid", cid);

        return "fore/category";
    }

    @GetMapping("/foreSearch")
    public String search(String keyword, @RequestParam(value = "sort", defaultValue = "all")String sort, Page page, Model model) {
        //搜索出来的产品,每页只显示20个
        page.setPageSize(20);
        PageHelper.offsetPage(page.getStart(), page.getPageSize());
        List<Product> products = productService.search(keyword);
        int total = (int) new PageInfo<>(products).getTotal();
        page.setTotal(total);
        page.setParam("&keyword="+keyword + "&sort="+sort);

        categoryService.sort(products, sort);

        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort);
        model.addAttribute("products", products);
        return "fore/searchResult";
    }

    @GetMapping("/foreBuyFromProductPage")
    public String buyFromProductPage(int pid, int number, HttpSession session) {
        Product product = productService.queryProductById(pid);
        List<OrderItem> orderItems = new ArrayList<>();

        OrderItem orderItem = new OrderItem();
        orderItem.setNumber(number);
        orderItem.setPid(pid);
        orderItem.setUid(((User) session.getAttribute("user")).getId());
        orderItem.setProduct(product);
        orderItems.add(orderItem);

        session.setAttribute("orderItems", orderItems);
        session.setAttribute("from", "productPage");

        return "redirect:foreToConfirmOrderPage?total=" + product.getPromotePrice() * number;
    }

    @GetMapping("/foreBuyFromCart")
    public String buyFromCart(int[] oiid, HttpSession session) {
        List<OrderItem> orderItems = new ArrayList<>();
        int total = 0;

        for (int id : oiid) {
            OrderItem orderItem = orderItemService.queryOrderItemById(id);
            total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
            orderItems.add(orderItem);
        }

        session.setAttribute("orderItems", orderItems);
        session.setAttribute("from", "cart");

        return "redirect:foreToConfirmOrderPage?total=" + total;
    }

    /**
     * @param ra  重定向携带参数
     */
    @PostMapping("/foreCreateOrder")
    public String createOrder(Order order, HttpSession session, RedirectAttributes ra) {
        //设置订单的键uid
        order.setUid(((User) session.getAttribute("user")).getId());
        //设置订单编号（订单编号由当前日期+四位随机数组成）
        order.setOrderCode(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(0, 10000));
        //设置订单状态为待付款
        order.setStatus(OrderService.waitPay);
        //设置创建订单的日期
        order.setCreateDate(new Date());

        float total;
        String from = (String) session.getAttribute("from");
        if ("cart".equals(from))
            total = orderService.addOrderAndUpdateOrderItem(order, (List<OrderItem>) session.getAttribute("orderItems"));
        else
            total = orderService.addOrderAndOrderItem(order, ((List<OrderItem>) session.getAttribute("orderItems")).get(0));

        ra.addAttribute("oid", order.getId());
        ra.addAttribute("total", total);
        return "redirect:foreToConfirmPaymentPage";
    }

    @GetMapping("/forePaymentSuccess")
    public String paymentSuccess(int oid, Model model) {
        Order order = orderService.queryOrderById(oid);
        //设置订单的状态为待发货
        order.setStatus(OrderService.waitDelivery);
        //设置订单的付款日期
        order.setPayDate(new Date());
        //修改当前订单的信息,订单项的信息并且修改产品库存
        orderService.updateOrderAndOrderItemAndProductStock(order);
        model.addAttribute("order", order);

        return "fore/paymentSuccess";
    }

    @GetMapping("/foreConfirmReceipt/{oid}")
    public String confirmReceipt(@PathVariable("oid")int oid, Model model) {
        Order order = orderService.queryOrderById(oid);
        model.addAttribute("order", order);

        return "fore/confirmReceipt";
    }

    @GetMapping("/foreReceivedSuccess")
    public String receivedSuccess(int oid) {
        Order order = orderService.queryOrderById(oid);
        //设置订单状态为待评价
        order.setStatus(OrderService.waitReview);
        //设置收货日期
        order.setConfirmDate(new Date());
        orderService.updateOrder(order);

        return "fore/receivedSuccess";
    }

    @GetMapping("/foreReview")
    public String review(int oid, int pid, Model model) {
        Order order = orderService.queryOrderById(oid);
        Product product = productService.queryProductById(pid);
        List<Review> reviews = reviewService.queryReviewByPid(product.getId());

        model.addAttribute("order", order);
        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);

        return "fore/review";
    }

    @PostMapping("/foreDoReview")
    public String doReview(int oid, int pid, int oiid, String content, HttpSession session) {
        Order order = orderService.queryOrderById(oid);
        OrderItem orderItem = orderItemService.queryOrderItemById(oiid);

        Review review = new Review();
        review.setContent(HtmlUtils.htmlEscape(content));
        review.setUid(((User) session.getAttribute("user")).getId());
        review.setPid(pid);
        review.setCreateDate(new Date());

        orderService.updateOrderAndOrderItemAndAddReview(order, orderItem, review);

        return "redirect:foreReview?oid="+oid+"&pid="+pid+"&showonly=yes";
    }

}

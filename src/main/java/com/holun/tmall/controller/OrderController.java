package com.holun.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.holun.tmall.entity.Order;
import com.holun.tmall.service.OrderService;
import com.holun.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin_order_list")
    public String list(Page page, Model model) {
        page.setPageSize(15);
        PageHelper.offsetPage(page.getStart(), page.getPageSize());
        List<Order> orders = orderService.list();
        int total = (int) new PageInfo<>(orders).getTotal();
        page.setTotal(total);

        model.addAttribute("page", page);
        model.addAttribute("orders", orders);

        return "admin/listOrder";
    }

    @GetMapping("/admin_order_delivery")
    public String delivery(Order order) {
        //设置当前订单的发货时间
        order.setDeliveryDate(new Date());
        //将当前订单的状态设置为待收获
        order.setStatus(OrderService.waitConfirm);
        orderService.updateOrder(order);

        return "redirect:/admin_order_list";
    }
}

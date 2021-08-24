package com.holun.tmall.controller;

import com.holun.tmall.entity.Order;
import com.holun.tmall.entity.OrderItem;
import com.holun.tmall.entity.User;
import com.holun.tmall.service.OrderItemService;
import com.holun.tmall.service.OrderService;
import com.holun.tmall.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 前台控制器（Rest）
 */
@RestController
public class ForeRestController {
    private UserService userService;
    private OrderService orderService;
    private OrderItemService orderItemService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setOrderItemService(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping("/foreLogin")
    public String login(String name, String pwd, HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        //创建subject对象
        Subject subject = SecurityUtils.getSubject();
        //以当前用户的用户名、密码为参数，生成口令
        UsernamePasswordToken token = new UsernamePasswordToken(name, pwd);

        try {
            //使用口令登录，进行身份认证
            subject.login(token);
            User user = userService.queryUserByName(name);
            //subject.getSession().setAttribute("user", user);
            session.setAttribute("user", user);

            return "success";
        } catch (UnknownAccountException uae) {
            return "不存在该用户";
        } catch (IncorrectCredentialsException ice) {
            return "密码错误";
        } catch (LockedAccountException lae) {
            return "账户被锁定";
        }
    }

    @PostMapping("/foreRegister")
    public String register(User user) {
        //因为有人在恶意注册的时候，会使用诸如 <script>alert('哈哈')</script> 这样的名称，会导致网页打开就弹出一个对话框。
        //那么在转义之后，就没有这个问题了。
        user.setName(HtmlUtils.htmlEscape(user.getName()));
        boolean exist = userService.isExist(user.getName());

        //若注册时，输入的用户名已存在，返回fail
        if (exist)
            return "fail";

        //若注册时，输入的用户名不存在，就将这个用户添加到user表中，返回success
        userService.addUser(user);
        return "success";
    }

    @GetMapping("/foreCheckLogin")
    public String checkLogin() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated())
            return "success";
        return "fail";
    }

    @PostMapping("/foreAddCart")
    public String addCart(int pid, int number, HttpSession session) {
        User user = (User) session.getAttribute("user");
        orderItemService.addProductToCart(pid, number, user.getId());

        return "success";
    }

    @PostMapping("/foreDeleteOrder")
    public String deleteOrder(int oid, HttpSession session) {
        User user = (User) session.getAttribute("user");
        //这里对user进行判断，是为了防止由于当前页面可能停留时间过久，导致session失效。
        if (user == null)
            return "fail";

        Order order = orderService.queryOrderById(oid);
        //设置订单状态为已删除
        order.setStatus(OrderService.delete);
        orderService.updateOrder(order);
        return "success";
    }

    @PostMapping("/foreDeleteOrderItem")
    public String deleteOrderItem(int oiid, HttpSession session) {
        User user = (User) session.getAttribute("user");
        //这里对user进行判断，是为了防止由于当前页面可能停留时间过久，导致session失效。
        if (user == null)
            return "fail";

        orderItemService.deleteOrderItemById(oiid);
        return "success";
    }

    @PostMapping("/foreChangeOrderItem")
    public String changeOrderItem(int pid, int number, HttpSession session) {
        User user = (User) session.getAttribute("user");
        //这里对user进行判断，是为了防止由于当前页面可能停留时间过久，导致session失效。
        if (user == null)
            return "fail";

        List<OrderItem> orderItems = orderItemService.listByUid(user.getId());
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getPid() == pid) {
                orderItem.setNumber(number);
                orderItemService.updateOrderItem(orderItem);
                break;
            }
        }

        return "success";
    }
}

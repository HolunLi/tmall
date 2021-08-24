package com.holun.tmall.interceptor;

import com.holun.tmall.entity.Category;
import com.holun.tmall.entity.OrderItem;
import com.holun.tmall.entity.User;
import com.holun.tmall.service.CategoryService;
import com.holun.tmall.service.OrderItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 前台拦截器
 */
public class ForeInterceptor implements HandlerInterceptor {
    private CategoryService categoryService;
    private OrderItemService orderItemService;

    public ForeInterceptor(CategoryService categoryService, OrderItemService orderItemService) {
        this.categoryService = categoryService;
        this.orderItemService = orderItemService;
    }

    /**
     * postHandle方法会在处理器处理请求完成后,生成视图之前被调用
     * 因此可以在将数据渲染到视图之前，往modelAndView中再添加一些数据
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();

        String contextPath = session.getServletContext().getContextPath();
        System.out.println(contextPath);

        String requestURI = StringUtils.remove(request.getRequestURI(), contextPath);
        System.out.println(requestURI);
        String method = StringUtils.substringAfterLast(requestURI, "/fore");
        if ("Home".equals(method) || "".equals(method)) {
            //System.out.println("不做处理");
        }
        else {
            List<Category> categories = categoryService.list();
            modelAndView.addObject("categories", categories);
        }

        User user = (User) session.getAttribute("user");
        //该变量用于记录购物车中商品的个数（未登录状态默认为0）
        int cartTotalItemNumber = 0;
        if (user != null) {
            List<OrderItem> orderItems = orderItemService.listByUid(user.getId());
            for (OrderItem orderItem : orderItems) {
                cartTotalItemNumber += orderItem.getNumber();
            }
        }
        modelAndView.addObject("cartTotalItemNumber", cartTotalItemNumber);
    }
}

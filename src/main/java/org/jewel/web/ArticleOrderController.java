package org.jewel.web;

import org.jewel.db.ArticleOrderRepository;
import org.jewel.db.ArticleRepository;
import org.jewel.model.ArticleOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
public class ArticleOrderController {

    @Autowired
    private ArticleOrderRepository orderRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping(path = "/orders")
    public String getOrdersList(ModelMap modelMap) {
        List<ArticleOrder> orderList = orderRepository.findAllOrders();


        modelMap.addAttribute("orderList", orderList);
        return "orderList";
    }

    @GetMapping(path = "/order/add")
    public String addOrderGet(ModelMap modelMap) {
        ArticleOrder order = new ArticleOrder();
        modelMap.addAttribute("order",order);
        modelMap.addAttribute("articleList", articleRepository.findAllArticles());
        return "addOrder";
    }

    @PostMapping(path = "/order/add")
    public String addOrderPost(
                               @ModelAttribute("order")
                                           ArticleOrder order)
    {
        order.setAddOrderDate(LocalDate.now());
     order.setExpectedDate(LocalDate.now().plusDays(30));
        orderRepository.save(order);

        return "redirect:/orders";
    }
}

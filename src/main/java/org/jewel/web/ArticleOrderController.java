package org.jewel.web;

import org.jewel.db.*;
import org.jewel.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ArticleOrderController {

    @Autowired
    private ArticleOrderRepository orderRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleInOrderRepository articleInOrderRepository;

    @Autowired
    private PriorityRepository priorityRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping(path = "/orders")
    public String getOrdersList(ModelMap modelMap) {
        List<ArticleOrder> orderList = orderRepository.findAllOrders();


        modelMap.addAttribute("orderList", orderList);
        return "orderList";
    }

    @GetMapping(path = "/order/add")
    public String addOrderGet(ModelMap modelMap) {
        ArticleOrder order = new ArticleOrder();
        List<Priority> priorities = new ArrayList<>();
        for (Priority p : priorityRepository.findAll()) {
            priorities.add(p);
        }
        List<Customer> customerList = new ArrayList<>();
        for (Customer customer : customerRepository.findAll()) {
            customerList.add(customer);
        }
        modelMap.addAttribute("order", order);
        modelMap.addAttribute("articleList", articleRepository.findAllArticles());
        modelMap.addAttribute("priorityList", priorities);
        modelMap.addAttribute("customersList", customerList );
        return "addOrder";
    }

    @PostMapping(path = "/order/add")
    public String addOrderPost(
            @ModelAttribute("order")
                    ArticleOrder order) {
        order.setAddOrderDate(LocalDate.now());
        order.setExpectedDate(LocalDate.now().plusDays(30));
        order.setOrderCondition(OrderCondition.ADDED);
        List<ArticleInOrder> articlesInOrder = new ArrayList<>();

        for (Article a:order.getArticles())
        {
            ArticleInOrder articleInOrder = new ArticleInOrder();
            articleInOrder.setArticleOrder(order.getOrderNumber());
            articleInOrder.setArticle(a);
            articleInOrder.setCount(1);
            articlesInOrder.add(articleInOrderRepository.save(articleInOrder));
        }
        order.setArticleInOrder(articlesInOrder);
        orderRepository.save(order);

        return "redirect:/orders";
    }
}

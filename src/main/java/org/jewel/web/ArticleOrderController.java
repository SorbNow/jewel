package org.jewel.web;

import org.jewel.db.*;
import org.jewel.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("order")
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

    private List<Priority> getPriorityList() {
        List<Priority> priorities = new ArrayList<>();
        for (Priority p : priorityRepository.findAll()) {
            priorities.add(p);
        }
        return priorities;
    }

    private List<Customer> getCustomerList() {
        List<Customer> customerList = new ArrayList<>();
        for (Customer customer : customerRepository.findAll()) {
            customerList.add(customer);
        }
        return customerList;
    }

    @GetMapping(path = "/orders")
    public String getOrdersList(ModelMap modelMap) {
        List<ArticleOrder> orderList = orderRepository.findAllOrders();


        modelMap.addAttribute("orderList", orderList);
        return "orderList";
    }

    @GetMapping(path = "/order/add")
    public String addOrderGet(ModelMap modelMap) {
        ArticleOrder order = new ArticleOrder();
        modelMap.addAttribute("order", order);
        modelMap.addAttribute("articleList", articleRepository.findAllArticles());
        modelMap.addAttribute("priorityList", getPriorityList());
        modelMap.addAttribute("customersList", getCustomerList() );
        return "addOrder";
    }

    @PostMapping(path = "/order/add")
    public String addOrderPost( RedirectAttributes redirectAttributes,
            ModelMap mod,
            @Validated
            @ModelAttribute("order")
                    ArticleOrder order,
                                BindingResult validationResult) {

        if (validationResult.hasErrors()) {

            List<Priority> priorities = new ArrayList<>();
            for (Priority p : priorityRepository.findAll()) {
                priorities.add(p);
            }
            List<Customer> customerList = new ArrayList<>();
            for (Customer customer : customerRepository.findAll()) {
                customerList.add(customer);
            }
            mod.addAttribute("articleList", articleRepository.findAllArticles());
            mod.addAttribute("priorityList", priorities);
            mod.addAttribute("customersList", customerList );
            return "addOrder";
        }
        order.setAddOrderDate(LocalDate.now());
        order.setExpectedDate(LocalDate.now().plusDays(30));
        order.setOrderCondition(OrderCondition.ADDED);
        List<ArticleInOrder> articlesInOrder = new ArrayList<>();

        for (Article a:order.getArticles())
        {
            ArticleInOrder articleInOrder = new ArticleInOrder();
            articleInOrder.setArticleOrder(order.getOrderNumber());
            articleInOrder.setArticle(a.getArticleName());
            articleInOrder.setCount(1);
//            articleInOrder.setDummyForUnique(order.getOrderNumber()+a.getArticleName());
            articlesInOrder.add(articleInOrderRepository.save(articleInOrder));
        }
        order.setArticleInOrder(articlesInOrder);
        mod.addAttribute("order",orderRepository.save(order));

        //mod.addAttribute("order",order.getOrderNumber());
//        mod.addAttribute("articles",articleInOrderRepository.findArticleInOrdersByArticleOrder(order.getOrderNumber()));
        mod.addAttribute("articles2",order.getArticleInOrder());

        redirectAttributes.addAttribute("orderId", order.getOrderId());

        return "redirect:/order/articlesCount";
    }

    @PostMapping(path = "/order/articlesCount")
    public String saveCount (@ModelAttribute("order")
                             ArticleOrder order) {
//        ArticleOrder order = orderRepository.findArticleOrderByOrderNumber(articles.get(0).getArticleOrder());
//        order.setArticleInOrder(articles);
        for (ArticleInOrder a:order.getArticleInOrder()) {
            articleInOrderRepository.save(a);
        }
//        articleInOrderRepository.saveAll(order.getArticleInOrder());
//        orderRepository.save(order);
        return "redirect:/orders";
    }

    @GetMapping(path = "/order/delete/{orderId}")
    public String deleteArticle(@PathVariable(name = "orderId") long orderId) {
        ArticleOrder order= orderRepository.findArticleOrderByOrderId(orderId);
        List<ArticleInOrder> articleInOrderList = articleInOrderRepository.findArticleInOrdersByArticleOrder(order.getOrderNumber());
        for (ArticleInOrder a:articleInOrderList) {
            articleInOrderRepository.delete(a);
        }
        orderRepository.delete(order);
        return "redirect:/articles";
    }

    @GetMapping(path = "/order/{orderId}")
    public String editOrder(ModelMap modelMap,
                              @PathVariable(name = "orderId") long orderId) {
        ArticleOrder order= orderRepository.findArticleOrderByOrderId(orderId);
        List<ArticleInOrder> articleInOrderList = articleInOrderRepository.findArticleInOrdersByArticleOrder(order.getOrderNumber());
        modelMap.addAttribute("order", order);
        modelMap.addAttribute("articleList", articleRepository.findAllArticles());
        modelMap.addAttribute("priorityList", getPriorityList());
        modelMap.addAttribute("customersList", getCustomerList() );
        return "editOrder";
    }

    @PostMapping(path = "/order/{orderId}")
    public String editOrderPost(ModelMap modelMap,
                                @PathVariable(name = "orderId") long orderId,
                                RedirectAttributes redirectAttributes,
                                @ModelAttribute("order")
                                        ArticleOrder order) {

        List<ArticleInOrder> articlesInOrder = order.getArticleInOrder();
        for (Article a:order.getArticles()) {
            if (articleInOrderRepository.findArticleInOrderByArticleAndArticleOrder(a.getArticleName(), order.getOrderNumber()) ==null) {

                ArticleInOrder articleInOrder = new ArticleInOrder();
                articleInOrder.setArticleOrder(order.getOrderNumber());
                articleInOrder.setArticle(a.getArticleName());
                articleInOrder.setCount(1);
                articlesInOrder.add(articleInOrderRepository.save(articleInOrder));
            }
        }
        orderRepository.save(order);
        redirectAttributes.addAttribute("orderId",order.getOrderId());
        return "redirect:/order/articlesCount";
    }

    @GetMapping(path = "/order/articlesCount")
    public String setCountGet ( ModelMap modelMap,
            @ModelAttribute("orderId")
                               long orderId) {
        ArticleOrder order = orderRepository.findArticleOrderByOrderId(orderId);
        modelMap.addAttribute("order" ,order);
        return "countArticlesInOrder";

    }
}
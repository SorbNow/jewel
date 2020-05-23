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
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    @Autowired
    private ArticleDoneDateAndCountRepository doneRepository;

    @ModelAttribute("approveForms")
    public ApproveCountList createForms() {
        ApproveCountList approveCountForms = new ApproveCountList();
        return approveCountForms;
    }
   /* @ModelAttribute("approveForm")
    public ApproveCountForm createForm() {

        ApproveCountForm form= new ApproveCountForm();
        form.setArticleName("");
        form.setDate(LocalDate.now());
        form.setCount(0);
        return form;
    }*/

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
        DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        modelMap.addAttribute("orderList", orderList);
        modelMap.addAttribute("dateFormatter", europeanDateFormatter);
        modelMap.addAttribute("today", LocalDate.now());
        modelMap.addAttribute("days", ChronoUnit.DAYS);
        modelMap.addAttribute("conAdded", OrderCondition.ADDED);
        modelMap.addAttribute("conCanceled", OrderCondition.CANCELED);
        modelMap.addAttribute("conMolded", OrderCondition.MOLDED);
        modelMap.addAttribute("conProcess", OrderCondition.PROCESSING);

        return "orderList";
    }

    @GetMapping(path = "/orders2")
    public String getOrdersList2(ModelMap modelMap) {
        List<ArticleOrder> orderList = orderRepository.findAllOrders();
        DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        modelMap.addAttribute("orderList", orderList);
        modelMap.addAttribute("dateFormatter", europeanDateFormatter);
        modelMap.addAttribute("today", LocalDate.now());
        modelMap.addAttribute("days", ChronoUnit.DAYS);
        modelMap.addAttribute("conAdded", OrderCondition.ADDED);
        modelMap.addAttribute("conCanceled", OrderCondition.CANCELED);
        modelMap.addAttribute("conMolded", OrderCondition.MOLDED);
        modelMap.addAttribute("conProcess", OrderCondition.PROCESSING);

        return "oldOrderList";
    }

    @GetMapping(path = "/orders3")
    public String getOrdersList3(ModelMap modelMap) {
        List<ArticleOrder> orderList = orderRepository.findAllOrders();
        DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        modelMap.addAttribute("orderList", orderList);
        modelMap.addAttribute("dateFormatter", europeanDateFormatter);
        modelMap.addAttribute("today", LocalDate.now());
        modelMap.addAttribute("days", ChronoUnit.DAYS);
        modelMap.addAttribute("conAdded", OrderCondition.ADDED);
        modelMap.addAttribute("conCanceled", OrderCondition.CANCELED);
        modelMap.addAttribute("conMolded", OrderCondition.MOLDED);
        modelMap.addAttribute("conProcess", OrderCondition.PROCESSING);

        return "orderModal";
    }

    @GetMapping(path = "/order/add")
    public String addOrderGet(ModelMap modelMap) {
        ArticleOrder order = new ArticleOrder();
        modelMap.addAttribute("order", order);
        modelMap.addAttribute("articleList", articleRepository.findAllArticlesSorted());
        modelMap.addAttribute("priorityList", getPriorityList());
        modelMap.addAttribute("customersList", getCustomerList());
        return "addOrder";
    }

    @PostMapping(path = "/order/add")
    public String addOrderPost(RedirectAttributes redirectAttributes,
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
            mod.addAttribute("articleList", articleRepository.findAllArticlesSorted());
            mod.addAttribute("priorityList", priorities);
            mod.addAttribute("customersList", customerList);
            return "addOrder";
        }

        order.setOrderCondition(OrderCondition.ADDED);
        List<ArticleInOrder> articlesInOrder = new ArrayList<>();

        for (Article a : order.getArticles()) {
            ArticleInOrder articleInOrder = new ArticleInOrder();
            articleInOrder.setArticleOrder(order.getOrderNumber());
            articleInOrder.setArticle(a);
            articleInOrder.setCount(1);
            articleInOrder.setAddOrderDate(LocalDate.now());
            articleInOrder.setExpectedDate(LocalDate.now().plusDays(a.getProductionTime()));
//            articleInOrder.setDummyForUnique(order.getOrderNumber()+a.getArticleName());
            articlesInOrder.add(articleInOrderRepository.save(articleInOrder));
        }
        order.setArticleInOrder(articlesInOrder);
        mod.addAttribute("order", orderRepository.save(order));

        //mod.addAttribute("order",order.getOrderNumber());
//        mod.addAttribute("articles",articleInOrderRepository.findArticleInOrdersByArticleOrder(order.getOrderNumber()));
        mod.addAttribute("articles2", order.getArticleInOrder());

        redirectAttributes.addAttribute("orderId", order.getOrderId());

        return "redirect:/order/articlesCount";
    }

    @PostMapping(path = "/order/articlesCount")
    public String saveCount(@ModelAttribute("order")
                                    ArticleOrder order) {
//        ArticleOrder order = orderRepository.findArticleOrderByOrderNumber(articles.get(0).getArticleOrder());
//        order.setArticleInOrder(articles);
        for (ArticleInOrder a : order.getArticleInOrder()) {
            if (a.getExpectedDate() == null) {
                LocalDate date = LocalDate.now();
                LocalDate molded = LocalDate.now();
                for (ArticleInOrder articleInOrder : order.getArticleInOrder()) {
                    if (articleInOrder.getAddOrderDate() != null) {
                        date = articleInOrder.getAddOrderDate();
                        molded = articleInOrder.getMoldedDate();
                    }
                }
                a.setAddOrderDate(date);
                a.setExpectedDate(date.plusDays(a.getArticle().getProductionTime()));
                if (order.getOrderCondition() == OrderCondition.MOLDED) {
                    a.setMoldedDate(molded);
                    a.setExpectedDateFromMolded(molded.plusDays(a.getArticle().getProductionTimeFromMolded()));
                }
            }
            articleInOrderRepository.save(a);
        }
//        articleInOrderRepository.saveAll(order.getArticleInOrder());
//        orderRepository.save(order);
        return "redirect:/orders";
    }

    @GetMapping(path = "/order/delete/{orderId}")
    public String deleteArticle(@PathVariable(name = "orderId") long orderId) {
        ArticleOrder order = orderRepository.findArticleOrderByOrderId(orderId);
        List<ArticleInOrder> articleInOrderList = articleInOrderRepository.findArticleInOrdersByArticleOrder(order.getOrderNumber());
        orderRepository.delete(order);
        for (ArticleInOrder a : articleInOrderList) {
            articleInOrderRepository.delete(a);
        }
        return "redirect:/orders";
    }

    @GetMapping(path = "/order/{orderId}")
    public String editOrder(ModelMap modelMap,
                            @PathVariable(name = "orderId") long orderId) {
        ArticleOrder order = orderRepository.findArticleOrderByOrderId(orderId);
        List<ArticleInOrder> articleInOrderList = articleInOrderRepository.findArticleInOrdersByArticleOrder(order.getOrderNumber());
        modelMap.addAttribute("order", order);
        modelMap.addAttribute("articleList", articleRepository.findAllArticlesSorted());
        modelMap.addAttribute("priorityList", getPriorityList());
        modelMap.addAttribute("customersList", getCustomerList());
        return "editOrder";
    }

    @PostMapping(path = "/order/{orderId}")
    public String editOrderPost(ModelMap modelMap,
                                @PathVariable(name = "orderId") long orderId,
                                RedirectAttributes redirectAttributes,
                                @ModelAttribute("order")
                                        ArticleOrder order) {

        List<ArticleInOrder> articlesInOrder = order.getArticleInOrder();
        for (Article a : order.getArticles()) {
            if (articleInOrderRepository.findArticleInOrderByArticleAndArticleOrder(a, order.getOrderNumber()) == null) {

                ArticleInOrder articleInOrder = new ArticleInOrder();
                articleInOrder.setArticleOrder(order.getOrderNumber());
                articleInOrder.setArticle(a);
                articleInOrder.setCount(1);
                articlesInOrder.add(articleInOrderRepository.save(articleInOrder));
                orderRepository.save(order);
            }
        }
        List<ArticleInOrder> articleInOrderList = order.getArticleInOrder();
        List<ArticleInOrder> articleInOrderToDelete = new ArrayList<>();
        for (int i = 0; i < articleInOrderList.size(); i++) {
            boolean isInOrder = false;
            for (Article a : order.getArticles()) {
                if (a.getArticleName().equals(articleInOrderList.get(i).getArticle().getArticleName()) &&
                        a.getMetalType().getHallmark() == articleInOrderList.get(i).getArticle().getMetalType().getHallmark() &&
                        a.getMetalType().getMetalTypeName().equals(articleInOrderList.get(i).getArticle().getMetalType().getMetalTypeName())) {
                    isInOrder = true;
                    break;
                }
            }
            if (!isInOrder) {

                articleInOrderToDelete.add(articleInOrderList.get(i));
                articleInOrderList.remove(articleInOrderList.get(i));
                order.setArticleInOrder(articleInOrderList);
                i--;

//               orderRepository.save(order);
//                articleInOrderRepository.delete(ArticleInOrderToDelete);
            }
        }
        /*order.setArticleInOrder(articleInOrderList);*/
        orderRepository.save(order);
        if (!articleInOrderToDelete.isEmpty()) {
            for (ArticleInOrder articleInOrderInd : articleInOrderToDelete)
                articleInOrderRepository.delete(articleInOrderInd);
        }
        redirectAttributes.addAttribute("orderId", order.getOrderId());
        return "redirect:/order/articlesCount";
    }

    @GetMapping(path = "/order/articlesCount")
    public String setCountGet(ModelMap modelMap,
                              @ModelAttribute("orderId")
                                      long orderId) {
        ArticleOrder order = orderRepository.findArticleOrderByOrderId(orderId);
        modelMap.addAttribute("order", order);
        return "countArticlesInOrder";

    }

    @GetMapping(path = "/order/changeOrderStatus/{orderId}")
    public String editOrder(@PathVariable(name = "orderId") long orderId,
                            RedirectAttributes redirectAttributes) {
        ArticleOrder order = orderRepository.findArticleOrderByOrderId(orderId);
        OrderCondition o = order.getOrderCondition();
        if (o == OrderCondition.MOLDED) {
            for (ArticleInOrder articleInOrder : order.getArticleInOrder()) {
                if (articleInOrder.getCount() - articleInOrder.getDoneCount() > 0) {
                    redirectAttributes.addAttribute("orderName", order);
                    return "redirect:/order/approve";
                }
            }
        }
        if (o == OrderCondition.FINISHED) {
            order.setFinishedDate(LocalDate.now());
        }
        if (o == OrderCondition.ADDED) {
            for (ArticleInOrder a : order.getArticleInOrder()) {
                a.setMoldedDate(LocalDate.now());
                a.setExpectedDateFromMolded(a.getMoldedDate().plusDays(a.getArticle().getProductionTimeFromMolded()));
            }
        }
        order.setOrderCondition(o.changeConditionToNext(o));
        orderRepository.save(order);

        return "redirect:/orders";
    }

    @GetMapping(path = "/order/approve")
    public String approveArticlesGet(ModelMap modelMap,
                                     @ModelAttribute("orderName") ArticleOrder order
    ) {

        ApproveCountList approveCountList = new ApproveCountList();
        List<ApproveCountForm> approveCountFormList = new ArrayList<>();
        for (ArticleInOrder a : order.getArticleInOrder()) {
            ArticleInOrder articleInOrder = articleInOrderRepository.findArticleInOrderByArticleInOrderId(a.getArticleInOrderId());
            List<ArticleDoneDateAndCount> dateAndCount = a.getDoneDateAndCount();
           /* if (a.getDoneDateAndCount() == null || a.getDoneDateAndCount().isEmpty()) {
                ArticleDoneDateAndCount articleDoneDateAndCount = new ArticleDoneDateAndCount();
                articleDoneDateAndCount.setDoneDate(LocalDate.now());
                articleDoneDateAndCount.setCount(0);
                dateAndCount.add(articleDoneDateAndCount);
                a.setDoneDateAndCount(dateAndCount);
            }*/
            ApproveCountForm form = new ApproveCountForm();
            form.setArticleName(a.getArticle().getArticleName());
            form.setCount(0);
            form.setDate(LocalDate.now());
            form.setMetalType(a.getArticle().getMetalType());
            approveCountFormList.add(form);
        }
        approveCountList.setApproveCountFormList(approveCountFormList);
        modelMap.addAttribute("order", order);
        modelMap.addAttribute("format", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        modelMap.addAttribute("forms", approveCountList);
        return "approveToOrder";
    }

    @PostMapping(path = "/order/approve")
    public String approveArticlesPost(ModelMap modelMap,
                                      @ModelAttribute("order")
                                              ArticleOrder order,
                                      @ModelAttribute("forms")
                                              ApproveCountList approveCountForms,
                                      BindingResult validationResult) {
        boolean isDone = true;
        for (ArticleInOrder a : order.getArticleInOrder()) {

            for (ApproveCountForm approveForm : approveCountForms.getApproveCountFormList()) {
                if (approveForm.getArticleName().equals(a.getArticle().getArticleName()) &&
                        approveForm.getMetalType().getHallmark() == a.getArticle().getMetalType().getHallmark() &&
                        approveForm.getMetalType().getMetalTypeName().equals(a.getArticle().getMetalType().getMetalTypeName())) {
                    List<ArticleDoneDateAndCount> dateAndCount = a.getDoneDateAndCount();
                    boolean isFound = false;
                    for (ArticleDoneDateAndCount articleDoneDateAndCount:a.getDoneDateAndCount()) {

                        if (articleDoneDateAndCount.getDoneDate().isEqual(approveForm.getDate()) && approveForm.getCount()!=0) {

                            articleDoneDateAndCount.setCount(articleDoneDateAndCount.getCount()+approveForm.getCount());
                            doneRepository.save(articleDoneDateAndCount);
                            isFound=true;

                        }

                    }
                    if(!isFound && approveForm.getCount()!=0) {

                        ArticleDoneDateAndCount doneDateAndCount = new ArticleDoneDateAndCount();
                        doneDateAndCount.setDoneDate(approveForm.getDate());
                        doneDateAndCount.setCount(approveForm.getCount());

                        dateAndCount.add(doneDateAndCount);
                        doneRepository.save(doneDateAndCount);
                        a.setDoneDateAndCount(dateAndCount);
                        articleInOrderRepository.save(a);

                    } else {break;}
                    a.setDoneCount(a.getDoneCount() + approveForm.getCount());
                    if (a.getLastDate()!=null && approveForm.getDate().isAfter(a.getLastDate())) {
                        a.setLastDate(approveForm.getDate());
                    }
                }
            }

            isDone = a.getCount() == a.getDoneCount();
            articleInOrderRepository.save(a);
        }
        if (isDone) {
            order.setOrderCondition(order.getOrderCondition().changeConditionToNext(order.getOrderCondition()));
            orderRepository.save(order);
        }
        orderRepository.save(order);
        return "redirect:/orders";
    }

    @GetMapping(path = "order/rollback/{type}/{orderId}")
    public String rollbackWithoutSave(@PathVariable(name = "type") String type, @PathVariable(name = "orderId") long orderId) {
        ArticleOrder order = orderRepository.findArticleOrderByOrderId(orderId);
        if (type.equals("save") || type.equals("clear")) {
            if (type.equals("clear")) {
                for (ArticleInOrder a : order.getArticleInOrder()) {
                    for (ArticleDoneDateAndCount articleDoneDateAndCount:a.getDoneDateAndCount()) {
                        doneRepository.delete(articleDoneDateAndCount);
                    }
                    a.getDoneDateAndCount().clear();
                    a.setDoneCount(0);
                    a.setLastDate(null);

                }
            }
            order.setOrderCondition(order.getOrderCondition().changeConditionToPrevious(order.getOrderCondition()));
            orderRepository.save(order);
        }
        return "redirect:/orders";
    }
}

package org.jewel.web;

import org.jewel.db.ProductionOrderRepository;
import org.jewel.db.ArticleRepository;
import org.jewel.model.Article;
import org.jewel.model.ArticleInOrder;
import org.jewel.model.ProductionOrder;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("simpleUser")
@TestPropertySource("/application.properties")
@Sql(value = {"/create_customer_before.sql", "/create_user_before.sql", "/create_priority_before.sql", "/create_metal_type_before.sql", "/create_article_before.sql", "/create_order_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create_user_before.sql", "/create_order_after.sql", "/create_article_after.sql", "/create_metal_type_after.sql", "/create_priority_after.sql", "/create_customer_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ProductionOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductionOrderRepository productionOrderRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @WithAnonymousUser
    void notAuthorizedTest() throws Exception {
        this.mockMvc.perform(get("/orders"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login-page"));
    }

    @Test
    @WithUserDetails("simpleUser")
    void accessDeniedTest() throws Exception {
        this.mockMvc.perform(get("/orders"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getOrdersList() throws Exception {

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Приор.")))
                .andExpect(content().string(containsString("#2")));
    }

    @Test
    void getOrdersList2() throws Exception {
        mockMvc.perform(get("/orders2"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Приор.")))
                .andExpect(content().string(containsString("#1")));
    }

    @Test
    void addOrderGet() throws Exception {
        mockMvc.perform(get("/order/add"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Подразделение, оформившее заказ:")));
    }

    @Test
    void addOrderPostAndSave() throws Exception {
        ProductionOrder productionOrder = new ProductionOrder();
        productionOrder.setOrderNumber("#3");
        mockMvc.perform(post("/order/add")
                .flashAttr("order", productionOrder))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Выберите минимум 1 артикул")));
        List<Article> articles = new ArrayList<>();
        productionOrder.setArticles(articles);

        mockMvc.perform(post("/order/add")
                .flashAttr("order", productionOrder))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Список артикулов не может быть пустым")));

        articles.add(articleRepository.findArticleByArticleId(1));
        articles.add(articleRepository.findArticleByArticleId(2));

        mockMvc.perform(post("/order/add")
                .flashAttr("order", productionOrder))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/articlesCount?orderId=" + productionOrderRepository.findProductionOrderByOrderNumber("#3").getOrderId()));

        Assert.assertNotNull(productionOrderRepository.findProductionOrderByOrderNumber("#3"));

        mockMvc.perform(get("/order/articlesCount")
                .flashAttr("orderId", productionOrderRepository.findProductionOrderByOrderNumber("#3").getOrderId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(articleRepository.findArticleByArticleId(2).getArticleName())));

        ProductionOrder order = productionOrderRepository.findProductionOrderByOrderNumber("#3");




  // write normal page for adding count! Проверка не будет нормально работать, пока не сделаешь нормально страницу с динамическим расширением таблицы




        /*for (ArticleInOrder a : order.getArticleInOrder()) {
            a.setCount(0);
        }
        mockMvc.perform(post("/order/articlesCount")
                .flashAttr("order", order))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Значение должно быть положительным")));

*/
        for (ArticleInOrder a : order.getArticleInOrder()) {
            a.setCount(1);
        }

        mockMvc.perform(post("/order/articlesCount")
                .flashAttr("order", order))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders"));
    }

    @Test
    void deleteOrder() throws Exception {

        mockMvc.perform(get("/order/delete/{orderId}",1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders"));
        Assert.assertNull(productionOrderRepository.findProductionOrderByOrderId(1));

    }

    @Test
    void editOrder() {
    }

    @Test
    void editOrderPost() {
    }

    @Test
    void approveArticlesGet() {
    }

    @Test
    void approveArticlesPost() {
    }

    @Test
    void rollbackWithoutSave() {
    }


}
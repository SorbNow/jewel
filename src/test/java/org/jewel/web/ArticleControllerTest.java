package org.jewel.web;

import org.jewel.db.ArticleRepository;
import org.jewel.db.MetalTypeRepository;
import org.jewel.model.Article;
import org.jewel.model.MetalType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc

@TestPropertySource("/application.properties")
//@ActiveProfiles("test")
@Sql(value = {"/create_article_before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create_article_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails("master")
class ArticleControllerTest {
   /* @BeforeEach
    void setUp() {
        MetalType metalType = new MetalType();
        metalType.setHallmark(585);
        metalType.setMetalTypeName("gold");
        metalTypeRepository.save(metalType);
        Article article = new Article();
        article.setMetalType(metalType);
        article.setArticleName("test-article-name");
        articleRepository.save(article);
    }

    @AfterEach
    void tearDown() {

    }*/
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleController articleController;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MetalTypeRepository metalTypeRepository;

    @Test
    @WithAnonymousUser
    void accessDeniedTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login-page"));
    }

    @Test
    void articlesListTest() throws Exception {
        this.mockMvc.perform(get("/articles"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Артикул")));
    }

    @Test
    void addNewArticle() throws Exception {
        MetalType metalType = new MetalType();
        metalType.setHallmark(585);
        metalType.setMetalTypeName("gold");
        metalTypeRepository.save(metalType);
        Article article = new Article();
        article.setMetalType(metalType);
        article.setArticleName("test-article-name");
        this.mockMvc.perform(post("/article/add")
                .flashAttr("article", article))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/articles"));
//        verify(articleRepository,times(1)).save(any(Article.class));
        assertEquals(article.getDummyArticleName(), article.getArticleName() + article.getMetalType().getMetalTypeName() + article.getMetalType().getHallmark());


        article.setMetalType(metalTypeRepository.findMetalTypeById(1));
        article.setArticleName("#1");
        this.mockMvc.perform(post("/article/add")
                .flashAttr("article", article))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void addExistArticle() throws Exception {
        Article article = new Article();
        article.setMetalType(metalTypeRepository.findMetalTypeById(1));
        article.setArticleName("#1");
        this.mockMvc.perform(post("/article/add")
                .flashAttr("article", article))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Артикул " + article.getArticleName() + " уже есть в базе.")));
    }

    @Test
    void addArticleWithExistMetalType() throws Exception {
        Article article = new Article();
        article.setMetalType(metalTypeRepository.findMetalTypeById(1));
        article.setArticleName("test");
        this.mockMvc.perform(post("/article/add")
                .flashAttr("article", article))
                .andDo(print())
                .andExpect(status().isFound());
    }
    @Test
    void addArticleWithBlankArticleName() throws Exception {
        Article article = new Article();
        article.setMetalType(metalTypeRepository.findMetalTypeById(1));
        article.setArticleName("");
        this.mockMvc.perform(post("/article/add")
                .flashAttr("article", article))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void addArticleWithoutArticleName() throws Exception {
        Article article = new Article();
        article.setMetalType(metalTypeRepository.findMetalTypeById(1));
        this.mockMvc.perform(post("/article/add")
                .flashAttr("article", article))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void addArticleWithoutAnyFields() throws Exception {
        Article article = new Article();
        this.mockMvc.perform(post("/article/add")
                .flashAttr("article", article))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void addArticleWithNullMetalType() throws Exception {

        Article article = new Article();
        article.setArticleName("test-article-name");
        this.mockMvc.perform(post("/article/add")
                .flashAttr("article", article))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void editArticleGetPage() throws Exception {
   /*     MetalType metalType = new MetalType();
        metalType.setHallmark(585);
        metalType.setMetalTypeName("gold");
        metalTypeRepository.save(metalType);
        Article article = new Article();
        article.setMetalType(metalType);
        article.setArticleName("test-article-name");
        articleService.saveArticle(article);*/
        List<Article> articles = articleRepository.findAllArticles();
        System.out.println(articles);
        System.out.println("size: " + articles.size());
        List<MetalType> metalTypes = metalTypeRepository.findAllMetalTypes();
        System.out.println(metalTypes);
        mockMvc.perform(get("/article/{articleId}", 1L))
                .andDo(print())
                .andExpect(status().isOk());
//                .andExpect((jsonPath("$.articleId", is(1L))));
       /* this.mockMvc.perform(post("/article/{id}", 1))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
*/

    }

    @Test
    void deleteArticle() throws Exception {

        boolean isExistBeforeDelete = articleRepository.findById(1L).isPresent();
        mockMvc.perform(get("/article/delete/{articleId}",1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/articles"));
        boolean isExistAfterDelete = articleRepository.findById(1L).isPresent();

        assertTrue(isExistBeforeDelete);
        assertFalse(isExistAfterDelete);
    }
}
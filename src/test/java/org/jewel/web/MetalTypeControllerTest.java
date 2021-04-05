package org.jewel.web;

import org.jewel.db.MetalTypeRepository;
import org.jewel.model.MetalType;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("master")
@TestPropertySource("/application.properties")
@Sql(value = {"/create_metal_type_before.sql", "/create_user_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create_metal_type_after.sql", "/create_user_before.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class MetalTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MetalTypeRepository metalTypeRepository;

    @Test
    @WithAnonymousUser
    void notAuthorizedTest() throws Exception {
        this.mockMvc.perform(get("/article/metalTypes"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login-page"));
    }

    @Test
    @WithUserDetails("simpleUser")
    void accessDeniedTest() throws Exception {
        this.mockMvc.perform(get("/article/metalTypes"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void metalTypePage() throws Exception {
        mockMvc.perform(get("/article/metalTypes"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Проба")));
    }

    @Test
    void addMetalTypeGet() throws Exception {
        mockMvc.perform(get("/article/metalType/add"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Название :")));
    }

    @Test
    void addMetalTypePost() throws Exception {
        MetalType metalType = new MetalType();
        metalType.setHallmark(-3);
        metalType.setMetalTypeName("gold");
        mockMvc.perform(post("/article/metalType/add")
                .flashAttr("metalType", metalType))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Поле обязательно для заполнения. Может иметь только положительные значения")));

        metalType.setHallmark(0);
        mockMvc.perform(post("/article/metalType/add")
                .flashAttr("metalType", metalType))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Поле обязательно для заполнения. Может иметь только положительные значения")));

        metalType.setHallmark(750);
        mockMvc.perform(post("/article/metalType/add")
                .flashAttr("metalType", metalType))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Такая запись уже есть в базе")));

        metalType.setHallmark(950);
        mockMvc.perform(post("/article/metalType/add")
                .flashAttr("metalType", metalType))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/article/metalTypes"));

        Assert.assertNotNull(metalTypeRepository.findMetalTypeByMetalTypeNameAndHallmark("gold", 950));


    }

    @Test
    void editMetalTypeGet() throws Exception {
        mockMvc.perform(get("/article/metalType/{id}", 2))
                .andExpect(content().string(containsString("silver")))
                .andExpect(content().string(containsString("Название")))
                .andExpect(status().isOk());

    }

    @Test
    void editMetalTypePost() throws Exception {
        MetalType metalType = metalTypeRepository.findMetalTypeById(2);
        mockMvc.perform(post("/article/metalType/{id}",2)
                .flashAttr("metalType", metalType))
                .andExpect(content().string(containsString("Такая запись уже есть в базе")))
                .andExpect(status().isOk());
        metalType.setHallmark(950);
        mockMvc.perform(post("/article/metalType/{id}",2)
                .flashAttr("metalType", metalType))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/article/metalTypes"));
    }

    @Test
    void deleteMetalType() throws Exception {

        mockMvc.perform(get("/article/metalType/delete/{id}", 2))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/article/metalTypes"));
        Assert.assertNull(metalTypeRepository.findMetalTypeById(2));
        Assert.assertNull(metalTypeRepository.findMetalTypeByMetalTypeNameAndHallmark("silver",750));
    }
}
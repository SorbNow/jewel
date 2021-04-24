package org.jewel.controller;

import org.jewel.repos.MineralRepository;
import org.jewel.model.Mineral;
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
@WithUserDetails("simpleUser")
@TestPropertySource("/application.properties")
@Sql(value = {"/create_mineral_before.sql", "/create_user_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create_mineral_after.sql", "/create_user_before.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class MineralControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MineralRepository mineralRepository;

    @Test
    @WithAnonymousUser
    void notAuthorizedTest() throws Exception {
        this.mockMvc.perform(get("/article/minerals"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login-page"));
    }

    @Test
    @WithUserDetails("simpleUser")
    void accessDeniedTest() throws Exception {
        this.mockMvc.perform(get("/article/minerals"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void mineralListPage() throws Exception {

        mockMvc.perform(get("/article/minerals"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Шифр Шемякин")))
                .andExpect(content().string(containsString("topaz")));

    }

    @Test
    void addMineralGet() throws Exception {
        mockMvc.perform(get("/article/mineral/add"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Название вставки :")));
    }

    @Test
    void addMineralPost() throws Exception {
        Mineral mineral = new Mineral();
        mineral.setInsert(" ");
        mineral.setLetterBallet("");
        mineral.setLetterChemyakin("");
        mineral.setLetterMikhailov("");
        mockMvc.perform(post("/article/mineral/add")
                .flashAttr("mineral", mineral))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Поле не может быть пустым")));

        mineral.setInsert("topaz");
        mockMvc.perform(post("/article/mineral/add")
                .flashAttr("mineral", mineral))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Такая запись уже есть в базе")));

        mineral.setInsert("Aquamarine");

        mockMvc.perform(post("/article/mineral/add")
                .flashAttr("mineral", mineral))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/article/minerals"));

        Assert.assertNotNull(mineralRepository.findMineralByInsert(mineral.getInsert()));
    }

    @Test
    void deleteMineral() throws Exception {
        mockMvc.perform(get("/article/mineral/delete/{id}",2))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/article/minerals"));

        Assert.assertNull(mineralRepository.findMineralById(2));
    }

    @Test
    void editMineralGet() throws Exception {
        mockMvc.perform(get("/article/mineral/{id}",1))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("topaz")));

    }

    @Test
    void editMineralPost() throws Exception {
        Mineral mineral = mineralRepository.findMineralById(1);
        mineral.setInsert("brilliant");

        mockMvc.perform(post("/article/mineral/{id}",1)
        .flashAttr("mineral",mineral))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Такая запись уже есть в базе")));

        mineral.setInsert("");

        mockMvc.perform(post("/article/mineral/{id}",1)
                .flashAttr("mineral",mineral))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Поле не может быть пустым")));

        mineral.setInsert("aquamarine");

        mockMvc.perform(post("/article/mineral/{id}",1)
                .flashAttr("mineral",mineral))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/article/minerals"));

    }
}
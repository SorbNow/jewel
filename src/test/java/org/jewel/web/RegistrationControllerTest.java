package org.jewel.web;

import org.hamcrest.CoreMatchers;
import org.jewel.db.UserRepository;
import org.jewel.model.UserRoles;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
@Sql(value = {"/create_user_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create_user_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails("master")
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private RegistrationForm form = new RegistrationForm();

    @Autowired
    private UserRepository userRepository;

    @Test
    void getRegistrationForm() throws Exception {
        mockMvc.perform(get("/admin/users/add")
        .flashAttr("form",form))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Логин:")));
    }

    @Test
    void processRegistrationForm() throws Exception {
        form.setLogin("master");
        mockMvc.perform(post("/admin/users/add")
                .flashAttr("form", form))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Поле пароля должно быть заполнено")))
                .andExpect(content().string(containsString("Укажите роль")));

        form.setPassword("123");
        form.setRole(UserRoles.АДМИНИСТРАТОР.toString());

        mockMvc.perform(post("/admin/users/add")
                .flashAttr("form", form))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Слишком короткий пароль")));

        form.setPassword("123123");
        form.setLogin("/");
        mockMvc.perform(post("/admin/users/add")
                .flashAttr("form", form))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Accepted only digits, letters, minus, underscore and dots")))
                .andExpect(content().string(containsString("Login should be at least 4 character length.")));

        form.setLogin("/?@#");
        mockMvc.perform(post("/admin/users/add")
                .flashAttr("form", form))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Accepted only digits, letters, minus, underscore and dots")));

        form.setLogin("userMe");

        mockMvc.perform(post("/admin/users/add")
                .flashAttr("form", form))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));

        Assert.assertNotNull(userRepository.findUserByLogin(form.getLogin()));

    }
}
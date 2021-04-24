package org.jewel.controller;

import org.jewel.repos.UserRepository;
import org.jewel.model.User;
import org.jewel.model.UserStatus;
import org.jewel.utils.ChangePasswordForm;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@Sql(value = {"/create_user_before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create_user_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    @WithAnonymousUser
    void accessDeniedTest() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login-page"));
    }

    @Test
    void editUser() throws Exception {

        this.mockMvc.perform(get("/admin/users/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Уровень доступа")))
                .andExpect(content().string(containsString(userRepository.findUserById(1).getLogin())));

    }

    @Test
    void saveEditedUser() throws Exception {
        User user = userRepository.findUserById(2);
        System.out.println("получили пользователя: " +  user.getLogin());
        user.setLogin("test");
        this.mockMvc.perform(post("/admin/users/{id}", 2)
                .flashAttr("user",user))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));
        Assert.assertEquals(user.getLogin(), userRepository.findUserById(user.getId()).getLogin());
        System.out.println("получили пользователя в конце: " +  userRepository.findUserById(user.getId()).getLogin());
    }

    @Test
    void deleteUser() throws Exception {

        this.mockMvc.perform(get("/admin/users/delete/{id}", 2))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));
        Assert.assertEquals(userRepository.findUserById(2).getStatus(), UserStatus.REMOVED);
    }

    @Test
    void clearUserPassword() throws Exception {
        User user = userRepository.findUserById(2);
        this.mockMvc.perform(get("/admin/users/{id}/clearPassword", 2))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));
        Assert.assertNotEquals(user.getEncodedPassword(),userRepository.findUserById(2).getEncodedPassword());
    }

    @Test
    void changePasswordGet() throws Exception {
        ChangePasswordForm passwordForm = new ChangePasswordForm();
        passwordForm.setNewPassword("asd123");
        passwordForm.setConfirmNewPassword("asd123");

        this.mockMvc.perform(get("/set-password")
        .flashAttr("passwordResetForm", passwordForm))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Введите пароль")));
    }

    @Test
    void changePasswordPost() throws Exception {
        ChangePasswordForm passwordForm = new ChangePasswordForm();
        passwordForm.setNewPassword("asd123123");
        passwordForm.setConfirmNewPassword("asd123123");
        User user = userRepository.findUserByLogin("master");

        this.mockMvc.perform(post("/set-password")
                .flashAttr("passwordResetForm", passwordForm))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Assert.assertNotEquals(user.getEncodedPassword(),userRepository.findUserByLogin("master").getEncodedPassword());



    }
}
package org.jewel.controller;

import org.hamcrest.Matchers;
import org.jewel.repos.PriorityRepository;
import org.jewel.model.Priority;
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
@TestPropertySource("/application.properties")
@Sql(value = {"/create_priority_before.sql", "/create_user_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create_priority_after.sql", "/create_user_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails("master")
class PriorityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PriorityRepository repository;

    @Test
    @WithAnonymousUser
    void notAuthorizedTest() throws Exception {
        this.mockMvc.perform(get("/admin/customers"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login-page"));
    }

    @Test
    @WithUserDetails("simpleUser")
    void accessDeniedTest() throws Exception {
        this.mockMvc.perform(get("/admin/customers"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void addPriority() throws Exception {
        mockMvc.perform(get("/admin/priority/add"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Краткое описание :")));
    }

    @Test
    void addPriorityPost() throws Exception {
        Priority priority = new Priority();
        priority.setDescription("");
        priority.setPriorityType("");

        mockMvc.perform(post("/admin/priority/add")
                .flashAttr("priority", priority))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Поле не может быть пустым")))
                .andExpect(content().string(containsString("Поле не можеть быть короче 4 символов")));

        priority.setDescription("12");
        priority.setPriorityType("AA");

        mockMvc.perform(post("/admin/priority/add")
                .flashAttr("priority", priority))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Поле не можеть быть короче 4 символов")));

        priority.setDescription("Test desc");

        mockMvc.perform(post("/admin/priority/add")
                .flashAttr("priority", priority))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Такая запись уже есть в базе")));

        priority.setPriorityType("BC");

        mockMvc.perform(post("/admin/priority/add")
                .flashAttr("priority", priority))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/priorities"));

        Assert.assertNotNull(repository.findPriorityByPriorityType(priority.getPriorityType()));

    }

    @Test
    void getPrioritiesList() throws Exception {
        mockMvc.perform(get("/admin/priorities"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allPrioritiesList", Matchers.hasSize(3)))
                .andExpect(content().string(containsString("Подробное описание")));

    }

    @Test
    void editPriorityGet() throws Exception {
        mockMvc.perform(get("/admin/priority/{id}", 2))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Краткое описание :")))
                .andExpect(content().string(containsString(repository.findPriorityById(2).getPriorityType())))
                .andExpect(content().string(containsString(repository.findPriorityById(2).getDescription())));
    }

    @Test
    void editPriorityPost() throws Exception {

        Priority priority = repository.findPriorityById(2);
        priority.setPriorityType("AA");
        mockMvc.perform(post("/admin/priority/{id}", 2)
                .flashAttr("priority", priority))
                .andExpect(content().string(containsString("Такая запись уже есть в базе")))
                .andExpect(status().isOk());

        priority.setPriorityType("");

        mockMvc.perform(post("/admin/priority/{id}", 2)
                .flashAttr("priority", priority))
                .andExpect(content().string(containsString("Поле не может быть пустым")))
                .andExpect(status().isOk());

        priority.setPriorityType("BC");
        priority.setDescription("");

        mockMvc.perform(post("/admin/priority/{id}", 2)
                .flashAttr("priority", priority))
                .andExpect(content().string(containsString("Поле не может быть пустым")))
                .andExpect(content().string(containsString("Поле не можеть быть короче 4 символов")))
                .andExpect(status().isOk());

        priority.setDescription("12");

           mockMvc.perform(post("/admin/priority/{id}", 2)
                .flashAttr("priority", priority))
                .andExpect(content().string(containsString("Поле не можеть быть короче 4 символов")))
                .andExpect(status().isOk());

           priority.setDescription("New desc");
        mockMvc.perform(post("/admin/priority/{id}", 2)
                .flashAttr("priority" , priority))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/priorities"));

        Assert.assertNotNull(repository.findPriorityByPriorityType("BC"));


    }

    @Test
    void deletePriority() throws Exception {
        mockMvc.perform(get("/admin/priority/delete/{id}",2))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/priorities"));

        Assert.assertNull(repository.findPriorityById(2));
    }
}
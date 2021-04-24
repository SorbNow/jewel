package org.jewel.controller;

import org.jewel.repos.CustomerRepository;
import org.jewel.model.Customer;
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
@Sql(value = {"/create_customer_before.sql", "/create_user_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create_customer_after.sql", "/create_user_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

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
    void getCustomerList() throws Exception {
        mockMvc.perform(get("/admin/customers"))
                .andDo(print())
                .andExpect(content().string(containsString("Торговое подразделение")));
    }

    @Test
    void editCustomer() throws Exception {
        mockMvc.perform(get("/admin/customer/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Город:")))
                .andExpect(content().string(containsString("Passage")));
    }

//how to test with wrong ID?
    /*@Test
    void editCustomerWithWrongId() throws Exception {
        mockMvc.perform(get("/admin/customer/{id}",100))
                .andDo(print())
                .andExpect(content().string(containsString("Город:")));
    }*/

    @Test
    void saveCustomerWithExistingName() throws Exception {
        Customer customer = customerRepository.findCustomerById(2);
        customer.setCustomerName("Passage");
        mockMvc.perform(post("/admin/customer/{id}", 2)
                .flashAttr("customer", customer))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Подразделение " + customer.getCustomerName() + " уже есть в базе.")));
    }

    @Test
    void saveCustomerWithExistingCity() throws Exception {
        Customer customer = customerRepository.findCustomerById(2);
        customer.setCustomerCity("EKB");
        mockMvc.perform(post("/admin/customer/{id}", 2)
                .flashAttr("customer", customer))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/customers"));
        Assert.assertEquals("EKB", customerRepository.findCustomerById(2).getCustomerCity());
    }

    @Test
    void saveCustomer() throws Exception {
        Customer customer = customerRepository.findCustomerById(2);
        customer.setCustomerName("Some");
        mockMvc.perform(post("/admin/customer/{id}", 2)
                .flashAttr("customer", customer))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/customers"));
        Assert.assertEquals("Some", customerRepository.findCustomerById(2).getCustomerName());
    }

    @Test
    void deleteCustomer() throws Exception {
        mockMvc.perform(get("/admin/customer/delete/{id}", 2))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/customers"));
        Assert.assertNull(customerRepository.findCustomerById(2));
        Assert.assertNull(customerRepository.findCustomerByCustomerName("Kutuzovsky"));
    }

    @Test
    void addCustomer() throws Exception {
        mockMvc.perform(get("/admin/customer/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Торговое подразделение:")));
    }

    @Test
    void addCustomerWithAttribute() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerName("GO Europe");
        customer.setCustomerCity("SPB");
        mockMvc.perform(get("/admin/customer/add")
        .flashAttr("customer", customer))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Торговое подразделение:")));
    }

    @Test
    void addCustomerPost() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerName("GO Europe");
        customer.setCustomerCity("SPB");
        mockMvc.perform(post("/admin/customer/add")
                .flashAttr("customer", customer))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/customers"));
    }

    @Test
    void addCustomerPostWithWrongData() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerName("");
        customer.setCustomerCity("SPB");
        mockMvc.perform(post("/admin/customer/add")
                .flashAttr("customer", customer))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Поле обязательно для заполнения")));
        customer.setCustomerName("GO Europe");
        customer.setCustomerCity("");
        mockMvc.perform(post("/admin/customer/add")
                .flashAttr("customer", customer))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Поле обязательно для заполнения")));

        Assert.assertNull(customerRepository.findCustomerByCustomerName("GO Europe"));

    }
}
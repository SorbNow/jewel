package org.jewel.config;

import org.jewel.repos.CustomerRepository;
import org.jewel.repos.UserRepository;
import org.jewel.model.Customer;
import org.jewel.model.User;
import org.jewel.model.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class StartupListener {
    @Autowired
    private UserRepository userDAO;


    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private CustomerRepository customerRepository;

    @EventListener
    @Transactional
    public void applicationStarted(ContextRefreshedEvent event) {

        List<User> users = new ArrayList<>();
        for (User user : userDAO.findAll()) {
            users.add(user);
        }
        if (users.size() == 0) {
            User u = new User();
            u.setLogin("master");
            u.setEncodedPassword(encoder.encode("123"));
            u.setUserRole("АДМИНИСТРАТОР");
            u.setStatus(UserStatus.REGISTERED);
            userDAO.save(u);
        }
        List<Customer> customers = new ArrayList<>();
        for (Customer customer : customerRepository.findAll()) {
            customers.add(customer);
        }
        if (customers.size() == 0) {
            Customer cus1 = new Customer();
            cus1.setCustomerName("BGD");
            cus1.setCustomerCity("SPB");
            Customer cus2 = new Customer();
            cus2.setCustomerName("BGD2");
            cus2.setCustomerCity("SPB");
            Customer cus3 = new Customer();
            cus3.setCustomerName("BGD3");
            cus3.setCustomerCity("SPB");
            Customer cus4 = new Customer();
            cus4.setCustomerName("Дмитровка");
            cus4.setCustomerCity("Москва");
            customerRepository.save(cus1);
            customerRepository.save(cus2);
            customerRepository.save(cus3);
            customerRepository.save(cus4);
        }

    }

//    public void contextDestroyed(ServletContextEvent sce) {
}

package org.jewel.web;

import org.jewel.db.CustomerRepository;
import org.jewel.db.GroupRepository;
import org.jewel.db.UserRepository;
import org.jewel.db.UserRoleRepository;
import org.jewel.model.Customer;
import org.jewel.model.Group;
import org.jewel.model.User;
import org.jewel.model.UserRole;
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
    private GroupRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @EventListener
    @Transactional
    public void applicationStarted(ContextRefreshedEvent event) {

        if (repository.findGroupByName("admins") == null) {
            Group g1 = new Group("admins");
            Group g2 = new Group("test");
            Group g3 = new Group("test22");
            repository.save(g1);
            repository.save(g2);
            repository.save(g3);
        }
        List<User> users = new ArrayList<>();
        for (User user : userDAO.findAll()) {
            users.add(user);
        }
        if (users.size() == 0) {
            User u = new User();
            u.setLogin("master");
            u.setEncodedPassword(encoder.encode("123"));
            u.setGroup(repository.findGroupByName("admins"));
            u.setUserRole("ADMIN");
            userDAO.save(u);
        }
        List<UserRole> roles = new ArrayList<>();
        for (UserRole role: userRoleRepository.findAll())
        {
            roles.add(role);
        }
        if (roles.size() == 0) {
            UserRole role1 = new UserRole();
            role1.setRoleName("ADMIN");
            role1.setRoleDescription("role for admin users");
            UserRole role2 = new UserRole();
            role2.setRoleName("USER");
            role2.setRoleDescription("");
            userRoleRepository.save(role1);
            userRoleRepository.save(role2);
        }
        List<Customer> customers = new ArrayList<>();
        for (Customer customer: customerRepository.findAll()) {
            customers.add(customer);
        }
        if (customers.size() == 0) {
            Customer cus1 = new Customer();
            cus1.setCustomerName("BGD");
            cus1.setCustomerCity("SPB");
            Customer cus2 = new Customer();
            cus2.setCustomerName("Дмитровка");
            cus2.setCustomerCity("Москва");
            customerRepository.save(cus1);
            customerRepository.save(cus2);
        }

    }

//    public void contextDestroyed(ServletContextEvent sce) {
}

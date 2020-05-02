package org.jewel.web;

import org.jewel.db.CustomerRepository;
import org.jewel.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping(path = "/admin/customers")
    public String getCustomerList(ModelMap model) {

        List<Customer> customerList = new ArrayList<>();
        for (Customer customer : customerRepository.findAll()) {
            customerList.add(customer);
        }
        model.addAttribute("customersList", customerList);
        return "customerList";
    }

    @GetMapping(path = "/admin/customer/{id}")
    public String editCustomer(@PathVariable(name = "id") int id, ModelMap modelMap) {
        Customer customer = customerRepository.findCustomerById(id);
        modelMap.addAttribute("customer", customer);
        return "editCustomer";
    }

    @PostMapping(path = "/admin/customer/{id}")
    public String saveCustomer(@PathVariable(name = "id") int id, Customer customer, ModelMap modelMap) {
        customerRepository.save(customer);
        modelMap.addAttribute("customer",customerRepository.findAll());
        return "redirect:/admin/customers";
    }
    @GetMapping(path = "/admin/customer/delete/{id}")
    public String deleteCustomer(@PathVariable(name = "id") int id) {
        Customer customer = customerRepository.findCustomerById(id);
        customerRepository.delete(customer);
        return "redirect:/admin/customers";
    }
}

package org.jewel.web;

import org.jewel.db.CustomerRepository;
import org.jewel.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer trimParameters(Customer customer) {
        customer.setCustomerCity(customer.getCustomerCity().trim());
        customer.setCustomerName(customer.getCustomerName().trim());
        return customer;

    }

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
    public String saveCustomer(@PathVariable(name = "id") int id,
                               @Validated
                               @ModelAttribute("customer")
                                       Customer customer,
                               ModelMap modelMap,
                               BindingResult validationResult) {
        customer = trimParameters(customer);
        if (validationResult.hasErrors()) {
            return "editCustomer";
        }
        if ((customerRepository.findCustomerByCustomerName(customer.getCustomerName()) != null) &&
                (!customer.getCustomerName().equals(customerRepository.findCustomerById(id).getCustomerName()))) {
            validationResult.addError(new FieldError("customer", "customerName",
                    "Подразделение " + customer.getCustomerName() + " уже есть в базе."));
            return "editCustomer";
        }
        customerRepository.save(customer);
        modelMap.addAttribute("customer", customerRepository.findAll());
        return "redirect:/admin/customers";
    }

    @GetMapping(path = "/admin/customer/delete/{id}")
    public String deleteCustomer(@PathVariable(name = "id") int id) {
        Customer customer = customerRepository.findCustomerById(id);
        customerRepository.delete(customer);
        return "redirect:/admin/customers";
    }

    @GetMapping(path = "/admin/customer/add")
    public String addCustomer(ModelMap modelMap) {
        Customer customer = new Customer();
        modelMap.addAttribute("customer", customer);
        return "addCustomer";
    }

    @PostMapping(path = "/admin/customer/add")
    public String addCustomerPost(ModelMap modelMap,
                                  @Validated
                                  @ModelAttribute("customer")
                                          Customer customer,
                                  BindingResult validationResult) {
        customer = trimParameters(customer);
        if (validationResult.hasErrors()) {
            return "addCustomer";
        }
        if (customerRepository.findCustomerByCustomerName(customer.getCustomerName()) != null) {
            validationResult.addError(new FieldError("customer", "customerName",
                    "Подразделение " + customer.getCustomerName() + " уже есть в базе."));
            return "addCustomer";
        }
        customerRepository.save(customer);
        modelMap.addAttribute("customers", customerRepository.findAll());
        return "redirect:/admin/customers";
    }
}

package org.jewel.web;

import org.jewel.db.PriorityRepository;
import org.jewel.model.Priority;
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
public class PriorityController {
    @Autowired
    private PriorityRepository priorityRepository;

    @GetMapping(path = "/admin/priority/add")
    public String addPriority(ModelMap modelMap) {
        Priority priority = new Priority();
        modelMap.addAttribute("priority", priority);
        return "addPriority";
    }

    @PostMapping(path = "/admin/priority/add")
    public String addPriorityPost(ModelMap modelMap,
                                  @Validated
                                  @ModelAttribute("priority")
                                          Priority priority,
                                  BindingResult validationResult) {
        priority.setPriorityType(priority.getPriorityType().trim());
        priority.setDescription(priority.getDescription().trim());
        if (validationResult.hasErrors()) {
            return "addPriority";
        }
        if (priorityRepository.findPriorityByPriorityType(priority.getPriorityType()) != null) {
            validationResult.addError(new FieldError("priority", "priorityType",
                    "Такая запись уже есть в базе"));
            return "addPriority";
        }
        priorityRepository.save(priority);
        return "redirect:/admin/priorities";
    }

    @GetMapping(path = "/admin/priorities")
    public String getPrioritiesList(ModelMap modelMap) {
        List<Priority> priorities = new ArrayList<>();
        for (Priority priority : priorityRepository.findAll()) {
            priorities.add(priority);
        }
        modelMap.addAttribute("allPrioritiesList", priorities);
        return "priorityList";
    }

    @GetMapping(path = "/admin/priority/{id}")
    public String editPriorityGet(@PathVariable(name = "id") int id,
                                  ModelMap modelMap) {
        Priority priority = priorityRepository.findPriorityById(id);
        modelMap.addAttribute("priority", priority);
        return "editPriority";
    }

    @PostMapping(path = "/admin/priority/{id}")
    public String editPriorityPost(@PathVariable(name = "id") int id,
                                   @Validated
                                   @ModelAttribute(name = "priority")
                                           Priority priority,
                                   BindingResult validationResult) {

        priority.setPriorityType(priority.getPriorityType().trim());
        priority.setDescription(priority.getDescription().trim());
        if (validationResult.hasErrors()) {
            return "editPriority";
        }
        if (priorityRepository.findPriorityByPriorityType(priority.getPriorityType()) != null) {
            validationResult.addError(new FieldError("priority", "priorityType",
                    "Такая запись уже есть в базе"));
            return "editPriority";
        }
        priorityRepository.save(priority);
        return "redirect:/admin/priorities";
    }

    @GetMapping(path = "/admin/priority/delete/{id}")
    public String deletePriority(@PathVariable(name = "id") int id) {
        Priority priority = priorityRepository.findPriorityById(id);
        priorityRepository.delete(priority);
        return "redirect:/admin/priorities";
    }

}

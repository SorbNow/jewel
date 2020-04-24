package org.jewel.web;

import org.jewel.db.PriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PriorityController {
    @Autowired
    private PriorityRepository priorityRepository;

    @GetMapping(value = "/add-priority")
    public String addPriority(){

        return "priority";
    }
}

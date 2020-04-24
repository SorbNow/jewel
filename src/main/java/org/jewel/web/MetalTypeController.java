package org.jewel.web;

import org.jewel.db.MetalTypeRepository;
import org.jewel.model.MetalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import java.util.List;

@Controller
public class MetalTypeController extends HttpServlet {

    @Autowired
    private MetalTypeRepository metalTypeRepository;

    @GetMapping(path = "articles/metalType")
    protected String addMetalTypePage(ModelMap model) {
        List<MetalType> metalTypes = metalTypeRepository.findAllMetalTypes();
        model.addAttribute("metalTypesList", metalTypes);
        return "metalType";
    }

    @PostMapping(path = "/articles/metalType")
    protected String processAddMetalType(@RequestParam("metalNameField") String metalTypeField,
                                         @RequestParam String hallmarkField) {
        MetalType metalType = null;
        int hallmark;
        try {

            hallmark = Integer.parseInt(hallmarkField);
        } catch (NumberFormatException ex) {
            return "redirect:/articles/metalType";
        }
        metalType = metalTypeRepository.findMetalTypeByMetalTypeNameAndHallmark(metalTypeField, hallmark);
        if (metalType == null || !metalTypeField.trim().isEmpty()) {
            metalType = new MetalType();
            metalType.setMetalTypeName(metalTypeField);
            metalType.setHallmark(hallmark);
            metalType = metalTypeRepository.save(metalType);
        }
        return "redirect:/articles/metalType";
    }
}

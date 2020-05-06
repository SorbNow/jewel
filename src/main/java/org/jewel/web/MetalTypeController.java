package org.jewel.web;

import org.jewel.db.MetalTypeRepository;
import org.jewel.model.MetalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import java.util.List;

@Controller
public class MetalTypeController extends HttpServlet {

    @Autowired
    private MetalTypeRepository metalTypeRepository;

    @GetMapping(path = "/article/metalTypes")
    public String MetalTypePage(ModelMap model) {
        List<MetalType> metalTypes = metalTypeRepository.findAllMetalTypes();
        model.addAttribute("allMetalTypesList", metalTypes);
        return "metalTypeList";
    }

    @GetMapping(path = "/article/metalType/add")
    public String addMetalTypeGet(ModelMap modelMap) {
        MetalType metalType = new MetalType();
        modelMap.addAttribute("metalType" , metalType);
        return "addMetalType";
    }

    @PostMapping(path = "/article/metalType/add")
    public String addMetalTypePost(
                                   @Validated
                                   @ModelAttribute("metalType")
                                   MetalType metalType,
                                   BindingResult validationResult) {
        metalType.setMetalTypeName(metalType.getMetalTypeName().trim());
        if (validationResult.hasErrors()) {
            return "addMetalType";
        }
        if (metalTypeRepository.findMetalTypeByMetalTypeNameAndHallmark(metalType.getMetalTypeName(),metalType.getHallmark()) != null) {
            validationResult.addError(new FieldError("metalType", "metalTypeName",
                    "Такая запись уже есть в базе"));
            return "addMetalType";
        }
        metalTypeRepository.save(metalType);
        return "redirect:/article/metalTypes";
    }

    @GetMapping(path = "/article/metalType/{id}")
    public String editMetalTypeGet(@PathVariable(name = "id") int id,
                                ModelMap modelMap) {
        MetalType metalType =metalTypeRepository.findMetalTypeById(id);
        modelMap.addAttribute("metalType", metalType);
        return "editMetalType";
    }

    @PostMapping(path = "/article/metalType/{id}")
    public String editMetalTypePost(@PathVariable(name = "id") int id,
                                    @Validated
                                    @ModelAttribute("metalType")
                                    MetalType metalType,
                                    BindingResult validationResult) {
        metalType.setMetalTypeName(metalType.getMetalTypeName().trim());
        if (validationResult.hasErrors()) {
            return "addMetalType";
        }
        if (metalTypeRepository.findMetalTypeByMetalTypeNameAndHallmark(metalType.getMetalTypeName(),metalType.getHallmark()) != null) {
            validationResult.addError(new FieldError("metalType", "metalTypeName",
                    "Такая запись уже есть в базе"));
            return "addMetalType";
        }
        metalTypeRepository.save(metalType);
        return "redirect:/article/metalTypes";
    }

    @GetMapping(path = "/article/metalType/delete/{id}")
    public String deleteMetalType(@PathVariable(name = "id") int id) {
        MetalType metalType = metalTypeRepository.findMetalTypeById(id);
        metalTypeRepository.delete(metalType);
        return "redirect:/article/metalTypes";
    }
}

package org.jewel.web;

import org.jewel.db.MineralRepository;
import org.jewel.model.Mineral;
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

import java.util.List;

@Controller
public class MineralController {

    @Autowired
    private MineralRepository mineralRepository;

    private Mineral trimMineralParameters(Mineral mineral) {
        mineral.setInsert(mineral.getInsert().trim());
        mineral.setLetterMikhailov(mineral.getLetterMikhailov().trim());
        mineral.setLetterChemyakin(mineral.getLetterChemyakin().trim());
        mineral.setLetterBallet(mineral.getLetterBallet().trim());
        return mineral;
    }

    @GetMapping(path = "/article/minerals")
    public String mineralListPage(ModelMap modelMap) {
        List<Mineral> minerals = mineralRepository.findAllMinerals();
        modelMap.addAttribute("allMineralsList", minerals);
        return "mineralList";
    }

    @GetMapping(path = "/article/mineral/add")
    public String addMineralGet(ModelMap modelMap) {
        Mineral mineral = new Mineral();
        modelMap.addAttribute("mineral", mineral);
        return "addMineral";
    }

    @PostMapping(path = "/article/mineral/add")
    public String addMineralPost(@Validated
                                 @ModelAttribute("mineral")
                                         Mineral mineral,
                                 BindingResult validationResult) {
        mineral = trimMineralParameters(mineral);
        if (validationResult.hasErrors()) {
            return "addMineral";
        }
        if (mineralRepository.findMineralByInsert(mineral.getInsert()) != null) {
            validationResult.addError(new FieldError("mineral", "insert",
                    "Такая запись уже есть в базе"));
            return "addMineral";
        }
        mineralRepository.save(mineral);
        return "redirect:/article/minerals";
    }

    @GetMapping(path = "/article/mineral/delete/{id}")
    public String deleteMineral(@PathVariable(name = "id") int id) {
        Mineral mineral = mineralRepository.findMineralById(id);
        mineralRepository.delete(mineral);
        return "redirect:/article/minerals";
    }

    @GetMapping(path = "/article/mineral/{id}")
    public String editMineralGet(@PathVariable(name = "id") int id,
                                 ModelMap modelMap) {
        Mineral mineral = mineralRepository.findMineralById(id);
        modelMap.addAttribute("mineral", mineral);
        return "editMineral";
    }

    @PostMapping(path = "/article/mineral/{id}")
    public String editMineralPost(@ModelAttribute(name = "id") int id,
                                  @Validated
                                  @ModelAttribute("mineral")
                                          Mineral mineral,
                                  BindingResult validationResult) {
        mineral = trimMineralParameters(mineral);
        if (validationResult.hasErrors()) {
            return "editMineral";
        }
        if (mineralRepository.findMineralByInsert(mineral.getInsert()) != null &&
                !mineral.getInsert().equals(mineralRepository.findMineralById(id).getInsert())) {
            validationResult.addError(new FieldError("mineral", "insert",
                    "Такая запись уже есть в базе"));
            return "editMineral";
        }

        mineralRepository.save(mineral);
        return "redirect:/article/minerals";
    }
}

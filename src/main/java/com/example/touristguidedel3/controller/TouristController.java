package com.example.touristguidedel3.controller;

import com.example.touristguidedel3.exception.DuplicateAttractionException;
import com.example.touristguidedel3.exception.InvalidAttractionException;
import com.example.touristguidedel3.model.TouristAttraction;
import com.example.touristguidedel3.service.TouristService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("attractions")
public class TouristController {
    private final TouristService service;

    public TouristController(TouristService touristService) {
        this.service = touristService;
    }

    @GetMapping
    public String getAttractions(Model model) {
        List<TouristAttraction> attractions = service.getAttractions();
        model.addAttribute("attractions", attractions);
        return "showattractions";
    }

    @GetMapping("/{id}")
    public String findAttractionById(@PathVariable int id, Model model) {
        TouristAttraction attraction = service.findAttractionById(id);
        model.addAttribute("attraction", attraction);
        return "attraction";
    }

    @GetMapping("/{id}/tags")
    public String findTags(@PathVariable int id, Model model) {
        TouristAttraction attraction = service.findAttractionById(id);
        model.addAttribute("attraction", attraction);
        return "showtags";
    }

    @GetMapping("/add")
    public String addAttraction(Model model) {
        TouristAttraction attraction = new TouristAttraction();
        model.addAttribute("attraction", attraction);
        model.addAttribute("cities", service.getCities());
        model.addAttribute("tags", service.getTags());
        return "addnewattraction";
    }

    @PostMapping("/save")
    public String saveAttraction(@ModelAttribute TouristAttraction attraction, Model model) {
        try {
            service.saveAttraction(attraction);
            return "redirect:/attractions";
        } catch (InvalidAttractionException | DuplicateAttractionException exception) {
            model.addAttribute("cities", service.getCities());
            model.addAttribute("tags", service.getTags());
            model.addAttribute("attraction", attraction);
            model.addAttribute("errorMessage", exception.getMessage());


            return "addnewattraction";
        }
    }

    @GetMapping("/{id}/edit")
    public String editAttraction(@PathVariable int id, Model model) {
        model.addAttribute("attraction", service.findAttractionById(id));
        model.addAttribute("cities", service.getCities());
        model.addAttribute("tags", service.getTags());
        return "edit";
    }

    @PostMapping("/update")
    public String updateAttraction(@ModelAttribute TouristAttraction attraction, Model model) {
        try {
            service.updateAttraction(attraction);
            return "redirect:/attractions";
        } catch (InvalidAttractionException | DuplicateAttractionException exception) {
            model.addAttribute("cities", service.getCities());
            model.addAttribute("tags", service.getTags());
            model.addAttribute("attraction", attraction);
            model.addAttribute("errorMessage", exception.getMessage());

            return "edit";
        }
    }
    @PostMapping("/delete/{id}")
    public String deleteAttraction(@PathVariable int id) {
        service.deleteAttraction(id);

        return "redirect:/attractions";
    }
}


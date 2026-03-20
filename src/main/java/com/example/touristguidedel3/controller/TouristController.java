package com.example.touristguidedel3.controller;

import com.example.touristguidedel3.model.TouristAttraction;
import com.example.touristguidedel3.service.TouristService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
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

    @GetMapping("/{name}")
    public String findAttractionByName(@PathVariable String name, Model model) {
        TouristAttraction attraction = service.findAttractionByName(name);
        model.addAttribute("attraction", attraction);
        return "attraction";
    }

    @GetMapping("/{name}/tags")
    public String findTags(@PathVariable String name, Model model) {
        TouristAttraction attraction = service.findAttractionByName(name);
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
//
//    @PostMapping("/save")
//    public String saveAttraction(@ModelAttribute TouristAttraction attraction) {
//        service.saveAttraction(attraction);
//        return "redirect:/attractions";
//    }
//
    @GetMapping("/{name}/edit")
    public String editAttraction(@PathVariable String name, Model model) {
        model.addAttribute("attraction", service.findAttractionByName(name));
        model.addAttribute("cities", service.getCities());
        model.addAttribute("tags", service.getTags());
        return "edit";
    }
//
//    @PostMapping("/update")
//    public String updateAttraction(@ModelAttribute TouristAttraction attraction) {
//        service.updateAttraction(attraction);
//        return "redirect:/attractions";
//    }
//
    @PostMapping("/delete/{name}")
    public String deleteAttraction(@PathVariable String name) {
        service.deleteAttraction(name);

        return "redirect:/attractions";
    }
}


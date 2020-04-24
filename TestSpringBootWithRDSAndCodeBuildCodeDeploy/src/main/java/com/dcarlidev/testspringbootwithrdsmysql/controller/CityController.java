/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dcarlidev.testspringbootwithrdsmysql.controller;

import com.dcarlidev.testspringbootwithrdsmysql.model.City;
import com.dcarlidev.testspringbootwithrdsmysql.services.CityService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author carlos
 */
@Controller
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping(path = "/showCities")
    public String getAllCities(Model model) {
        System.out.println("Starting to get all data...");
        List<City> cities = cityService.getAllCities();
        cities.stream().forEach(c -> System.out.println("City: " + c.getName()));
        model.addAttribute("cities", cities);
        return "showCities";
    }

}

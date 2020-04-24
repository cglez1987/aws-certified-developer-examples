/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dcarlidev.testspringbootwithrdsmysql.services;

import com.dcarlidev.testspringbootwithrdsmysql.model.City;
import com.dcarlidev.testspringbootwithrdsmysql.repositories.CityRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author carlos
 */
@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<City> getAllCities() {
        return (List<City>) cityRepository.findAll();
    }

    public City getCityById(Long id) {
        City city = null;
        city = cityRepository.findById(id).get();
        return city;
    }

}

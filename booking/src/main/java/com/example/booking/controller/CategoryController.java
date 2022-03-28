package com.example.booking.controller;

import com.example.booking.model.Category;
import com.example.booking.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")

public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;
    @GetMapping
    public List<Category> getAll (){
        return categoryRepository.findAll();
    }
}

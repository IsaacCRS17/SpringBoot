package com.admin.firstproject.controller;

import com.admin.firstproject.type.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class categoryRESTController {

    public static List<Category> categories;

    public categoryRESTController() {
        categories=new ArrayList<>();
        //Obj 1
        Category category1  = new Category();
        category1.setId("CAT01");
        category1.setName("ZAPATILLAS");
        categories.add(category1);
        //Obj 2
        Category category2  = new Category();
        category2.setId("CAT02");
        category2.setName("GORRAS");
        categories.add(category2);
    }

    @GetMapping
    public List<Category> list(){
        return categories;
    }
}

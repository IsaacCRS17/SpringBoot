package com.admin.firstproject.controller;

import com.admin.firstproject.Entity.CategoryEntity;
import com.admin.firstproject.service.CategoryService;
import com.admin.firstproject.type.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryRESTController {

    @Autowired
    private CategoryService categoryService;
    public CategoryRESTController() {
    }

    @GetMapping
    public List<CategoryDTO> list(@RequestParam(defaultValue = "") String filter){
        return this.categoryService.getList(filter);
    }

    @PostMapping
    public void add(@RequestBody CategoryDTO categoryDTO){
        this.categoryService.add(categoryDTO);
    }
    @PutMapping
    public void update(@RequestBody CategoryDTO categoryDTO){
        this.categoryService.update(categoryDTO);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        this.categoryService.delete(id);
    }
}

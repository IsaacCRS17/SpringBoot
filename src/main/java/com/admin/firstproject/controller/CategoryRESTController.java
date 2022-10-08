package com.admin.firstproject.controller;

import com.admin.firstproject.service.CategoryService;
import com.admin.firstproject.type.ApiResponse;
import com.admin.firstproject.type.CategoryDTO;
import com.admin.firstproject.type.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryRESTController {

    @Autowired
    private CategoryService categoryService;
    public CategoryRESTController() {
    }
    @GetMapping
    public Pagination<CategoryDTO> list(
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return this.categoryService.getList(filter, page, size);
    }
    @PostMapping
    public ApiResponse<CategoryDTO> add(@RequestBody CategoryDTO categoryDTO){
        return this.categoryService.add(categoryDTO);
    }
    @PutMapping
    public ApiResponse<CategoryDTO> update(@RequestBody CategoryDTO categoryDTO){
        return this.categoryService.update(categoryDTO);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        this.categoryService.delete(id);
    }

}

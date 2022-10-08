package com.admin.firstproject.controller;

import com.admin.firstproject.service.GradoService;
import com.admin.firstproject.type.ApiResponse;
import com.admin.firstproject.type.GradoDTO;
import com.admin.firstproject.type.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/grado")
public class GradoRESTController {

    @Autowired
    private GradoService gradoService;

    public GradoRESTController(){
    }

    @GetMapping
    public Pagination<GradoDTO> list(
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return this.gradoService.getList(filter, page, size);
    }

    @PostMapping
    public ApiResponse<GradoDTO> add(@RequestBody GradoDTO gradoDTO){
        return this.gradoService.add(gradoDTO);
    }

    @PutMapping
    public ApiResponse<GradoDTO> update(@RequestBody GradoDTO gradoDTO){
        return this.gradoService.update(gradoDTO);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        this.gradoService.delete(id);
    }

}
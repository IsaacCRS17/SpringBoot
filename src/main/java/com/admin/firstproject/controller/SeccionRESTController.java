package com.admin.firstproject.controller;

import com.admin.firstproject.service.SeccionService;
import com.admin.firstproject.type.ApiResponse;
import com.admin.firstproject.type.Pagination;
import com.admin.firstproject.type.SeccionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/seccion")
public class SeccionRESTController {

    @Autowired
    private SeccionService seccionService;

    public SeccionRESTController(){
    }

    @GetMapping
    public Pagination<SeccionDTO> list(
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return this.seccionService.getList(filter, page, size);
    }

    @PostMapping
    public ApiResponse<SeccionDTO> add(@RequestBody SeccionDTO seccionDTO){
        return this.seccionService.add(seccionDTO);
    }

    @PutMapping
    public ApiResponse<SeccionDTO> update(@RequestBody SeccionDTO seccionDTO){
        return this.seccionService.update(seccionDTO);
    }

    @DeleteMapping("{id}")
    public ApiResponse<SeccionDTO> delete(@PathVariable String id){
        return this.seccionService.delete(id);
    }

}

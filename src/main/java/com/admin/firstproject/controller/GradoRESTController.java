package com.admin.firstproject.controller;

import com.admin.firstproject.service.GradoService;
import com.admin.firstproject.service.SeccionService;
import com.admin.firstproject.type.ApiResponse;
import com.admin.firstproject.type.GradoDTO;
import com.admin.firstproject.type.Pagination;
import com.admin.firstproject.type.SeccionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/grado")
public class GradoRESTController {

    @Autowired
    private GradoService gradoService;
    @Autowired
    private SeccionService seccionService;

    public GradoRESTController(){
    }

    @GetMapping
    public ApiResponse<Pagination<GradoDTO>> list(
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return this.gradoService.getList(filter, page, size);
    }

    @GetMapping("/{id}/secciones")
    public Pagination<SeccionDTO> listSectxGrad(
            @PathVariable String id,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return this.seccionService.getListSxG(id, filter, page, size);
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
    public ApiResponse<GradoDTO> delete(@PathVariable String id){
        return this.gradoService.delete(id);
    }

}

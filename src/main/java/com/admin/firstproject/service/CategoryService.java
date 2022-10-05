package com.admin.firstproject.service;

import com.admin.firstproject.Entity.CategoryEntity;
import com.admin.firstproject.repository.CategoryRepository;
import com.admin.firstproject.type.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> getList(){
        List<CategoryEntity> categoryEntities= this.categoryRepository.findAll();
        List<CategoryDTO> categoryDTOS=new ArrayList<>();
        for (CategoryEntity categoryEntity: categoryEntities){
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(categoryEntity.getUniqueIdentifier());
            categoryDTO.setCode(categoryEntity.getCode());
            categoryDTO.setName(categoryEntity.getName());
            categoryDTOS.add(categoryDTO);
        }
        return categoryDTOS;
    }

    public void add(CategoryDTO categoryDTO){
        categoryDTO.setId(UUID.randomUUID().toString());

        //change DTO to entity
        CategoryEntity categoryEntity=new CategoryEntity();
        categoryEntity.setUniqueIdentifier(categoryDTO.getId());
        categoryEntity.setCode(categoryDTO.getCode());
        categoryEntity.setName(categoryDTO.getName());
        this.categoryRepository.save(categoryEntity);
    }

    public void update(CategoryDTO categoryDTO){
        Optional<CategoryEntity> optionalCategoryEntity=this.categoryRepository.findByUniqueIdentifier(categoryDTO.getId());
        if(optionalCategoryEntity.isPresent()){
            CategoryEntity categoryEntity=optionalCategoryEntity.get();
            //Set update data
            categoryEntity.setCode(categoryDTO.getCode());
            categoryEntity.setName(categoryDTO.getName());
            //Update in database
            this.categoryRepository.save(categoryEntity);
        }else{
            System.out.println("No existe la categoría para poder actualizar");
        }
    }
    //id dto=uniqueIdentifier Entity
    public void delete(String id){
        Optional<CategoryEntity> optionalCategoryEntity=this.categoryRepository.findByUniqueIdentifier(id);
        if(optionalCategoryEntity.isPresent()){
            this.categoryRepository.delete(optionalCategoryEntity.get());
        } else{
            System.out.println("No existe la categoría para poder eliminar");
        }
    }
}



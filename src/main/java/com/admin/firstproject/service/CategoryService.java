package com.admin.firstproject.service;

import com.admin.firstproject.Entity.CategoryEntity;
import com.admin.firstproject.repository.CategoryRepository;
import com.admin.firstproject.type.CategoryDTO;
import com.admin.firstproject.util.Code;
import com.admin.firstproject.util.ConstantsGeneric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> getList(String filter){
        return this.categoryRepository.findCategories(ConstantsGeneric.CREATED_STATUS, filter).orElse(new ArrayList<>()).stream().map(CategoryEntity::getDTO).collect(Collectors.toList());
    }

    public void add(CategoryDTO categoryDTO){
        System.out.println(categoryDTO.toString());
        categoryDTO.setId(UUID.randomUUID().toString());
        categoryDTO.setCode(Code.generateCode(Code.CATEGORY_CODE, this.categoryRepository.count() + 1, Code.CATEGORY_LENGTH));
        categoryDTO.setStatus(ConstantsGeneric.CREATED_STATUS);
        categoryDTO.setCreateAt(LocalDateTime.now());

        Optional<CategoryEntity> optionalCategoryEntity = this.categoryRepository.findByName(categoryDTO.getName());
        if (optionalCategoryEntity.isPresent()) {
            System.out.println("No se resgistró, la categoría existe");
            return;
        }


        //change DTO to entity
        CategoryEntity categoryEntity=new CategoryEntity();
        categoryEntity.setData(categoryDTO);
        this.categoryRepository.save(categoryEntity);
    }

    public void update(CategoryDTO categoryDTO){

        Optional<CategoryEntity> optionalCategoryEntity=this.categoryRepository.findByUniqueIdentifier(categoryDTO.getId());
        if(optionalCategoryEntity.isPresent()){
            categoryDTO.setUpdateAt(LocalDateTime.now());
            CategoryEntity categoryEntity=optionalCategoryEntity.get();
            //Set update data
            if(categoryDTO.getCode()!=null) {
                categoryEntity.setCode(categoryDTO.getCode());
            }
            if(categoryDTO.getName()!=null) {
                categoryEntity.setName(categoryDTO.getName());
            }
            categoryEntity.setUpdateAt(categoryDTO.getUpdateAt());
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
            CategoryEntity categoryEntity=optionalCategoryEntity.get();
            categoryEntity.setStatus(ConstantsGeneric.DELETED_STATUS);
            categoryEntity.setDeleteAt(LocalDateTime.now());
            this.categoryRepository.save(categoryEntity);
        } else{
            System.out.println("No existe la categoría para poder eliminar");
        }
    }
}



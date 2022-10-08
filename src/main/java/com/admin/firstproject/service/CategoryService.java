package com.admin.firstproject.service;

import com.admin.firstproject.Entity.CategoryEntity;
import com.admin.firstproject.repository.CategoryRepository;
import com.admin.firstproject.type.ApiResponse;
import com.admin.firstproject.type.CategoryDTO;
import com.admin.firstproject.type.Pagination;
import com.admin.firstproject.util.Code;
import com.admin.firstproject.util.ConstantsGeneric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Pagination<CategoryDTO> getList(String filter, int page, int size){
        Pagination<CategoryDTO> pagination= new Pagination();
        pagination.setCountFilter(this.categoryRepository.findCountCategories(ConstantsGeneric.CREATED_STATUS, filter));
        if (pagination.getCountFilter() > 0) {
            Pageable pageable= PageRequest.of(page, size);
            List<CategoryEntity> categoryEntities=this.categoryRepository.findCategories(ConstantsGeneric.CREATED_STATUS, filter, pageable).orElse(new ArrayList<>());
            pagination.setList(categoryEntities.stream().map(CategoryEntity::getDTO).collect(Collectors.toList()));
        }
        pagination.setTotalPages(pagination.processAndGetTotalPages(size));
        return pagination;
    }

    public ApiResponse<CategoryDTO> add(CategoryDTO categoryDTO){
        ApiResponse<CategoryDTO> apiResponse = new ApiResponse<>();
        categoryDTO.setId(UUID.randomUUID().toString());
        categoryDTO.setCode(Code.generateCode(Code.CATEGORY_CODE, this.categoryRepository.count() + 1, Code.CATEGORY_LENGTH));
        categoryDTO.setStatus(ConstantsGeneric.CREATED_STATUS);
        categoryDTO.setCreateAt(LocalDateTime.now());

        Optional<CategoryEntity> optionalCategoryEntity = this.categoryRepository.findByName(categoryDTO.getName());
        if (optionalCategoryEntity.isPresent()) {
            apiResponse.setSuccessful(false);
            apiResponse.setCode("CATEGORY_EXISTS");
            apiResponse.setMessage("No se resgistró, la categoría existe");
            return apiResponse;
        }


        //change DTO to entity
        CategoryEntity categoryEntity=new CategoryEntity();
        categoryEntity.setData(categoryDTO);
        apiResponse.setData(this.categoryRepository.save(categoryEntity).getDTO());
        apiResponse.setSuccessful(true);
        apiResponse.setMessage("ok");
        return apiResponse;
    }

    public ApiResponse<CategoryDTO> update(CategoryDTO categoryDTO){
        ApiResponse<CategoryDTO> apiResponse = new ApiResponse<>();

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
            apiResponse.setSuccessful(true);
            apiResponse.setMessage("ok");
            apiResponse.setData(this.categoryRepository.save(categoryEntity).getDTO());
            return apiResponse;
        }else{
            apiResponse.setSuccessful(false);
            apiResponse.setCode("CATEGORY_DOES_NOT_EXISTS");
            apiResponse.setMessage("No existe la categoría para poder actualizar");
        }
        return apiResponse;
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



package com.admin.firstproject.service;

import com.admin.firstproject.Entity.GradoEntity;
import com.admin.firstproject.Entity.SeccionEntity;
import com.admin.firstproject.repository.GradoRepository;
import com.admin.firstproject.type.ApiResponse;
import com.admin.firstproject.type.GradoDTO;
import com.admin.firstproject.type.Pagination;
import com.admin.firstproject.type.SeccionDTO;
import com.admin.firstproject.util.Code;
import com.admin.firstproject.util.ConstantsGeneric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GradoService {

    @Autowired
    private GradoRepository gradoRepository;

    public Pagination<GradoDTO> getList(String filter, int page, int size){
        Pagination<GradoDTO> pagination = new Pagination<>();
        pagination.setCountFilter(this.gradoRepository.findCountGrados(ConstantsGeneric.CREATED_STATUS, filter));
        if(pagination.getCountFilter()>0){
            Pageable pageable= PageRequest.of(page, size);
            List<GradoEntity> gradoEntities=this.gradoRepository.findGrados(ConstantsGeneric.CREATED_STATUS, filter, pageable).orElse(new ArrayList<>());
            pagination.setList(gradoEntities.stream().map(GradoEntity::getGradoDTO).collect(Collectors.toList()));
        }
        pagination.setTotalPages(pagination.processAndGetTotalPages(size));
        return pagination;
    }
    public Pagination<SeccionDTO> getListSections(String id, String filter, int page, int size){
        Pagination<SeccionDTO> pagination = new Pagination<>();
        pagination.setCountFilter(this.gradoRepository.findCountSectionsInGrade(id, filter));
        if(pagination.getCountFilter()>0) {
            Pageable pageable= PageRequest.of(page, size);
                List<GradoEntity> gradoEntities=this.gradoRepository.findSectiosInGrade(ConstantsGeneric.CREATED_STATUS, filter, pageable).orElse(new ArrayList<>());
//            Optional<GradoEntity> optionalGradoEntity = this.gradoRepository.findByUniqueIdentifier(id);
//            GradoEntity gradoEntity = optionalGradoEntity.get();
//            Set<SeccionEntity> seccionEntities = gradoEntity.getSecciones();
//            pagination.setList(seccionEntities.stream().map(SeccionEntity::getSeccionDTO).collect(Collectors.toList()));
        }
        pagination.setTotalPages(pagination.processAndGetTotalPages(size));
        return pagination;
    }
    public ApiResponse<GradoDTO> add(GradoDTO gradoDTO){
        ApiResponse<GradoDTO> apiResponse = new ApiResponse<>();
        gradoDTO.setId(UUID.randomUUID().toString());
        gradoDTO.setCode(Code.generateCode(Code.GRADE_CODE, this.gradoRepository.count() + 1, Code.GRADE_LENGTH));
        gradoDTO.setStatus(ConstantsGeneric.CREATED_STATUS);
        gradoDTO.setCreateAt(LocalDateTime.now());

        Optional<GradoEntity> optionalGradoEntity = this.gradoRepository.findByName(gradoDTO.getName());
        if (optionalGradoEntity.isPresent()) {
            apiResponse.setSuccessful(false);
            apiResponse.setCode("GRADE_EXISTS");
            apiResponse.setMessage("No se resgistró, el grado existe");
            return apiResponse;
        }


        //change DTO to entity
        GradoEntity gradoEntity =new GradoEntity();
        gradoEntity.setGradoDTO(gradoDTO);
        apiResponse.setData(this.gradoRepository.save(gradoEntity).getGradoDTO());
        apiResponse.setSuccessful(true);
        apiResponse.setMessage("ok");
        return apiResponse;
    }

    public ApiResponse<GradoDTO> update(GradoDTO gradoDTO){
        ApiResponse<GradoDTO> apiResponse = new ApiResponse<>();

        Optional<GradoEntity> optionalGradoEntity=this.gradoRepository.findByUniqueIdentifier(gradoDTO.getId());
        if(optionalGradoEntity.isPresent()){
            gradoDTO.setUpdateAt(LocalDateTime.now());
            GradoEntity gradoEntity =optionalGradoEntity.get();
            //Set update data
            if(gradoDTO.getCode()!=null) {
                gradoEntity.setCode(gradoDTO.getCode());
            }
            if(gradoDTO.getName()!=null) {
                gradoEntity.setName(gradoDTO.getName());
            }
            gradoEntity.setUpdateAt(gradoDTO.getUpdateAt());
            //Update in database
            apiResponse.setSuccessful(true);
            apiResponse.setMessage("ok");
            apiResponse.setData(this.gradoRepository.save(gradoEntity).getGradoDTO());
            return apiResponse;
        }else{
            apiResponse.setSuccessful(false);
            apiResponse.setCode("GRADE_DOES_NOT_EXISTS");
            apiResponse.setMessage("No existe el grado para poder actualizar");
        }
        return apiResponse;
    }
    //id dto=uniqueIdentifier Entity
    public ApiResponse<GradoDTO> delete(String id){
        ApiResponse<GradoDTO> apiResponse = new ApiResponse<>();
        Optional<GradoEntity> optionalGradoEntity=this.gradoRepository.findByUniqueIdentifier(id);
        if(optionalGradoEntity.isPresent()){
            GradoEntity gradoEntity =optionalGradoEntity.get();
            gradoEntity.setStatus(ConstantsGeneric.DELETED_STATUS);
            gradoEntity.setDeleteAt(LocalDateTime.now());

            apiResponse.setSuccessful(true);
            apiResponse.setMessage("ok");
            apiResponse.setData(this.gradoRepository.save(gradoEntity).getGradoDTO());
        } else{
            apiResponse.setSuccessful(false);
            apiResponse.setCode("GRADE_DOES_NOT_EXISTS");
            apiResponse.setMessage("No existe el grado para poder eliminar");
        }
        return apiResponse;
    }
}

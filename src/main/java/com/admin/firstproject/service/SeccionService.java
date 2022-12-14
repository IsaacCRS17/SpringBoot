package com.admin.firstproject.service;

import com.admin.firstproject.Entity.SeccionEntity;
import com.admin.firstproject.repository.SeccionRepository;
import com.admin.firstproject.type.ApiResponse;
import com.admin.firstproject.type.GradoDTO;
import com.admin.firstproject.type.Pagination;
import com.admin.firstproject.type.SeccionDTO;
import com.admin.firstproject.util.Code;
import com.admin.firstproject.util.ConstantsGeneric;
import lombok.extern.log4j.Log4j2;
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

@Log4j2
@Service
public class SeccionService {

    @Autowired
    private SeccionRepository seccionRepository;

    public ApiResponse<Pagination<SeccionDTO>> getList(String filter, int page, int size){
        log.info("filter page size {} {} {}", filter, page, size);
        ApiResponse<Pagination<SeccionDTO>> apiResponse = new ApiResponse<>();
        Pagination<SeccionDTO> pagination = new Pagination<>();
        pagination.setCountFilter(this.seccionRepository.findCountSeccion(ConstantsGeneric.CREATED_STATUS, filter));
        if(pagination.getCountFilter()>0){
            Pageable pageable= PageRequest.of(page, size);
            List<SeccionEntity> seccionEntities=this.seccionRepository.findSeccion(ConstantsGeneric.CREATED_STATUS, filter, pageable).orElse(new ArrayList<>());
            pagination.setList(seccionEntities.stream().map(SeccionEntity::getSeccionDTO).collect(Collectors.toList()));
        }
        pagination.setTotalPages(pagination.processAndGetTotalPages(size));
        apiResponse.setData(pagination);
        apiResponse.setSuccessful(true);
        apiResponse.setMessage("ok");
        return apiResponse;
    }

    public ApiResponse<Pagination<SeccionDTO>> getListSxG(String id, String filter, int page, int size){
        log.info("id filter page size {} {} {} {}", id, filter, page, size);
        ApiResponse<Pagination<SeccionDTO>> apiResponse = new ApiResponse<>();
        Pagination<SeccionDTO> pagination = new Pagination<>();
        pagination.setCountFilter(this.seccionRepository.findCountSeccionxGrado(id,ConstantsGeneric.CREATED_STATUS, filter));
        System.out.println(pagination.getCountFilter());
        if(pagination.getCountFilter()>0){
            Pageable pageable= PageRequest.of(page, size);
            List<SeccionEntity> seccionEntities=this.seccionRepository.findSeccionxGrado(id, ConstantsGeneric.CREATED_STATUS, filter, pageable).orElse(new ArrayList<>());
            pagination.setList(seccionEntities.stream().map(SeccionEntity::getSeccionDTO).collect(Collectors.toList()));
        }
        pagination.setTotalPages(pagination.processAndGetTotalPages(size));
        apiResponse.setData(pagination);
        apiResponse.setSuccessful(true);
        apiResponse.setMessage("ok");
        return apiResponse;
    }

    public ApiResponse<SeccionDTO> add(SeccionDTO seccionDTO){
        ApiResponse<SeccionDTO> apiResponse = new ApiResponse<>();
        seccionDTO.setId(UUID.randomUUID().toString());
        seccionDTO.setCode(Code.generateCode(Code.SECTION_CODE, this.seccionRepository.count() + 1, Code.SECTION_LENGTH));
        seccionDTO.setStatus(ConstantsGeneric.CREATED_STATUS);
        seccionDTO.setCreateAt(LocalDateTime.now());

        Optional<SeccionEntity> optionalSeccionEntity = this.seccionRepository.findByName(seccionDTO.getName());
        if (optionalSeccionEntity.isPresent()) {
            log.warn("No se completo el registro");
            apiResponse.setSuccessful(false);
            apiResponse.setCode("SECTION_EXISTS");
            apiResponse.setMessage("No se resgistr??, la secci??n existe");
            return apiResponse;
        }
        //change DTO to entity
        SeccionEntity seccionEntity =new SeccionEntity();
        seccionEntity.setSeccionDTO(seccionDTO);
        apiResponse.setData(this.seccionRepository.save(seccionEntity).getSeccionDTO());
        apiResponse.setSuccessful(true);
        apiResponse.setMessage("ok");
        return apiResponse;
    }

    public ApiResponse<SeccionDTO> update(SeccionDTO seccionDTO){
        ApiResponse<SeccionDTO> apiResponse = new ApiResponse<>();

        Optional<SeccionEntity> optionalSeccionEntity=this.seccionRepository.findByUniqueIdentifier(seccionDTO.getId());
        if(optionalSeccionEntity.isPresent()){
            seccionDTO.setUpdateAt(LocalDateTime.now());
            SeccionEntity seccionEntity =optionalSeccionEntity.get();
            //Set update data
            if(seccionDTO.getCode()!=null) {
                seccionEntity.setCode(seccionDTO.getCode());
            }
            if(seccionDTO.getName()!=null) {
                seccionEntity.setName(seccionDTO.getName());
            }
            seccionEntity.setUpdateAt(seccionDTO.getUpdateAt());
            //Update in database
            apiResponse.setSuccessful(true);
            apiResponse.setMessage("ok");
            apiResponse.setData(this.seccionRepository.save(seccionEntity).getSeccionDTO());
            return apiResponse;
        }else{
            log.warn("No se complet?? la actualizaci??n");
            apiResponse.setSuccessful(false);
            apiResponse.setCode("SECTION_DOES_NOT_EXISTS");
            apiResponse.setMessage("No existe la secci??n para poder actualizar");
        }
        return apiResponse;
    }
    //id dto=uniqueIdentifier Entity
    public ApiResponse<SeccionDTO> delete(String id){
        ApiResponse<SeccionDTO> apiResponse = new ApiResponse<>();

        Optional<SeccionEntity> optionalSeccionEntity=this.seccionRepository.findByUniqueIdentifier(id);
        if(optionalSeccionEntity.isPresent()){
            SeccionEntity seccionEntity =optionalSeccionEntity.get();
            seccionEntity.setStatus(ConstantsGeneric.DELETED_STATUS);
            seccionEntity.setDeleteAt(LocalDateTime.now());

            apiResponse.setSuccessful(true);
            apiResponse.setMessage("ok");
            apiResponse.setData(this.seccionRepository.save(seccionEntity).getSeccionDTO());
        } else{
            log.warn("No se elimin?? el registro");
            apiResponse.setSuccessful(false);
            apiResponse.setCode("SECTION_DOES_NOT_EXISTS");
            apiResponse.setMessage("No existe la secci??n para poder eliminar");
        }
        return apiResponse;
    }
}

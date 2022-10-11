package com.admin.firstproject.type;

import com.admin.firstproject.Entity.SeccionEntity;
import lombok.Data;

import java.util.Set;

@Data
public class GradoDTO extends AuditoryDTO{
    private String code;
    private Character name;
    private Set<SeccionEntity> secciones;
}

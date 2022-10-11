package com.admin.firstproject.type;

import com.admin.firstproject.Entity.GradoEntity;
import lombok.Data;

import java.util.Set;

@Data
public class SeccionDTO extends AuditoryDTO{
    private String code;
    private Character name;
    private Set<GradoEntity> grados;
}

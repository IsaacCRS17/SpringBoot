package com.admin.firstproject.type;

import com.admin.firstproject.Entity.SeccionEntity;
import lombok.Data;

import java.util.List;

@Data
public class GradoDTO extends AuditoryDTO{
    private String code;
    private Character name;
}

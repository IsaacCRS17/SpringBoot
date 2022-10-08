package com.admin.firstproject.type;

import lombok.Data;

@Data
public class CategoryDTO extends AuditoryDTO{
    private String code;
    private String name;

}

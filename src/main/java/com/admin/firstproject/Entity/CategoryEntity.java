package com.admin.firstproject.Entity;

import com.admin.firstproject.type.CategoryDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "category")
public class CategoryEntity extends AuditoryEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcategory")
    private Integer id;

    @Column(name = "tx_code", length = 10)
    private String code;

    @Column(name = "tx_name", length = 100)
    private String name;


    public CategoryDTO getDTO(){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(this.getUniqueIdentifier());
        categoryDTO.setCode(this.code);
        categoryDTO.setName(this.name);
        categoryDTO.setStatus(this.getStatus());
        categoryDTO.setCreateAt(this.getCreateAt());
        categoryDTO.setUpdateAt(this.getUpdateAt());
        categoryDTO.setDeleteAt(this.getDeleteAt());
        return categoryDTO;
    }
    public void setData(CategoryDTO categoryDTO){
        this.setUniqueIdentifier(categoryDTO.getId());
        this.code=categoryDTO.getCode();
        this.name=categoryDTO.getName();
        this.setStatus(categoryDTO.getStatus());
        this.setCreateAt(categoryDTO.getCreateAt());
        this.setUpdateAt(categoryDTO.getUpdateAt());
        this.setDeleteAt(categoryDTO.getDeleteAt());
    }

}
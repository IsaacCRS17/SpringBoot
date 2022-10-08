package com.admin.firstproject.Entity;

import com.admin.firstproject.type.GradoDTO;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Grado")
public class GradoEntity extends AuditoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tb_grado_id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "tb_grado_cod", length = 40)
    private String code;
    @Column(name = "tb_grado_nom")
    private Character name;

    public GradoDTO getGradoDTO(){
        GradoDTO gradoDTO = new GradoDTO();
        gradoDTO.setId(this.getUniqueIdentifier());
        gradoDTO.setCode(this.code);
        gradoDTO.setName(this.name);
        gradoDTO.setStatus(this.getStatus());
        gradoDTO.setCreateAt(this.getCreateAt());
        gradoDTO.setUpdateAt(this.getUpdateAt());
        gradoDTO.setDeleteAt(this.getDeleteAt());
        return gradoDTO;
    }

    public void setGradoDTO(GradoDTO gradoDTO){
        this.setUniqueIdentifier(gradoDTO.getId());
        this.code= gradoDTO.getCode();
        this.name= gradoDTO.getName();
        this.setStatus(gradoDTO.getStatus());
        this.setCreateAt(gradoDTO.getCreateAt());
        this.setUpdateAt(gradoDTO.getUpdateAt());
        this.setDeleteAt(gradoDTO.getDeleteAt());
    }
}

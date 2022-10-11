package com.admin.firstproject.Entity;

import com.admin.firstproject.type.SeccionDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "Seccion")
public class SeccionEntity extends AuditoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tb_seccion_id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "tb_seccion_cod", length = 40)
    private String code;
    @Column(name = "tb_seccion_nom")
    private Character name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "seccionxgrado",
            joinColumns = {@JoinColumn(name = "tb_seccion_id", referencedColumnName = "tb_seccion_id")},
            inverseJoinColumns = {@JoinColumn(name = "tb_grado_id", referencedColumnName = "tb_grado_id")}
    )
    private Set<GradoEntity> grados= new HashSet<>();

    public SeccionDTO getSeccionDTO(){
        SeccionDTO seccionDTO = new SeccionDTO();
        seccionDTO.setId(this.getUniqueIdentifier());
        seccionDTO.setCode(this.code);
        seccionDTO.setName(this.name);
        seccionDTO.setGrados(this.grados);
        seccionDTO.setStatus(this.getStatus());
        seccionDTO.setCreateAt(this.getCreateAt());
        seccionDTO.setUpdateAt(this.getUpdateAt());
        seccionDTO.setDeleteAt(this.getDeleteAt());
        return seccionDTO;
    }

    public void setSeccionDTO(SeccionDTO seccionDTO){
        this.setUniqueIdentifier(seccionDTO.getId());
        this.code= seccionDTO.getCode();
        this.name= seccionDTO.getName();
        this.grados= seccionDTO.getGrados();
        this.setStatus(seccionDTO.getStatus());
        this.setCreateAt(seccionDTO.getCreateAt());
        this.setUpdateAt(seccionDTO.getUpdateAt());
        this.setDeleteAt(seccionDTO.getDeleteAt());
    }
}

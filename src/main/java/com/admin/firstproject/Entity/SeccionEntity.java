package com.admin.firstproject.Entity;

import com.admin.firstproject.type.SeccionDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "cod", length = 40)
    private String code;
    @Column(name = "nom")
    private Character name;


/*    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinTable(name = "seccionxgrado",
            joinColumns = {@JoinColumn(name = "seccion_id", referencedColumnName = "tb_seccion_id")},
            inverseJoinColumns = {@JoinColumn(name = "tb_grado_id", referencedColumnName = "tb_grado_id")}
    )
    private List<GradoEntity> grados= new ArrayList<>();*/

    public SeccionDTO getSeccionDTO(){
        SeccionDTO seccionDTO = new SeccionDTO();
        seccionDTO.setId(this.getUniqueIdentifier());
        seccionDTO.setCode(this.code);
        seccionDTO.setName(this.name);
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
        this.setStatus(seccionDTO.getStatus());
        this.setCreateAt(seccionDTO.getCreateAt());
        this.setUpdateAt(seccionDTO.getUpdateAt());
        this.setDeleteAt(seccionDTO.getDeleteAt());
    }
}

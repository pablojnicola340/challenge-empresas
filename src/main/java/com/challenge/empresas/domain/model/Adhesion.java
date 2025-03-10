package com.challenge.empresas.domain.model;

import java.util.Date;
import jakarta.persistence.*;

@Entity
public class Adhesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @Column(nullable = false)
    private Date fechaAdhesion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Date getFechaAdhesion() {
        return fechaAdhesion;
    }

    public void setFechaAdhesion(Date fechaAdhesion) {
        this.fechaAdhesion = fechaAdhesion;
    }
}

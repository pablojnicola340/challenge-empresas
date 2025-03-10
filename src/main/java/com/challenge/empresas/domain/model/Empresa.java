package com.challenge.empresas.domain.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cuit;

    @Column(nullable = false)
    private String razonSocial;

    @Column(nullable = false)
    private Date fechaAdhesion;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transferencia> transferencias = new ArrayList<>();

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Adhesion> adhesiones = new ArrayList<>();

    public Empresa() {
    }

    public Empresa(String cuit, String razonSocial, Date fechaAdhesion) {
        this.cuit = cuit;
        this.razonSocial = razonSocial;
        this.fechaAdhesion = fechaAdhesion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Date getFechaAdhesion() {
        return fechaAdhesion;
    }

    public void setFechaAdhesion(Date fechaAdhesion) {
        this.fechaAdhesion = fechaAdhesion;
    }

    public List<Transferencia> getTransferencias() {
        return transferencias;
    }

    public void setTransferencias(List<Transferencia> transferencias) {
        this.transferencias = transferencias;
    }

    public List<Adhesion> getAdhesiones() {
        return adhesiones;
    }

    public void setAdhesiones(List<Adhesion> adhesiones) {
        this.adhesiones = adhesiones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empresa empresa = (Empresa) o;
        return Objects.equals(cuit, empresa.cuit) &&
                Objects.equals(razonSocial, empresa.razonSocial) &&
                Objects.equals(fechaAdhesion, empresa.fechaAdhesion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cuit, razonSocial, fechaAdhesion);
    }
}

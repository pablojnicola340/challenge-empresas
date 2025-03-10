package com.challenge.empresas.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal importe;

    @ManyToOne
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @Column(nullable = false)
    private String cuentaDebito;

    @Column(nullable = false)
    private String cuentaCredito;

    @Column(nullable = false)
    private Date fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getCuentaDebito() {
        return cuentaDebito;
    }

    public void setCuentaDebito(String cuentaDebito) {
        this.cuentaDebito = cuentaDebito;
    }

    public String getCuentaCredito() {
        return cuentaCredito;
    }

    public void setCuentaCredito(String cuentaCredito) {
        this.cuentaCredito = cuentaCredito;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}

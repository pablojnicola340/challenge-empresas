package com.challenge.empresas.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public class EmpresaRequestDTO {

    @NotBlank(message = "El CUIT es obligatorio")
    private String cuit;

    @NotBlank(message = "La Razón Social es obligatoria")
    private String razonSocial;

    public EmpresaRequestDTO(String cuit, String razonSocial) {
        this.cuit = cuit;
        this.razonSocial = razonSocial;
    }

    public EmpresaRequestDTO() {
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
}

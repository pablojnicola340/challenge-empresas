package com.challenge.empresas.infrastructure.factory;

import com.challenge.empresas.adapter.in.web.dto.EmpresaRequestDTO;
import com.challenge.empresas.domain.model.Empresa;
import com.challenge.empresas.domain.model.Adhesion;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class EmpresaFactory {

    public Empresa crearEmpresa(EmpresaRequestDTO empresaRequestDTO) {
        Empresa empresa = new Empresa();
        empresa.setCuit(empresaRequestDTO.getCuit());
        empresa.setRazonSocial(empresaRequestDTO.getRazonSocial());
        return empresa;
    }

    public Adhesion crearAdhesion(Empresa empresa) {
        Adhesion adhesion = new Adhesion();
        adhesion.setEmpresa(empresa);
        adhesion.setFechaAdhesion(new Date());
        return adhesion;
    }
}

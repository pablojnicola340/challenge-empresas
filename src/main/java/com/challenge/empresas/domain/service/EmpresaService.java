package com.challenge.empresas.domain.service;

import com.challenge.empresas.adapter.in.web.dto.EmpresaRequestDTO;
import com.challenge.empresas.domain.model.Empresa;
import java.util.List;

public interface EmpresaService {
    List<Empresa> obtenerEmpresasConTransferenciasUltimoMes();
    List<Empresa> obtenerEmpresasAdheridasUltimoMes();
    void adherirEmpresa(EmpresaRequestDTO empresa);
}

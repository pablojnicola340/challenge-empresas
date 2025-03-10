package com.challenge.empresas.domain.service.impl;

import com.challenge.empresas.adapter.in.web.dto.EmpresaRequestDTO;
import com.challenge.empresas.domain.exception.ValidationException;
import com.challenge.empresas.domain.model.Empresa;
import com.challenge.empresas.domain.model.Adhesion;
import com.challenge.empresas.adapter.out.persistence.EmpresaRepository;
import com.challenge.empresas.adapter.out.persistence.TransferenciaRepository;
import com.challenge.empresas.adapter.out.persistence.AdhesionRepository;
import com.challenge.empresas.domain.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final TransferenciaRepository transferenciaRepository;
    private final AdhesionRepository adhesionRepository;

    @Autowired
    public EmpresaServiceImpl(EmpresaRepository empresaRepository, TransferenciaRepository transferenciaRepository, AdhesionRepository adhesionRepository) {
        this.empresaRepository = empresaRepository;
        this.transferenciaRepository = transferenciaRepository;
        this.adhesionRepository = adhesionRepository;
    }

    @Override
    public List<Empresa> obtenerEmpresasConTransferenciasUltimoMes() {
        Date fechaLimite = new Date(System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000); // Últimos 30 días
        return transferenciaRepository.findDistinctEmpresasByFechaAfter(fechaLimite);
    }

    @Override
    public List<Empresa> obtenerEmpresasAdheridasUltimoMes() {
        Date fechaLimite = new Date(System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000);
        return adhesionRepository.findEmpresasByFechaAdhesionAfter(fechaLimite);
    }

    @Override
    public void adherirEmpresa(EmpresaRequestDTO empresaRequestDTO) {
        if (empresaRequestDTO.getCuit() == null || empresaRequestDTO.getCuit().trim().isEmpty()) {
            throw new ValidationException("El CUIT es obligatorio");
        }
        if (empresaRequestDTO.getRazonSocial() == null || empresaRequestDTO.getRazonSocial().trim().isEmpty()) {
            throw new ValidationException("La Razón Social es obligatoria");
        }

        Empresa empresa = new Empresa();
        empresa.setCuit(empresaRequestDTO.getCuit());
        empresa.setRazonSocial(empresaRequestDTO.getRazonSocial());

        empresaRepository.save(empresa);
        Adhesion adhesion = new Adhesion();
        adhesion.setEmpresa(empresa);
        adhesion.setFechaAdhesion(new Date());
        adhesionRepository.save(adhesion);
    }
}

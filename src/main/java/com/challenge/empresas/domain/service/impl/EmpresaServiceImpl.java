package com.challenge.empresas.domain.service.impl;

import com.challenge.empresas.adapter.out.persistence.AdhesionRepository;
import com.challenge.empresas.adapter.out.persistence.EmpresaRepository;
import com.challenge.empresas.domain.exception.ValidationException;
import com.challenge.empresas.domain.model.Empresa;
import com.challenge.empresas.domain.service.EmpresaService;
import com.challenge.empresas.domain.strategy.FiltroEmpresasStrategy;
import com.challenge.empresas.infrastructure.factory.EmpresaFactory;
import com.challenge.empresas.adapter.in.web.dto.EmpresaRequestDTO;
import com.challenge.empresas.domain.model.Adhesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final Map<String, FiltroEmpresasStrategy> estrategias;
    private final EmpresaRepository empresaRepository;
    private final AdhesionRepository adhesionRepository;
    private final EmpresaFactory empresaFactory;

    @Autowired
    public EmpresaServiceImpl(List<FiltroEmpresasStrategy> estrategias,
                              EmpresaRepository empresaRepository,
                              AdhesionRepository adhesionRepository,
                              EmpresaFactory empresaFactory) {
        this.estrategias = estrategias.stream()
                .collect(Collectors.toMap(
                        estrategia -> estrategia.getClass().getSimpleName(),
                        Function.identity()
                ));
        this.empresaRepository = empresaRepository;
        this.adhesionRepository = adhesionRepository;
        this.empresaFactory = empresaFactory;
    }

    @Override
    public List<Empresa> obtenerEmpresasConTransferenciasUltimoMes() {
        Date fechaLimite = calcularFechaLimite(30);
        return estrategias.get("FiltroTransferenciasUltimoMes").filtrarEmpresas(fechaLimite);
    }

    @Override
    public List<Empresa> obtenerEmpresasAdheridasUltimoMes() {
        Date fechaLimite = calcularFechaLimite(30);
        return estrategias.get("FiltroAdhesionUltimoMes").filtrarEmpresas(fechaLimite);
    }

    @Override
    public void adherirEmpresa(EmpresaRequestDTO empresaRequestDTO) {
        if (empresaRequestDTO.getCuit() == null || empresaRequestDTO.getCuit().trim().isEmpty()) {
            throw new ValidationException("El CUIT es obligatorio");
        }
        if (empresaRequestDTO.getRazonSocial() == null || empresaRequestDTO.getRazonSocial().trim().isEmpty()) {
            throw new ValidationException("La Razón Social es obligatoria");
        }

        Empresa empresa = empresaFactory.crearEmpresa(empresaRequestDTO);
        empresaRepository.save(empresa);

        Adhesion adhesion = empresaFactory.crearAdhesion(empresa);
        adhesionRepository.save(adhesion);
    }

    private Date calcularFechaLimite(int dias) {
        return new Date(System.currentTimeMillis() - (long) dias * 24 * 60 * 60 * 1000);
    }
}

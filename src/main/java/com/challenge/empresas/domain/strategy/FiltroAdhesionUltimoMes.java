package com.challenge.empresas.domain.strategy;

import com.challenge.empresas.adapter.out.persistence.AdhesionRepository;
import com.challenge.empresas.domain.model.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FiltroAdhesionUltimoMes implements FiltroEmpresasStrategy {

    private final AdhesionRepository adhesionRepository;

    @Autowired
    public FiltroAdhesionUltimoMes(AdhesionRepository adhesionRepository) {
        this.adhesionRepository = adhesionRepository;
    }

    @Override
    public List<Empresa> filtrarEmpresas(Date fechaLimite) {
        return adhesionRepository.findEmpresasByFechaAdhesionAfter(fechaLimite);
    }
}

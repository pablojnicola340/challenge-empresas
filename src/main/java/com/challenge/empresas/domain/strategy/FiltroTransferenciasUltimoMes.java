package com.challenge.empresas.domain.strategy;

import com.challenge.empresas.adapter.out.persistence.TransferenciaRepository;
import com.challenge.empresas.domain.model.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FiltroTransferenciasUltimoMes implements FiltroEmpresasStrategy {

    private final TransferenciaRepository transferenciaRepository;

    @Autowired
    public FiltroTransferenciasUltimoMes(TransferenciaRepository transferenciaRepository) {
        this.transferenciaRepository = transferenciaRepository;
    }

    @Override
    public List<Empresa> filtrarEmpresas(Date fechaLimite) {
        return transferenciaRepository.findDistinctEmpresasByFechaAfter(fechaLimite);
    }
}

package com.challenge.empresas.domain.strategy;

import com.challenge.empresas.domain.model.Empresa;

import java.util.Date;
import java.util.List;

public interface FiltroEmpresasStrategy {
    List<Empresa> filtrarEmpresas(Date fechaLimite);
}

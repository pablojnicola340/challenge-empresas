package com.challenge.empresas.domain.service;

import com.challenge.empresas.adapter.in.web.dto.EmpresaRequestDTO;
import com.challenge.empresas.adapter.out.persistence.AdhesionRepository;
import com.challenge.empresas.adapter.out.persistence.EmpresaRepository;
import com.challenge.empresas.adapter.out.persistence.TransferenciaRepository;
import com.challenge.empresas.domain.exception.ValidationException;
import com.challenge.empresas.domain.model.Adhesion;
import com.challenge.empresas.domain.model.Empresa;
import com.challenge.empresas.domain.service.impl.EmpresaServiceImpl;
import com.challenge.empresas.domain.strategy.FiltroAdhesionUltimoMes;
import com.challenge.empresas.domain.strategy.FiltroTransferenciasUltimoMes;
import com.challenge.empresas.infrastructure.factory.EmpresaFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmpresaServiceImplTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private TransferenciaRepository transferenciaRepository;

    @Mock
    private AdhesionRepository adhesionRepository;

    @Mock
    private FiltroTransferenciasUltimoMes filtroTransferenciasUltimoMes;

    @Mock
    private FiltroAdhesionUltimoMes filtroAdhesionUltimoMes;

    @Mock
    private EmpresaFactory empresaFactory;

    private EmpresaServiceImpl empresaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        empresaService = new EmpresaServiceImpl(
                List.of(filtroTransferenciasUltimoMes, filtroAdhesionUltimoMes),
                empresaRepository,
                adhesionRepository,
                empresaFactory
        );
    }

    @Test
    void obtenerEmpresasConTransferenciasUltimoMes_DebeRetornarListaDeEmpresas() {
        Empresa empresa1 = new Empresa("30-12345678-9", "Empresa 1", new Date());
        Empresa empresa2 = new Empresa("30-87654321-0", "Empresa 2", new Date());
        List<Empresa> empresas = Arrays.asList(empresa1, empresa2);

        when(filtroTransferenciasUltimoMes.filtrarEmpresas(any(Date.class))).thenReturn(empresas);

        List<Empresa> resultado = empresaService.obtenerEmpresasConTransferenciasUltimoMes();

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(empresa1));
        assertTrue(resultado.contains(empresa2));
        verify(filtroTransferenciasUltimoMes, times(1)).filtrarEmpresas(any(Date.class));
    }

    @Test
    void obtenerEmpresasConTransferenciasUltimoMes_DebeRetornarListaVacia() {
        when(filtroTransferenciasUltimoMes.filtrarEmpresas(any(Date.class))).thenReturn(Collections.emptyList());

        List<Empresa> resultado = empresaService.obtenerEmpresasConTransferenciasUltimoMes();

        assertTrue(resultado.isEmpty());
        verify(filtroTransferenciasUltimoMes, times(1)).filtrarEmpresas(any(Date.class));
    }

    @Test
    void obtenerEmpresasAdheridasUltimoMes_DebeRetornarListaDeEmpresas() {
        Empresa empresa1 = new Empresa("30-12345678-9", "Empresa 1", new Date());
        Empresa empresa2 = new Empresa("30-87654321-0", "Empresa 2", new Date());
        List<Empresa> empresas = Arrays.asList(empresa1, empresa2);

        when(filtroAdhesionUltimoMes.filtrarEmpresas(any(Date.class))).thenReturn(empresas);

        List<Empresa> resultado = empresaService.obtenerEmpresasAdheridasUltimoMes();

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(empresa1));
        assertTrue(resultado.contains(empresa2));
        verify(filtroAdhesionUltimoMes, times(1)).filtrarEmpresas(any(Date.class));
    }

    @Test
    void obtenerEmpresasAdheridasUltimoMes_DebeRetornarListaVacia() {
        when(filtroAdhesionUltimoMes.filtrarEmpresas(any(Date.class))).thenReturn(Collections.emptyList());

        List<Empresa> resultado = empresaService.obtenerEmpresasAdheridasUltimoMes();

        assertTrue(resultado.isEmpty());
        verify(filtroAdhesionUltimoMes, times(1)).filtrarEmpresas(any(Date.class));
    }

    @Test
    void adherirEmpresa_DebeGuardarEmpresaYAdhesion() {
        EmpresaRequestDTO requestDTO = new EmpresaRequestDTO("30-12345678-9", "Empresa Test");
        Empresa empresa = new Empresa(requestDTO.getCuit(), requestDTO.getRazonSocial(), new Date());
        Adhesion adhesion = new Adhesion();
        adhesion.setEmpresa(empresa);
        adhesion.setFechaAdhesion(new Date());

        when(empresaFactory.crearEmpresa(requestDTO)).thenReturn(empresa);
        when(empresaFactory.crearAdhesion(empresa)).thenReturn(adhesion);
        when(empresaRepository.save(empresa)).thenReturn(empresa);
        when(adhesionRepository.save(adhesion)).thenReturn(adhesion);

        empresaService.adherirEmpresa(requestDTO);

        verify(empresaFactory, times(1)).crearEmpresa(requestDTO);
        verify(empresaFactory, times(1)).crearAdhesion(empresa);
        verify(empresaRepository, times(1)).save(empresa);
        verify(adhesionRepository, times(1)).save(adhesion);
    }

    @Test
    void adherirEmpresa_CuitVacio_DebeLanzarValidationException() {
        EmpresaRequestDTO requestDTO = new EmpresaRequestDTO("", "Empresa Test");

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            empresaService.adherirEmpresa(requestDTO);
        });

        assertEquals("El CUIT es obligatorio", exception.getMessage());
        verify(empresaRepository, never()).save(any(Empresa.class));
        verify(adhesionRepository, never()).save(any(Adhesion.class));
    }

    @Test
    void adherirEmpresa_RazonSocialVacia_DebeLanzarValidationException() {
        EmpresaRequestDTO requestDTO = new EmpresaRequestDTO("30-12345678-9", "");

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            empresaService.adherirEmpresa(requestDTO);
        });

        assertEquals("La Razón Social es obligatoria", exception.getMessage());
        verify(empresaRepository, never()).save(any(Empresa.class));
        verify(adhesionRepository, never()).save(any(Adhesion.class));
    }
}
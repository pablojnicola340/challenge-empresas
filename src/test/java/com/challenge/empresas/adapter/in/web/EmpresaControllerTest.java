package com.challenge.empresas.adapter.in.web;

import com.challenge.empresas.adapter.in.web.dto.EmpresaRequestDTO;
import com.challenge.empresas.domain.model.Empresa;
import com.challenge.empresas.domain.service.EmpresaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EmpresaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmpresaService empresaService;

    @InjectMocks
    private EmpresaController empresaController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(empresaController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void obtenerEmpresasConTransferenciasUltimoMes_DebeRetornarListaDeEmpresas() throws Exception {
        Empresa empresa = new Empresa("30-12345678-9", "Empresa Test", new Date());
        List<Empresa> empresas = Collections.singletonList(empresa);
        when(empresaService.obtenerEmpresasConTransferenciasUltimoMes()).thenReturn(empresas);

        mockMvc.perform(get("/empresas/transferencias-ultimo-mes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].cuit").value(empresa.getCuit()))
                .andExpect(jsonPath("$[0].razonSocial").value(empresa.getRazonSocial()));

        verify(empresaService, times(1)).obtenerEmpresasConTransferenciasUltimoMes();
    }

    @Test
    void obtenerEmpresasConTransferenciasUltimoMes_DebeRetornarNoContent() throws Exception {
        when(empresaService.obtenerEmpresasConTransferenciasUltimoMes()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/empresas/transferencias-ultimo-mes"))
                .andExpect(status().isNoContent());

        verify(empresaService, times(1)).obtenerEmpresasConTransferenciasUltimoMes();
    }

    @Test
    void obtenerEmpresasAdheridasUltimoMes_DebeRetornarListaDeEmpresas() throws Exception {
        Empresa empresa = new Empresa("30-12345678-9", "Empresa Test", new Date());
        List<Empresa> empresas = Collections.singletonList(empresa);
        when(empresaService.obtenerEmpresasAdheridasUltimoMes()).thenReturn(empresas);

        mockMvc.perform(get("/empresas/adheridas-ultimo-mes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].cuit").value(empresa.getCuit()))
                .andExpect(jsonPath("$[0].razonSocial").value(empresa.getRazonSocial()));

        verify(empresaService, times(1)).obtenerEmpresasAdheridasUltimoMes();
    }

    @Test
    void obtenerEmpresasAdheridasUltimoMes_DebeRetornarNoContent() throws Exception {
        when(empresaService.obtenerEmpresasAdheridasUltimoMes()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/empresas/adheridas-ultimo-mes"))
                .andExpect(status().isNoContent());

        verify(empresaService, times(1)).obtenerEmpresasAdheridasUltimoMes();
    }

    @Test
    void adherirEmpresa_DebeRetornarCreated() throws Exception {
        EmpresaRequestDTO requestDTO = new EmpresaRequestDTO("30-12345678-9", "Empresa Test");
        doNothing().when(empresaService).adherirEmpresa(any(EmpresaRequestDTO.class));

        mockMvc.perform(post("/empresas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated());

        verify(empresaService, times(1)).adherirEmpresa(any(EmpresaRequestDTO.class));
    }
}

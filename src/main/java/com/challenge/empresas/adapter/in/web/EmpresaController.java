package com.challenge.empresas.adapter.in.web;

import com.challenge.empresas.adapter.in.web.dto.EmpresaRequestDTO;
import com.challenge.empresas.adapter.in.web.utils.Sanitizer;
import com.challenge.empresas.domain.exception.ValidationException;
import com.challenge.empresas.domain.model.Empresa;
import com.challenge.empresas.domain.service.EmpresaService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/empresas", produces = "application/json")
@Tag(name = "Empresas", description = "Operaciones relacionadas con la gestión de empresas")
@OpenAPIDefinition(info = @Info(title = "API Empresas", version = "1.0", description = "Operaciones para gestionar empresas"))
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping("/transferencias-ultimo-mes")
    @Operation(
            summary = "Obtener empresas con transferencias en el último mes",
            description = "Devuelve una lista de empresas que realizaron transferencias en el último mes."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresas encontradas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Empresa.class))),
            @ApiResponse(responseCode = "204", description = "No hay empresas con transferencias en el último mes",
                    content = @Content)
    })
    public ResponseEntity<List<Empresa>> obtenerEmpresasConTransferenciasUltimoMes() {
        List<Empresa> empresas = empresaService.obtenerEmpresasConTransferenciasUltimoMes();
        if (empresas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Devuelve 204 si la lista está vacía
        }
        return new ResponseEntity<>(empresas, HttpStatus.OK); // Devuelve 200 con la lista de empresas
    }

    @GetMapping("/adheridas-ultimo-mes")
    @Operation(
            summary = "Obtener empresas adheridas en el último mes",
            description = "Devuelve una lista de empresas que se adhirieron en el último mes."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresas encontradas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Empresa.class))),
            @ApiResponse(responseCode = "204", description = "No hay empresas adheridas en el último mes",
                    content = @Content)
    })
    public ResponseEntity<List<Empresa>> obtenerEmpresasAdheridasUltimoMes() {
        List<Empresa> empresas = empresaService.obtenerEmpresasAdheridasUltimoMes();
        if (empresas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Devuelve 204 si la lista está vacía
        }
        return new ResponseEntity<>(empresas, HttpStatus.OK); // Devuelve 200 con la lista de empresas
    }

    @PostMapping
    @Operation(
            summary = "Adherir una nueva empresa",
            description = "Registra una nueva empresa en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empresa adherida correctamente",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content)
    })
    public ResponseEntity<Void> adherirEmpresa(
            @Parameter(description = "Datos de la empresa a adherir", required = true)
            @RequestBody EmpresaRequestDTO empresaRequestDTO) {

        // Sanitizo los datos de entrada para XSS
        String cuitSanitizado = Sanitizer.sanitizeNumber(empresaRequestDTO.getCuit());
        String razonSocialSanitizada = Sanitizer.sanitizeString(empresaRequestDTO.getRazonSocial());

        // Sanitizo para SQL Injection (capa adicional de seguridad)
        cuitSanitizado = Sanitizer.sanitizeForSQL(cuitSanitizado);
        razonSocialSanitizada = Sanitizer.sanitizeForSQL(razonSocialSanitizada);

        if (empresaRequestDTO.getCuit() == null || empresaRequestDTO.getRazonSocial() == null) {
            throw new ValidationException("CUIT y Razón Social son obligatorios");
        }

        // Actualizo el DTO con los datos sanitizados
        empresaRequestDTO.setCuit(cuitSanitizado);
        empresaRequestDTO.setRazonSocial(razonSocialSanitizada);

        empresaService.adherirEmpresa(empresaRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

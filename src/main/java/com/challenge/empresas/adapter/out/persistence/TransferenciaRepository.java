package com.challenge.empresas.adapter.out.persistence;

import com.challenge.empresas.domain.model.Empresa;
import com.challenge.empresas.domain.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
    @Query("SELECT DISTINCT t.empresa FROM Transferencia t WHERE t.fecha > :fechaLimite")
    List<Empresa> findDistinctEmpresasByFechaAfter(Date fechaLimite);
}

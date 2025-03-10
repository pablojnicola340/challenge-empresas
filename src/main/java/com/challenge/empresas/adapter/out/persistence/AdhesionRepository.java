package com.challenge.empresas.adapter.out.persistence;

import com.challenge.empresas.domain.model.Adhesion;
import com.challenge.empresas.domain.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface AdhesionRepository extends JpaRepository<Adhesion, Long> {
    @Query("SELECT DISTINCT a.empresa FROM Adhesion a WHERE a.fechaAdhesion > :fechaLimite")
    List<Empresa> findEmpresasByFechaAdhesionAfter(@Param("fechaLimite") Date fechaLimite);
}

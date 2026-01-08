package med.vol.api.domain.consultas;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public interface ConsultasRepository extends JpaRepository<Consultas, Long> {
    boolean existsByMedicoIdAndFecha(Long id, @NotNull @Future LocalDateTime fecha);

    boolean existsByPacienteIdAndFechaBetween(@NotNull Long id, LocalDateTime primerHorario, LocalDateTime ultimoHorario);

    @Query(value = """
    SELECT new med.vol.api.consultas
    .DatosRelatoriosConsultaMedica(
        COUNT(*) AS totalConsultas,
        SUM(CASE WHEN c.motivo_cancelamiento IS NOT NULL THEN 1 ELSE 0 END) AS consultasCanceladas,
        SUM(CASE WHEN c.motivo_cancelamiento IS NULL THEN 1 ELSE 0 END) AS consultasRealizadas,
        MONTHNAME(c.fecha) AS mes
    FROM consultas c
    WHERE MONTH(c.fecha) = :mes AND YEAR(c.fecha) = :anio
""", nativeQuery = true)
    DatosRelatoriosConsultaMedica generarRelatorioMensual(@Param("mes") Month mes, @Param("anio") int anio);
}

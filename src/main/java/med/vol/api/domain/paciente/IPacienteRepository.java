package med.vol.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IPacienteRepository extends JpaRepository<Paciente,Long> {
    @Query("""
            select p.activo
            from Paciente p
            where
            p.id = :idPaciente
            """)
    boolean findActivoById(@NotNull Long idPaciente);
}

package med.vol.api.domain.consultas;

import med.vol.api.domain.medico.Medico;
import med.vol.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

public record DatosDetallesConsultas(
        Long id,
        Long idPaciente,
        Long idMedico,
        LocalDateTime fecha
) {
    public DatosDetallesConsultas(Consultas datos){
        this(
                datos.getId(), datos.getPaciente().getId(),datos.getMedico().getId(),datos.getFecha()
        );
    }
}

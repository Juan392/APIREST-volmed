package med.vol.api.domain.paciente;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;
import med.vol.api.domain.direccion.DatosDireccion;

public record DatosActualizacionPaciente(
        @NotNull @JsonAlias("id")
        Long id,
        String nombre,
        DatosDireccion direccion,
        String telefono
) {
}

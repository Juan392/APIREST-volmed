package med.vol.api.domain.medico;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;
import med.vol.api.domain.direccion.DatosDireccion;

public record DatosActualizacionMedico(
        @NotNull @JsonAlias("id")
        Long id,
        @JsonAlias("nombre")
        String nombre,
        @JsonAlias("email")
        DatosDireccion direccion,
        @JsonAlias("telefono")
        String telefono
) {
}

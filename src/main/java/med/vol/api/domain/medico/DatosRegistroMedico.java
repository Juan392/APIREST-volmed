package med.vol.api.domain.medico;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.vol.api.domain.direccion.DatosDireccion;

public record DatosRegistroMedico(
        //Hacer validaciones
        @NotBlank @JsonAlias("nombre")
        String nombre,
        @NotBlank @Email @JsonAlias("email")
        String email,
        @NotBlank @Pattern(regexp = "^(\\+52)?\\d{10}$") @JsonAlias("telefono")
        String telefono,
        @NotBlank @Pattern (regexp = "^[A-Z]{4}\\d{6}[HM][A-Z]{5}[A-Z0-9]{2}$\n")@JsonAlias("documento")
        String documento,
        @NotNull @JsonAlias("especialidad")
        Especialidad especialidad,
        @NotNull @Valid @JsonAlias("direccion")
        DatosDireccion direccion
) {
}

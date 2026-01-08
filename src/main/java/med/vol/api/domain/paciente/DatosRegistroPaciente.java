package med.vol.api.domain.paciente;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.vol.api.domain.direccion.DatosDireccion;
import med.vol.api.domain.direccion.Direccion;

public record DatosRegistroPaciente(
        @NotBlank String nombre,
        @NotBlank @Email String email,
        @NotNull String telefono,
        @NotBlank @Pattern(regexp = "\\d{7,9}") String documento,
        @NotNull DatosDireccion direccion
) {
}
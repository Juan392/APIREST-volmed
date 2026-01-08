package med.vol.api.domain.direccion;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DatosDireccion(
        @NotBlank @JsonAlias("calle")
        String calle,
        @NotBlank @JsonAlias("numero")
        String numero,
        @NotBlank @JsonAlias("complemento")
        String complemento,
        @NotBlank @JsonAlias("barrio")
        String barrio,
        @NotBlank @Pattern(regexp = "\\d{4,8}") @JsonAlias("codigo_postal")
        String codigo_postal,
        @NotBlank @JsonAlias("ciudad")
        String ciudad,
        @NotBlank @JsonAlias("estado")
        String estado
) {
}

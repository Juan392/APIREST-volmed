package med.vol.api.domain.paciente;

import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class DatosListaPacienteModelAssembler implements RepresentationModelAssembler<DatosListarPaciente,EntityModel<DatosListarPaciente>> {
    // El metodo toModel convierte una instancia de DatosListaMedico en un EntityModel,
    // que es una representación envolvente que proporciona una estructura estable para el JSON y puede incluir links adicionales.
    @Override
    @NonNull
    public EntityModel<DatosListarPaciente> toModel(@NonNull DatosListarPaciente datosListarPaciente) {
        return EntityModel.of(datosListarPaciente);
    }
}

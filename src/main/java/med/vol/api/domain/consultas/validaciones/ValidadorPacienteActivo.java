package med.vol.api.domain.consultas.validaciones;

import med.vol.api.domain.ValidacionExcepcion;
import med.vol.api.domain.consultas.DatosReservaConsultas;
import med.vol.api.domain.consultas.validaciones.ValidadorDeConsultas;
import med.vol.api.domain.paciente.IPacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteActivo implements ValidadorDeConsultas {

    @Autowired
    private IPacienteRepository repository;

    public void validar(DatosReservaConsultas datos){
        var pacienteEstaActivo = repository.findActivoById(datos.idPaciente());
        if(!pacienteEstaActivo){
            throw new ValidacionExcepcion("Consulta no puede ser reservada con paciente excluido");
        }
    }
}

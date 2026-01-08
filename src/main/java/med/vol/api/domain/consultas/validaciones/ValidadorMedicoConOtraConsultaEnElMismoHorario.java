package med.vol.api.domain.consultas.validaciones;

import med.vol.api.domain.ValidacionExcepcion;
import med.vol.api.domain.consultas.ConsultasRepository;
import med.vol.api.domain.consultas.DatosReservaConsultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoConOtraConsultaEnElMismoHorario implements ValidadorDeConsultas{

    @Autowired
    private ConsultasRepository repository;

    public void validar(DatosReservaConsultas datos){
        var medicoTieneOtraConsultaEnElMismoHorario = repository.existsByMedicoIdAndFecha(datos.idMedico(), datos.fecha());
        if(medicoTieneOtraConsultaEnElMismoHorario){
            throw new ValidacionExcepcion("Medico ya tiene otra consulta en esa misma fecha y hora");
        }
    }
}

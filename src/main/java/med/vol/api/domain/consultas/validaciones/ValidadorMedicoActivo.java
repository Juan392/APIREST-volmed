package med.vol.api.domain.consultas.validaciones;

import med.vol.api.domain.ValidacionExcepcion;
import med.vol.api.domain.consultas.DatosReservaConsultas;
import med.vol.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoActivo implements ValidadorDeConsultas{

    @Autowired
    private MedicoRepository repositrory;

    public void validar(DatosReservaConsultas datos) {
        //eleccion del medico opcional
        if(datos.idMedico() == null) {
            return;
        }
        var medicoEstaActivo = repositrory.findActivoById(datos.idMedico());
        if(!medicoEstaActivo){
            throw new ValidacionExcepcion("Consulta no puede ser reservada con medico excluido");
        }
    }

}

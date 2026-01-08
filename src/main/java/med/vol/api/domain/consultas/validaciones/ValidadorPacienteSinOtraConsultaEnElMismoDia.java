package med.vol.api.domain.consultas.validaciones;

import med.vol.api.domain.consultas.validaciones.ValidadorDeConsultas;
import med.vol.api.domain.ValidacionExcepcion;
import med.vol.api.domain.consultas.ConsultasRepository;
import med.vol.api.domain.consultas.DatosReservaConsultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteSinOtraConsultaEnElMismoDia implements ValidadorDeConsultas {

    @Autowired
    private ConsultasRepository repository;

    public void validar(DatosReservaConsultas datos){
        var primerHorario = datos.fecha().withHour(7);
        var ultimoHorario = datos.fecha().withHour(18);
        var pacienteTieneOtraConsultaEnElDia = repository.existsByPacienteIdAndFechaBetween(datos.idPaciente(), primerHorario, ultimoHorario);
        if(pacienteTieneOtraConsultaEnElDia) {
            throw new ValidacionExcepcion("Paciente ya tiene una consulta reservada para ese dia");
        }
    }
}

package med.vol.api.domain.consultas.validaciones;

import med.vol.api.domain.ValidacionExcepcion;
import med.vol.api.domain.consultas.DatosReservaConsultas;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorConsultaConAnticipacion implements ValidadorDeConsultas{

    public void validar(DatosReservaConsultas datos) {
        var fechaConsulta = datos.fecha();
        var ahora = LocalDateTime.now();
        var diferenciaEnMinutos = Duration.between(ahora, fechaConsulta).toMinutes();
        if(diferenciaEnMinutos < 30) {
            throw new ValidacionExcepcion("Horario seleccionado con menos de 30 minutos de anticipacion");
        }
    }
}

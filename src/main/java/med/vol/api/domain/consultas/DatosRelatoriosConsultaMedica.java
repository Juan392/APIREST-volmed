package med.vol.api.domain.consultas;

public record DatosRelatoriosConsultaMedica(
        Long totalConsultas,
        Long consultasCanceladas,
        Long consultasRealizadas,
        String mes
) {
}

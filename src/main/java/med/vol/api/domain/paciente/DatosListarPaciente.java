package med.vol.api.domain.paciente;

public record DatosListarPaciente(
        String nombre,
        String email,
        String documento
) {
    public DatosListarPaciente(Paciente paciente){
        this(
                paciente.getNombre(),
                paciente.getEmail(),
                paciente.getDocumento()
        );
    }
}

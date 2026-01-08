package med.vol.api.domain.paciente;

import med.vol.api.domain.direccion.Direccion;

public record DatosDetallesPaciente(
        Long id,
        boolean activo,
        String nombre,
        String correo,
        String telefono,
        String documento,
        Direccion direccion
) {
    public DatosDetallesPaciente(Paciente paciente){
        this(
                paciente.getId(),
                paciente.isActivo(),
                paciente.getNombre(),
                paciente.getEmail(),
                paciente.getTelefono(),
                paciente.getDocumento(),
                paciente.getDireccion()
        );
    }
}

package med.vol.api.domain.medico;


import med.vol.api.domain.direccion.Direccion;

public record DatosDetallesMedico(
        long id,
        boolean activo,
        String nombre,
        String email,
        String telefono,
        String documento,
        Especialidad especialidad,
        Direccion direccion
) {
    public DatosDetallesMedico(Medico medico){
        this(
                medico.getId(),
                medico.isActivo(),
                medico.getNombre(),
                medico.getEmail(),
                medico.getTelefono(),
                medico.getDocumento(),
                medico.getEspecialidad(),
                medico.getDireccion()
        );
    }
}

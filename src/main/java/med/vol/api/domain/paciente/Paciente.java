package med.vol.api.domain.paciente;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.vol.api.domain.direccion.Direccion;

@Entity
@Table(name = "pacientes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private boolean activo;
    private String email;
    private String telefono;
    private String documento;
    @Embedded
    Direccion direccion;

    public Paciente(DatosRegistroPaciente datos) {
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.documento = datos.documento();
        this.telefono = datos.telefono();
        this.direccion = new Direccion(datos.direccion());
    }

    public void actualizarPaciente(DatosActualizacionPaciente datos){
        if(datos.nombre() != null)
            this.nombre= datos.nombre();
        if(datos.telefono() !=null)
            this.telefono= datos.telefono();
        if(datos.direccion() !=null)
            this.direccion= new Direccion(datos.direccion());
    }

    public void eliminarPaciente(){
        this.activo=false;
    }
}

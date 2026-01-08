package med.vol.api.domain.medico;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.vol.api.domain.direccion.Direccion;

@Entity(name = "Medico")
@Table(name = "medicos")
@Getter //Para obtener los getters
@NoArgsConstructor //Genera un constructor sin argumentos
@AllArgsConstructor //Genera un constructor con todos los argumentos
@EqualsAndHashCode(of = "id") //Identifica si 2 objetos son iguales si el id es igual
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean activo;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    @Embedded
    private Direccion direccion;

    public Medico(DatosRegistroMedico datos) {
        this.activo = true;
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.telefono = datos.telefono();
        this.documento = datos.documento();
        this.especialidad = datos.especialidad();
        this.direccion = new Direccion(datos.direccion());
    }

    public void actualizarInformacion(@Valid DatosActualizacionMedico datos) {
        if(datos.nombre()!=null){
            this.nombre = datos.nombre();
        }
        if(datos.telefono()!=null){
            this.telefono= datos.telefono();
        }
        if(datos.direccion()!=null){
            this.direccion.actualizarDireccion(datos.direccion());
        }
    }

    public void eliminarLogico() {
        this.activo = false;
    }
}

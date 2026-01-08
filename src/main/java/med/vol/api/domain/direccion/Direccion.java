package med.vol.api.domain.direccion;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter //Para obtener los getters
@NoArgsConstructor //Genera un constructor sin argumentos
@AllArgsConstructor //Genera un constructor con todos los argumentos
@Embeddable
public class Direccion {
    private String calle;
    private String numero;
    private String complemento;
    private String barrio;
    private String codigo_postal;
    private String ciudad;
    private String estado;

    public Direccion(DatosDireccion datos){
        this.calle = datos.calle();
        this.numero = datos.numero();
        this.complemento = datos.complemento();
        this.barrio = datos.barrio();
        this.codigo_postal = datos.codigo_postal();
        this.ciudad = datos.ciudad();
        this.estado = datos.estado();
    }

    public void actualizarDireccion(DatosDireccion datos){
    if (datos.calle() !=null)
        this.calle = datos.calle();
    if(datos.numero() != null)
        this.numero = datos.numero();
    if(datos.complemento() !=null)
        this.complemento = datos.complemento();
    if(datos.barrio() != null)
        this.barrio = datos.barrio();
    if(datos.codigo_postal() != null)
        this.codigo_postal = datos.codigo_postal();
    if(datos.ciudad() != null)
        this.ciudad = datos.ciudad();
    if(datos.estado() !=null)
        this.estado = datos.estado();
    }
}


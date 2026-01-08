package med.vol.api.domain.consultas;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.vol.api.domain.ValidacionExcepcion;
import med.vol.api.domain.consultas.validaciones.ValidadorDeConsultas;
import med.vol.api.domain.medico.Medico;
import med.vol.api.domain.medico.MedicoRepository;
import med.vol.api.domain.paciente.IPacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@SecurityRequirement(name = "bearer-key")
@Service
public class ReservarConsultas {
    @Autowired
    private ConsultasRepository repository;
    @Autowired
    private IPacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private List<ValidadorDeConsultas> validadores;

    public DatosDetallesConsultas reservar(DatosReservaConsultas datos){
        if(!pacienteRepository.existsById(datos.idPaciente())){
            throw new ValidacionExcepcion("No existe un paciente con ese id");
        }

        if(datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionExcepcion("No existe un medico con ese id");
        }

        validadores.forEach(v -> v.validar(datos));
        var paciente = pacienteRepository.findById(datos.idPaciente()).get();
        var medico = elegirMedico(datos);
        if(medico== null){
            throw new ValidacionExcepcion("No existe un medico disponible");
        }
        var consulta = new Consultas(null, paciente, medico, datos.fecha(), null);
        repository.save(consulta);
        return new DatosDetallesConsultas(consulta);
    }

    private Medico elegirMedico(DatosReservaConsultas datos) {
        if(medicoRepository.existsById(datos.idMedico())){
            return medicoRepository.findById(datos.idMedico()).get();
        }
        else{
            if(datos.especialidad() == null){
                throw new ValidacionExcepcion("La especialidade es nula");
            }else{
                return medicoRepository.findByEspecialiadAndDisponibleEnLaFecha(datos.especialidad(), datos.fecha());
            }
        }
    }

    public void cancelar(DatosCancelamientoConsulta datos) {
        if (!repository.existsById(datos.idConsulta())) {
            throw new ValidacionExcepcion("Id de la consulta informado no existe!");
        }
        var consulta = repository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
    }
}

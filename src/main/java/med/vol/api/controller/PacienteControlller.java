package med.vol.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.vol.api.domain.medico.DatosDetallesMedico;
import med.vol.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/paciente")
public class PacienteControlller {
    @Autowired
    IPacienteRepository repository;
    @Autowired // PagedResourcesAssembler se usa para convertir una Page en un PagedModel.
    private PagedResourcesAssembler<DatosListarPaciente> pagedResourcesAssembler;
    @Autowired // Inyectamos nuestro DatosListaMedicoModelAssembler para convertir DatosListaMedico en EntityModel.
    private DatosListaPacienteModelAssembler datosListaPacientesModelAssembler;

    @Transactional
    @PostMapping
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroPaciente datos, UriComponentsBuilder uriComponentsBuilder){
            var paciente = new Paciente(datos);
       repository.save(paciente);
       var uri = uriComponentsBuilder.path("/paciente/{id}").buildAndExpand(paciente.getId()).toUri();
       return ResponseEntity.created(uri).body(new DatosDetallesPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<DatosListarPaciente>>> listar(@PageableDefault(size = 10, sort = {"nombre"})Pageable paginacion){
        Page<DatosListarPaciente> pagina = repository.findAll(paginacion).map(DatosListarPaciente::new);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(pagina, datosListaPacientesModelAssembler));
    }

    @Transactional
    @PutMapping
    public ResponseEntity actualizar(@RequestBody @Valid DatosActualizacionPaciente datos){
        var paciente = repository.getReferenceById(datos.id());
        paciente.actualizarPaciente(datos);
        return ResponseEntity.ok(new DatosDetallesPaciente(paciente));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable Long id){
        var paciente = repository.getReferenceById(id);
        paciente.eliminarPaciente();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id){
        var paciente = repository.getReferenceById(id);
        return  ResponseEntity.ok(new DatosDetallesPaciente(paciente));
    }
}

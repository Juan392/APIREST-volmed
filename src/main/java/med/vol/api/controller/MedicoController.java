package med.vol.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.vol.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.util.UriComponentsBuilder;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/medicos")
public class MedicoController {
    //Inyeccion de dependencias
    @Autowired
    private MedicoRepository repository;
    //Dentro de MedicoController
    @Autowired // PagedResourcesAssembler se usa para convertir una Page en un PagedModel.
    private PagedResourcesAssembler<DatosListaMedico> pagedResourcesAssembler;
    @Autowired // Inyectamos nuestro DatosListaMedicoModelAssembler para convertir DatosListaMedico en EntityModel.
    private DatosListaMedicoModelAssembler datosListaMedicoModelAssembler;
    //Garantiza que se haga junto
    @Transactional
    @PostMapping
    //Hacer validaciones y obtener las cosas
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroMedico datos, UriComponentsBuilder uriComponentsBuilder){
       var medico = new Medico(datos);
       repository.save(medico);
       var uri= uriComponentsBuilder.path("/medico/{id}").buildAndExpand(medico.getId()).toUri();
       return ResponseEntity.created(uri).body(new DatosDetallesMedico(medico));
    }

    //Hacer una paginacion
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<DatosListaMedico>>> listar(@PageableDefault(size = 10, sort = {"nombre"}) Pageable paginacion) {
        Page<DatosListaMedico> pagina = repository.findAllByActivoTrue(paginacion).map(DatosListaMedico::new);
        // Usamos el pagedResourcesAssembler y el datosListaMedicoModelAssembler para convertir la Page en un PagedModel.
        // Esto garantiza que cada objeto DatosListaMedico sea envuelto en un EntityModel, proporcionando una estructura JSON estable y permitiendo añadir links adicionales.
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(pagina, datosListaMedicoModelAssembler));
    }

    //Actualizar
    @Transactional
    @PutMapping
    public ResponseEntity actualizar(@RequestBody @Valid
                                     DatosActualizacionMedico datos){
        var medico = repository.getReferenceById(datos.id());
        medico.actualizarInformacion(datos);
        return ResponseEntity.ok(new DatosDetallesMedico(medico));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.eliminarLogico();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        return  ResponseEntity.ok(new DatosDetallesMedico(medico));
    }

}

package med.vol.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.vol.api.domain.consultas.ReservarConsultas;
import med.vol.api.domain.consultas.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.YearMonth;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/consultas")
public class ConsultasController {
    @Autowired
    private ReservarConsultas reservarConsultas;
    @Autowired
    private ConsultasRepository repository;

    @Transactional
    @PostMapping
    public ResponseEntity reservar(@RequestBody @Valid DatosReservaConsultas datos, UriComponentsBuilder uriComponentsBuilder){
       var detallesConsulta = reservarConsultas.reservar(datos);
        return ResponseEntity.ok(detallesConsulta);
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity cancelar(@RequestBody @Valid DatosCancelamientoConsulta datos){
        reservarConsultas.cancelar(datos);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("relatorio-mensual/{mes]")
    ResponseEntity<DatosRelatoriosConsultaMedica> relatarioMensual(@RequestParam YearMonth mes) {
        var month= mes.getMonth();
        var year = mes.getYear();
        DatosRelatoriosConsultaMedica datos = repository.generarRelatorioMensual(month, year);
        return ResponseEntity.ok(datos);
    }
}

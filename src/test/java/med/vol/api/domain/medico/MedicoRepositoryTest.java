package med.vol.api.domain.medico;

import jakarta.persistence.EntityManager;
import med.vol.api.domain.consultas.Consultas;
import med.vol.api.domain.direccion.DatosDireccion;
import med.vol.api.domain.direccion.Direccion;
import med.vol.api.domain.paciente.DatosRegistroPaciente;
import med.vol.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private EntityManager em;
    @Test
    @DisplayName("Deberia devolver null cuando el medico buscado existe pero no esta disponible")
    void findByEspecialidadAndDisponibleEnLaFecha() {
        var lunesSiguiente= LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
        var medico = registrarMedico("Medico1","medico1@gmail.com","123456",Especialidad.CARDIOLOGÍA);
        var paciente = registrarPaciente("Paciente1", "paciente1@gmail.com","12344");
        registrarConsulta(medico,paciente,lunesSiguiente);
        var medicoLibre = medicoRepository.findByEspecialidadAndDisponibleEnLaFecha(Especialidad.CARDIOLOGÍA, lunesSiguiente);
        assertThat(medicoLibre).isNull();
    }

    @Test
    @DisplayName("Deberia devolver medico cuando el medico buscado esta disponible en esa fecha")
    void findByEspecialidadAndDisponibleEnLaFechaEscenario2() {
        //given o arrange
        var lunesSiguienteALas10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
        var medico = registrarMedico("Medico1", "medico@gmail.com", "12345", Especialidad.CARDIOLOGIA);
        //when o act
        var medicoLibre = medicoRepository.findByEspecialidadAndDisponibleEnLaFecha(Especialidad.CARDIOLOGIA, lunesSiguienteALas10);
        //then o assert
        assertThat(medicoLibre).isEqualTo(medico);
    }



    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        em.persist(new Consultas(null, paciente, medico, fecha, null));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento) {
        var paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }

    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
        return new DatosRegistroMedico(
                nombre,
                email,
                "6145489789",
                documento,
                especialidad,
                datosDireccion()
        );
    }

    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
        return new DatosRegistroPaciente(
                nombre,
                email,
                "1234564",
                documento,
                datosDireccion()
        );
    }

    private DatosDireccion datosDireccion() {
        return new DatosDireccion(
                "calle x",
                "distrito y",
                "ciudad z",
                "123",
                "1",
                "Mex",
                "edomex"
        );
    }
}

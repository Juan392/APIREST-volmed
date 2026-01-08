package med.vol.api.controller;

import jakarta.validation.Valid;
import med.vol.api.domain.usuario.DatosAutenticacion;
import med.vol.api.domain.usuario.Usuario;
import med.vol.api.infra.security.DatosTokenJWT;
import med.vol.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController //Para que lo identifique como el controller
@RequestMapping("/login") //la ruta donde funcionara
public class AutenticacionController{
    @Autowired //inyeccion de dependecias
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager manager;
    @PostMapping
    public ResponseEntity iniciarSesion(@RequestBody @Valid DatosAutenticacion datos){
        var authenticationToken = new UsernamePasswordAuthenticationToken(datos.login(), datos.contrasena()); //representa un usuario logeado
        var autenticacion = manager.authenticate(authenticationToken); //aqui se crea el token, pasamos el token autenticado al codigo
        var tokenJWT = tokenService.generarToken( (Usuario) autenticacion.getPrincipal());
        return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
    }
}

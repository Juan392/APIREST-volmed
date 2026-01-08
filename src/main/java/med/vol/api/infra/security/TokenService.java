package med.vol.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.vol.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;
    //se crea el metodo para generar el token
    public String generarToken(Usuario usuario){
        try {
            //del objeto algortimo obtenemos la firma
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("api_voll.med") //Esto dice cual es el servidor que firma el token
                    .withSubject(usuario.getLogin())//quien se logio
                    .withExpiresAt(fechaDeExpiracion())
                    .sign(algorithm); //pasa el algortimo
        } catch (JWTCreationException exception){
            // Invalid Signing configuration / Couldn't convert Claims.
            throw new RuntimeException("Error al obtenr el token.", exception);
        }
    }

    //se crea uan fecha de expiracion para el token
    private Instant fechaDeExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-06:00"));
    }

    public String getSubject(String TokenJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    // specify any specific claim validations
                    .withIssuer("api_voll.med")
                    // reusable verifier instance
                    .build()
                    .verify(TokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
           throw new RuntimeException("Token JWT es invalido o ha expirado");
        }
    }
}

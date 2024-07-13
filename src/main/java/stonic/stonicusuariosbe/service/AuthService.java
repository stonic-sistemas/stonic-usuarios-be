package stonic.stonicusuariosbe.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;

import stonic.stonicusuariosbe.model.AuthResponse;
import stonic.stonicusuariosbe.model.LoginRequest;
import stonic.stonicusuariosbe.model.RegisterRequest;
import stonic.stonicusuariosbe.model.Usuario;
import stonic.stonicusuariosbe.model.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UsuarioRepository usuarioRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getClave()));
        UserDetails usuario =usuarioRepository.findByCorreo(request.getCorreo()).orElseThrow();
        String token=jwtService.getToken(usuario);
        return AuthResponse.builder()
            .token(token)
            .build();
    }

    public AuthResponse register(RegisterRequest request) {
    	Usuario usuario = Usuario.builder()
    			.nombres(request.getNombres())
    			.apellidos(request.getApellidos())
    			.clave(passwordEncoder.encode( request.getClave()))
				.dni(request.getDni())
				.telefono(request.getTelefono())
				.correo(request.getCorreo())
				.nickname(request.getNickname())
				.fechanacimiento(request.getFechanacimiento())
				.flagactivo(true)
				.flagverificado(false)
				.fecharegistro(LocalDateTime.now())
    			.build()
    	;
    	usuarioRepository.save(usuario);
    	return AuthResponse.builder()
				.token(jwtService.getToken(usuario))
                .build()
        ;
    }

}
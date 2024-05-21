package stonic.stonicusuariosbe.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails usuario =usuarioRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(usuario);
        return AuthResponse.builder()
            .token(token)
            .build();
    }

    public AuthResponse register(RegisterRequest request) {
    	Usuario usuario = Usuario.builder()
    			.nombre(request.getNombre())
    			.apellidos(request.getApellidos())
    			.clave(passwordEncoder.encode( request.getClave()))
				.dni(request.getDni())
				.telefono(request.getTelefono())
    			.build()
    	;
    	usuarioRepository.save(usuario);
    	return AuthResponse.builder()
				.token(jwtService.getToken(usuario))
                .build()
        ;
    }

}
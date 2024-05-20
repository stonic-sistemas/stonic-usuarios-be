package stonic.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import stonic.model.AuthResponse;
import stonic.model.LoginRequest;
import stonic.model.RegisterRequest;
import stonic.model.Usuario;
import stonic.model.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
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
    			.clave(request.getClave())
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
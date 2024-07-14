package stonic.stonicusuariosbe.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import stonic.stonicusuariosbe.model.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UsuarioRepository usuarioRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public AuthResponse login(LoginRequest request) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getClave()));

			UserDetails usuario = usuarioRepository.findByCorreo(request.getCorreo())
					.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + request.getCorreo()));

			String token = jwtService.getToken(usuario);

			return AuthResponse.builder()
					.token(token)
					.build();
		} catch (Exception e) {
			// Manejar cualquier excepción que pueda surgir durante la autenticación o búsqueda de usuario
			throw new RuntimeException("Error durante el inicio de sesión", e);
		}
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
				.rol(RolUsuario.USER)
    			.build()
    	;
    	usuarioRepository.save(usuario);
    	return AuthResponse.builder()
				.token(jwtService.getToken(usuario))
                .build()
        ;
    }

}
package stonic.stonicusuariosbe.model;

import java.io.Serial;
import java.util.Collection;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="usu_usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"correo"})})
public class Usuario implements UserDetails{
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	Integer idusuario;
	@Basic
	String nombres;
	String apellidos;
	String clave;
	String dni;
	String telefono;
	String correo;
	String nickname;
	LocalDateTime audfecharegistro;
	LocalDate fechanacimiento;
	Boolean flagactivo;
	Boolean flagverificado;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Collections.emptyList();
		//return List.of(new SimpleGrantedAuthority((rol.name())));
		//return null;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.clave;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.nombres;
	}
}

package stonic.stonicusuariosbe.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
    //Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByCorreo(String correo);
}

package app.web.scout.model.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.web.scout.model.pojo.Usuario;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {

	Optional<Usuario> findByUsuario(String usuario);

}

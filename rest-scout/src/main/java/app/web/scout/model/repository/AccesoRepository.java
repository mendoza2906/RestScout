package app.web.scout.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.web.scout.model.pojo.Usuario;

@Repository
public interface AccesoRepository extends JpaRepository<Usuario, Integer>{

}

package app.web.scout.model.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.web.scout.model.pojo.security.Privilegio;

@Repository
public interface PrivilegioRepo extends JpaRepository<Privilegio, Integer> {

	Privilegio findByCodigo(String roleRepresentante);

}

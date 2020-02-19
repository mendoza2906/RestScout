package app.web.scout.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.web.scout.model.pojo.Scout;

@Repository
public interface ScoutRepository extends JpaRepository<Scout,Integer> {
	
	Scout findByIdentificacion(String identificacion);

}

package app.web.scout.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.web.scout.model.pojo.ScoutModulo;

@Repository
public interface ScoutModuloRepository  extends JpaRepository<ScoutModulo, Integer>{
	ScoutModulo findByIdModuloAndIdScout(Integer idModulo, Integer idScout);
}

package app.web.scout.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.web.scout.model.pojo.TipoScout;

@Repository
public interface TipoScoutRepository extends JpaRepository<TipoScout, Integer>{

	@Query(value=" select ts.id as idTipoScout, ts.codigo as codigo, " +
			" ts.nombre as tipoScout, ts.descripcion as descripcion  from TipoScout ts  ")
	List<ListatTipoScouts> listarTiposScouts();
	interface ListatTipoScouts{ 
		Integer getIdTipoScout();
		String getCodigo(); 
		String getTipoScout();
		String getDescripcion();
	}
}

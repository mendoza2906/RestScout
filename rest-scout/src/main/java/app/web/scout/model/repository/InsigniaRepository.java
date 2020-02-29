package app.web.scout.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.web.scout.model.pojo.Insignia;


@Repository
public interface InsigniaRepository extends JpaRepository<Insignia, Integer> {
	
	@Query(value="select i.id as id, i.codigo as codigo, i.nombre as nombre,i.descripcion as descripcion, i.estado as estado" 
			+ " from Insignia i ")
	List<COInsignia> listarInsignias();
	interface COInsignia{
		Integer getId();
		String getCodigo();
		String getNombre();
		String getDescripcion();
		String getEstado();
	}
}

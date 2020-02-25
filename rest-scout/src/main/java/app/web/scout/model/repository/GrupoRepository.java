package app.web.scout.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import app.web.scout.model.pojo.Grupo;


@Repository
public interface GrupoRepository  extends JpaRepository<Grupo, Integer>{

	@Query(value="select g.id as id, r.id as idRama,gr.id as idGrupoRama, g.codigo as codigo, g.nombre as nombre, g.estado as estado" 
			+ " from Grupo g inner join GrupoRama gr on gr.idGrupo = g.id"
			+ " inner join Rama r on gr.idRama = r.id where r.id =(?1) ")
	List<CustObjGrupo> listarGrupos(Integer idRama);
	interface CustObjGrupo{
		Integer getId();
		Integer getIdRama();
		Integer getIdGrupoRama();
		String getCodigo();
		String getNombre();
		String getEstado();
	}
	
	@Query(value="	SELECT r.id as idRama, g.id as idGrupo, gr.id as idGrupoRama from Rama r " + 
			"	INNER JOIN GrupoRama gr ON gr.idRama = r.id " + 
			"	INNER JOIN Grupo g ON g.id = gr.idGrupo " + 
			"	WHERE gr.id = (?1) ")
	List<GrupoRama> recuperarCombosGrupoRama(Integer idGrupoRama);
	interface GrupoRama{
		Integer getIdRama();
		Integer getIdGrupo();
		Integer getIdGrupoRama();
	}
	
	@Query(value="	SELECT r.id as idRama, g.id as idGrupo, gr.id as idGrupoRama from Rama r " + 
			"	INNER JOIN GrupoRama gr ON gr.idRama = r.id " + 
			"	INNER JOIN Grupo g ON g.id = gr.idGrupo " + 
			"	INNER JOIN Scout s ON gr.id = s.idGrupoRama " + 
			"	WHERE s.id = (?1) ")
	List<GrupoRama> recuperarCombosScouts(Integer idScout);	
	
}

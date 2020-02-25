package app.web.scout.model.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.web.scout.model.pojo.Actividad;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Integer>{
	@Query(value=" select a.id as idActividad,a.codigo as codigo, " +
			" a.nombre as actividad,a.descripcion as descripcion  from Actividad a  ")
	List<ListaActividades> listarActividades();
	interface ListaActividades{ 
		Integer getIdActividad();
		String getCodigo(); 
		String getActividad();
		String getDescripcion();
	}
	
	@Query(value=" SELECT asi.id as idAsistencia,asi.fechaAsistencia as fechaAsistencia, asi.observacion as observacion,a.nombre AS actividad," +
			" gr.id as idGrupoRama,r.id as idRama, g.id as idGrupo, g.nombre AS grupo, r.nombre AS rama,a.id as idActividad " + 
			" FROM Asistencia asi " + 
			" INNER JOIN Actividad a ON a.id = asi.idActividades " + 
			" INNER JOIN DetalleAsistencia da ON da.idAsistencia = asi.id " + 
			" INNER JOIN Scout s ON s.id = da.idScout " + 
			" INNER JOIN GrupoRama gr ON gr.id= s.idGrupoRama " + 
			" INNER JOIN Grupo g ON g.id = gr.idGrupo " + 
			" INNER JOIN Rama r ON r.id = gr.idRama " + 
			" GROUP BY asi.id,asi.fechaAsistencia, asi.observacion ")
	List<ListaAsistencia> listarAsistencias();
	interface ListaAsistencia{ 
		Integer getIdAsistencia();
		Date getFechaAsistencia(); 
		String getObservacion();
		String getActividad();
		Integer getIdGrupoRama();
		Integer getIdRama();
		Integer getIdGrupo();
		String getGrupo();
		String getRama();
		Integer getIdActividad();
	}
}

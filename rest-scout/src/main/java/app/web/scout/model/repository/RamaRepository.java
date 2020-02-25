package app.web.scout.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.web.scout.model.pojo.Rama;

@Repository
public interface RamaRepository extends JpaRepository<Rama, Integer> {

//	@Query(value = "select d.id_hora as idHora, d.hora as hora, d.orden as orden from  med.fn_listar_horas_disponibles "+
//			" (:pi_id_medico,:pi_fechaCita,:pi_id_hora) as d ", nativeQuery = true)
//	public List<CustObjHoras> fnListarHoras(@Param("pi_id_medico") Integer pi_id_medico,
//			@Param("pi_fechaCita") String pi_fechaCita,@Param("pi_id_hora") Integer pi_id_hora);
//	
//	public interface CustObjHoras{
//		Integer getIdHora();
//		String getHora();
//		Integer getOrden();	
//	}
	
	@Query(value="select r.id as id, r.codigo as codigo, r.nombre as nombre, r.estado as estado" +
			" from Rama r ")
	List<CustObjRama> listarRamas();
	interface CustObjRama{
		Integer getId();
		String getCodigo();
		String getNombre();
		String getEstado();
	}	
	
}

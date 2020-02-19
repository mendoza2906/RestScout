package app.web.scout.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.web.scout.model.pojo.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer>{

	@Query(value="select per.id as idPer, per.codigo as codigo, per.nombre as nombre, per.descripcion as descripcion, " +
			" per.estado as estado from Perfil per ")
	List<CustObjPerfil> listarPerfiles();
	interface CustObjPerfil{
		Integer getIdPer();
		String getCodigo();
		String getNombre();
		String getDescripcion();
		String getEstado();
	}
}

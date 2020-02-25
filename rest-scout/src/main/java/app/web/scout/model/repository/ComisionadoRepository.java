package app.web.scout.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.web.scout.model.pojo.Comisionado;

@Repository
public interface ComisionadoRepository extends JpaRepository<Comisionado, Integer>{

	@Query(value=" select s.id as idScout, s.idTipoScout as idTipoScout, s.idGrupoRama as idGrupoRama," +
			" s.identificacion as identificacion, concat(s.apellidos,' ',s.nombres) as nombresCompletos," + 
			" ts.nombre as tipoScout ,s.direccion as direccion,s.celular as celular from  Scout s " + 
			" inner join Comisionado c on s.id = c.idScout " + 
			" inner join TipoScout ts on ts.id = s.idTipoScout ")
	List<ListComisionados> listadoComisionados();
	interface ListComisionados{ 
		Integer getIdScout();
		Integer getIdTipoScout();
		Integer getIdGrupoRama();
		String getIdentificacion(); 
		String getNombresCompletos(); 
		String getTipoScout(); 
		String getDireccion();
		String getCelular();
	}

}

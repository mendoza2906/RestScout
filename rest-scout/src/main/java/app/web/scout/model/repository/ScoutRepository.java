package app.web.scout.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.web.scout.model.pojo.Scout;
import app.web.scout.model.repository.ComisionadoRepository.ListComisionados;

@Repository
public interface ScoutRepository extends JpaRepository<Scout,Integer> {
	
	Scout findByIdentificacion(String identificacion);
	
	@Query(value=" select s.id as idScout, s.idTipoScout as idTipoScout, s.idGrupoRama as idGrupoRama," +
			" s.identificacion as identificacion, concat(s.apellidos,' ',s.nombres) as nombresCompletos," + 
			" ts.nombre as tipoScout, s.direccion as direccion,s.celular as celular from  Scout s " + 
			" inner join TipoScout ts on ts.id = s.idTipoScout " +
			" where s.id not in (select c.idScout from Comisionado c) order by s.nombres, s.apellidos asc ")
	List<ListadoScouts> listadoScouts();
	interface ListadoScouts{ 
		Integer getIdScout();
		Integer getIdTipoScout();
		Integer getIdGrupoRama();
		String getIdentificacion(); 
		String getNombresCompletos(); 
		String getTipoScout(); 
		String getDireccion();
		String getCelular();
	}


	@Query(value=" SELECT s.id as idScout,s.idTipoScout as idTipoScout, s.idGrupoRama as idGrupoRama, CONCAT(s.nombres,' ',s.apellidos) AS nombres, " + 
			"	s.identificacion as identificacion, u.id as idUsuario, u.usuario as usuario, p.id as idPerfil, p.codigo as codigo,p.descripcion as perfil " + 
			" 	FROM Scout s  " + 
			"	INNER JOIN Usuario u on u.idScout = s.id " + 
			"	INNER JOIN Perfil p on u.idPerfil = p.id " + 
			"	WHERE u.usuario = (?1)")
	List<CODatosUsuario> recuperarDatosScout(String usuario);
	interface CODatosUsuario{
		Integer getIdScout();
		Integer getIdTipoScout();
		Integer getIdGrupoRama();
		String getNombres();
		String getIdentificacion(); 
		Integer getIdUsuario();
		String getUsuario();
		Integer getIdPerfil();
		String getCodigo();
		String getPerfil();
	}

	
	@Query(value=" select s.id as idScout, " +
			" s.identificacion as identificacion, concat(s.nombres,' ',s.apellidos ) as nombresCompletos," + 
			" ts.nombre as tipoScout, s.direccion as direccion, s.celular as celular " + 
			" from Scout s " + 
			" inner join TipoScout ts on ts.id = s.idTipoScout " + 
			" where s.idGrupoRama =(?1) order by s.nombres, s.apellidos asc ")
	List<ListScouts> listarScouts(Integer idGrupoRama);
	interface ListScouts{ 
		Integer getIdScout(); 
//		Integer getIdMedico(); 
		String getIdentificacion(); 
		String getNombresCompletos(); 
		String getTipoScout(); 
		String getDireccion();
		String getCelular();
	}
	
	@Query(value=" SELECT s.id as idScout, r.nombre AS rama ,i.nombre AS insignia, i.imagen as imagen,ts.nombre as tipoScout,g.nombre AS grupo,  " + 
			"	CONCAT(s.nombres,' ', s.apellidos) AS nombres, (SELECT COUNT(m1.id) FROM Modulo m1 " + 
			"	INNER JOIN ScoutModulo sm ON m1.id= sm.idModulo WHERE sm.idScout= (?1)  AND sm.aprobado=1 AND m1.idInsignia= i.id)AS contCumplidos," +
			"	(SELECT count(m1.id) FROM Modulo m1 WHERE  m1.idInsignia= m.idInsignia) AS contTotales, i.urlFoto as urlFoto" + 
			"	FROM Rama r INNER JOIN RamasInsignia ri ON ri.idRama = r.id " + 
			"	INNER JOIN Insignia i ON i.id = ri.idInsignia " + 
			"	INNER JOIN Modulo m ON m.idInsignia = i.id " + 
			"	INNER JOIN GrupoRama gr ON gr.idRama = r.id" + 
			"	INNER JOIN Grupo g ON g.id = gr.idGrupo " + 
			"	INNER JOIN Scout s ON s.idGrupoRama = gr.id " + 
			" 	inner join TipoScout ts on ts.id = s.idTipoScout " + 
			"	WHERE s.id= (?1) AND m.id NOT IN " + 
			"	(SELECT m1.id FROM Modulo m1 INNER JOIN ScoutModulo sm ON m1.id= sm.idModulo WHERE sm.idScout=(?1) AND sm.aprobado=1) " + 
			"	 OR (i.orden = 1 AND  s.id= (?1)) AND i.orden= " + 
			"	((SELECT max(i2.orden) FROM Modulo m1 INNER JOIN ScoutModulo sm ON m1.id= sm.idModulo  " + 
			"	inner JOIN Insignia i2 ON i2.id = m1.idInsignia  WHERE sm.idScout=(?1) AND sm.aprobado=1)+1) " + 
			"	GROUP BY s.id, r.nombre,i.nombre,i.imagen,g.nombre,s.nombres, s.apellidos " +
			"	ORDER BY i.orden asc ")
	List<ListInsigniaScouts> recuperarUltimaInsigniaScout(Integer idScout);
	interface ListInsigniaScouts{ 
		Integer getIdScout(); 
		String getRama(); 
		String getInsignia(); 
		String getImagen(); 
		String getTipoScout(); 
		String getGrupo(); 
		String getNombres(); 
		Integer getContCumplidos(); 
		Integer getContTotales(); 
		String getUrlFoto(); 
	}

	@Query(value=" SELECT distinct s.id as idScout,i.id as idInsignia,i.nombre AS insignia, i.imagen as imagen,i.descripcion AS descripcion, m.id as idModulo ," + 
			"	COUNT(m.id)AS contCumplidos,(SELECT count(m1.id) FROM Modulo m1 WHERE  m1.idInsignia= m.idInsignia) AS contTotales,i.urlFoto as urlFoto " +
			"	FROM Insignia i " + 
			"	INNER JOIN Modulo m ON m.idInsignia = i.id " + 
			"	INNER JOIN ScoutModulo sm ON m.id= sm.idModulo " + 
			"	INNER JOIN Scout s ON s.id = sm.idScout " + 
			"	WHERE s.id= (?1) AND sm.aprobado=1 " + 
			"	GROUP BY s.id,i.id,i.nombre,i.imagen,i.descripcion " + 
			"	ORDER BY i.orden asc ")
	List<ListInsigniasObt> recuperarInsigniasObtenidas(Integer idScout);
	interface ListInsigniasObt{ 
		Integer getIdScout();
		Integer getIdInsignia(); 
		String getInsignia(); 
		String getImagen(); 
		String getDescripcion(); 
		Integer getIdModulo(); 
		Integer getContCumplidos(); 
		Integer getContTotales(); 
		String getUrlFoto(); 
	}
	
	@Query(value=" SELECT distinct s.id as idScout,i.id as idInsignia,sm.id as idScoutModulos, m.id as idModulo, m.nombre as modulo," + 
			"	m.descripcion as descripcion,sm.observacion  as observacion FROM Rama r " + 
			"	INNER JOIN RamasInsignia ri ON ri.idRama = r.id " + 
			"	INNER JOIN Insignia i ON i.id = ri.idInsignia " + 
			"	INNER JOIN Modulo m ON m.idInsignia = i.id " + 
			"	INNER JOIN GrupoRama gr ON gr.idRama = r.id" + 
			"	INNER JOIN Scout s ON s.idGrupoRama = gr.id " + 
			"	INNER JOIN ScoutModulo sm ON m.id= sm.idModulo " +
			"	WHERE s.id= (?1) AND sm.aprobado=1 " +  
			"	ORDER BY i.orden asc ")
	List<ListDetalleInsignias> recuperarDetalleInsignias(Integer idScout);
	interface ListDetalleInsignias{ 
		Integer getIdScout(); 
		Integer getIdInsignia(); 
		Integer getIdScoutModulos(); 
		Integer getIdModulo(); 
		String getModulo(); 
		String getDescripcion(); 
		String getObservacion(); 

	}

	@Query(value=" SELECT s.id as idScout,m.id as idModulo,m.nombre as modulo,m.descripcion as descripcion,i.id as idInsignia,i.nombre as insignia " + 
			"	FROM Rama r INNER JOIN RamasInsignia ri ON ri.idRama = r.id " + 
			"	INNER JOIN Insignia i ON i.id = ri.idInsignia " + 
			"	INNER JOIN Modulo m ON m.idInsignia = i.id " + 
			"	INNER JOIN GrupoRama gr ON gr.idRama = r.id" + 
			"	INNER JOIN Grupo g ON g.id = gr.idGrupo " + 
			"	INNER JOIN Scout s ON s.idGrupoRama = gr.id " + 
			" 	inner join TipoScout ts on ts.id = s.idTipoScout " + 
			"	WHERE s.id= (?1) AND m.id NOT IN " + 
			"	(SELECT m1.id FROM Modulo m1 INNER JOIN ScoutModulo sm ON m1.id= sm.idModulo WHERE sm.idScout=(?1) AND sm.aprobado=1) " + 
			"	 OR (i.orden = 1 AND  s.id= (?1)) AND i.orden= " + 
			"	((SELECT max(i2.orden) FROM Modulo m1 INNER JOIN ScoutModulo sm ON m1.id= sm.idModulo  " + 
			"	inner JOIN Insignia i2 ON i2.id = m1.idInsignia  WHERE sm.idScout=(?1) AND sm.aprobado=1)+1) " + 
			"	GROUP BY s.id, r.nombre,i.nombre,i.imagen,g.nombre,s.nombres, s.apellidos " +
			"	ORDER BY i.orden asc ")
	List<ListUltimoModulo> recuperarUltimaModuloInsignia(Integer idScout);
	interface ListUltimoModulo{ 
		Integer getIdScout(); 
		Integer getIdModulo(); 
		String getModulo(); 
		String getDescripcion(); 
		Integer getIdInsignia(); 
		String getInsignia(); 
	}


}

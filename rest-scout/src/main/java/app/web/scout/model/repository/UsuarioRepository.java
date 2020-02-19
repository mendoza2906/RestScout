package app.web.scout.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.web.scout.model.pojo.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	Usuario findByUsuario(String usuario); //sql que busca por atributo

	Usuario findByToken(String token);
	
	
//	@Query(value="SELECT m.id as id, m.ambiente as ambiente, m.nombre as nombre, m.url as url "
//			+ "FROM ModuloRolUsuario AS mru "
//			+ "JOIN ModuloRol AS mr ON mru.modulo_rol_id=mr.id "
//			+ "JOIN Modulo AS m ON mr.modulo_id=m.id "
//			+ "JOIN Usuario AS u ON mru.usuario_id=u.id "
//			+ "WHERE u.usuario = ?1 "
//			)
//	List<CustomObject> buscarUsuarioModulos(String usuario);
//	
//	interface CustomObject { 
//	    Integer getId(); 
//	    String getAmbiente(); 
//	    String getNombre(); 
//	    String getUrl(); 
//	  
//	} 
//	
}



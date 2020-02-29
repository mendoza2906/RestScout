package app.web.scout.model.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.web.scout.model.pojo.Noticia;

@Repository
public interface NoticiaRepository  extends JpaRepository<Noticia, Integer>{

	@Query(value=" select n.id as idNoticia, n.titulo as titulo, n.contenido as contenido," +
			" n.urlImg as urlImg, n.fechaPublic as fechaPublic, n.fuente as fuente from  Noticia n " + 
			" order by n.titulo asc ")
	List<ListadoNot> listadoNoticias();
	interface ListadoNot{ 
		Integer getIdNoticia();
		String getTitulo(); 
		String getContenido(); 
		String getUrlImg(); 
		Date getFechaPublic();
		String getFuente();
	}
}

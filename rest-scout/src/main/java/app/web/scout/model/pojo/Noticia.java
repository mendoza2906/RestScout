package app.web.scout.model.pojo;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


/**
 * The persistent class for the noticias database table.
 * 
 */
@Entity
@Table(name="noticias")
@NoArgsConstructor
public class Noticia  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_noticia")
	@Getter @Setter private Integer id;

	@Getter @Setter private String contenido;

	@Getter @Setter private String estado;

	@Column(name="url_img")
	@Getter @Setter private String urlImg;

	@Version
	@Getter @Setter private Integer version;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_public")
	@Getter @Setter private Date fechaPublic;

	@Getter @Setter private String fuente;

	@Getter @Setter private String titulo;

	@PrePersist
	void preInsert() {
	   if (this.estado == null)
	       this.estado = "A";
	   
	   if (this.fechaPublic == null)
	       this.fechaPublic = new Date();
	}

}
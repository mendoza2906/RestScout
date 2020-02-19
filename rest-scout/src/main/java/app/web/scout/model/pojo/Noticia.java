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
	@Column(name="not_codigo")
	@Getter @Setter private Integer notCodigo;

	@Column(name="not_contenido")
	@Getter @Setter private String notContenido;

	@Column(name="not_estado")
	@Getter @Setter private byte notEstado;

	@Temporal(TemporalType.DATE)
	@Column(name="not_fecha_public")
	@Getter @Setter private Date notFechaPublic;

	@Column(name="not_fuente")
	@Getter @Setter private String notFuente;

	@Column(name="not_titulo")
	@Getter @Setter private String notTitulo;


}
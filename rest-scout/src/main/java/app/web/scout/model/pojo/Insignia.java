package app.web.scout.model.pojo;

import javax.persistence.*;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the insignia database table.
 * 
 */
@Entity
@Table(name="insignia")
@Where(clause = "estado='A'")
@NoArgsConstructor
public class Insignia  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_insignia")
	@Getter @Setter private Integer id;

	@Getter @Setter private String codigo;

	@Getter @Setter private String descripcion;
	
	@Getter @Setter private String estado;
	
	@Column(name="url_foto")
	@Getter @Setter private String urlFoto;

	@Getter @Setter private String imagen;

	@Getter @Setter private String nombre;

	@Getter @Setter private Integer orden;

	@Version
	@Getter @Setter private Integer version;

	//RELACIONES
	//bi-directional many-to-one association to Modulo
	@OneToMany(mappedBy="insignia", cascade=CascadeType.ALL)
	@Getter @Setter private List<Modulo> modulos;

	//bi-directional many-to-one association to RamasInsignia
	@OneToMany(mappedBy="insignia", cascade=CascadeType.ALL)
	@Getter @Setter private List<RamasInsignia> ramasInsignias;

	@PrePersist
	void preInsert() {
	   if (this.estado == null)
	       this.estado = "A";
	}
}
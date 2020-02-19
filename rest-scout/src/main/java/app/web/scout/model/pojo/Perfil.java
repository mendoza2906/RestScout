package app.web.scout.model.pojo;

import javax.persistence.*;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the perfil database table.
 * 
 */
@Entity
@Table(schema="seg", name="perfil")
@Where(clause = "estado='A'")
@NoArgsConstructor
public class Perfil  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_perfil")
	@Getter @Setter private Integer id;

	@Getter @Setter private String codigo;

	@Getter @Setter private String descripcion;

	@Getter @Setter private String estado;

//	@Column(name="fecha_ingreso")
//	private Timestamp fechaIngreso;

	@Getter @Setter private String nombre;

	@Version
	@Getter @Setter private Integer version;

	//bi-directional many-to-one association to Acceso
	@OneToMany(mappedBy="perfil", cascade=CascadeType.ALL)
	@Getter @Setter private List<Acceso> accesos;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="perfil", cascade=CascadeType.ALL)
	@Getter @Setter private List<Usuario> usuarios;

	@PrePersist
	void preInsert() {
	   if (this.estado == null)
	       this.estado = "A";
	}
	
}
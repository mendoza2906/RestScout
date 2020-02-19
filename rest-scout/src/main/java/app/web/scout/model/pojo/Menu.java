package app.web.scout.model.pojo;

import javax.persistence.*;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the menu database table.
 * 
 */
@Entity
@Table(schema="seg", name="menu")
@Where(clause = "estado='A'")
@NoArgsConstructor
public class Menu {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_menu")
	@Getter @Setter private Integer id;

	@Getter @Setter private String descripcion;

	@Getter @Setter private String estado;

//	@Column(name="fecha_ingreso")
//	private Timestamp fechaIngreso;

	@Column(name="for_asociado")
	@Getter @Setter private Boolean forAsociado;

	@Column(name="id_menu_padre")
	@Getter @Setter private Integer idMenuPadre;

	@Column(name="nombre_frm")
	@Getter @Setter private String nombreFrm;

	@Getter @Setter private Integer posicion;

	@Version
	@Getter @Setter private Integer version;

	//bi-directional many-to-one association to Acceso
	@OneToMany(mappedBy="menu", cascade=CascadeType.ALL)
	@Getter @Setter private List<Acceso> accesos;
	
	@PrePersist
	void preInsert() {
	   if (this.estado == null)
	       this.estado = "A";
	}
	
}
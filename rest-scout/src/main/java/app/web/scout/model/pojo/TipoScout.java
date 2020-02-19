package app.web.scout.model.pojo;

import javax.persistence.*;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;


/**
 * The persistent class for the tipo_scout database table.
 * 
 */
@Entity
@Table(name="tipo_scout")
@Where(clause = "estado='A'")
@NoArgsConstructor
public class TipoScout  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo_scout")
	@Getter @Setter private Integer id;

	@Getter @Setter private String codigo;

	@Column(name="codigo_rango")
	@Getter @Setter private String codigoRango;

	@Getter @Setter private String descripcion;

	@Getter @Setter private String estado;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="fecha_ingreso")
//	@Getter @Setter private Date fechaIngreso;

	@Getter @Setter private String nombre;

	@Version
	@Getter @Setter private Integer version;

	//RELACIONES
	//bi-directional many-to-one association to Scout
	@OneToMany(mappedBy="tipoScout", cascade=CascadeType.ALL)
	@Getter @Setter private List<Scout> scouts;
	
	@PrePersist
	void preInsert() {
	   if (this.estado == null)
	       this.estado = "A";
	}

}
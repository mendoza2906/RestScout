package app.web.scout.model.pojo;

import javax.persistence.*;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;


/**
 * The persistent class for the rama database table.
 * 
 */
@Entity
@Table(name="rama")
@Where(clause = "estado='A'")
@NoArgsConstructor
public class Rama  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_rama")
	@Getter @Setter private Integer id;
	
	@Getter @Setter private String codigo;

	@Getter @Setter private String descripcion;

	@Column(name="edad_max")
	@Getter @Setter private Integer edadMax;

	@Column(name="edad_min")
	@Getter @Setter private Integer edadMin;

	@Getter @Setter private String estado;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="fecha_ingreso")
//	@Getter @Setter private Date fechaIngreso;

	@Getter @Setter private String lema;

	@Getter @Setter private String ley;

	@Getter @Setter private String nombre;

	@Column(name="num_integrantes_min")
	@Getter @Setter private Integer numIntegrantesMin;
	
	@Column(name="num_integrantes_max")
	@Getter @Setter private Integer numIntegrantesMax;

	@Getter @Setter private String oracion;

	@Getter @Setter private String promesa;

	@Getter @Setter private String saludo;

	@Getter @Setter private String senial;

	@Column(name="sistema_grupo")
	@Getter @Setter private String sistemaGrupo;

	@Version
	@Getter @Setter private Integer version;

	//RELACIONES
	//bi-directional many-to-one association to GrupoRama
	@OneToMany(mappedBy="rama", cascade=CascadeType.ALL)
	@Getter @Setter private List<GrupoRama> grupoRamas;

	//bi-directional many-to-one association to RamasInsignia
	@OneToMany(mappedBy="rama", cascade=CascadeType.ALL)
	@Getter @Setter private List<RamasInsignia> ramasInsignias;

	@PrePersist
	void preInsert() {
	   if (this.estado == null)
	       this.estado = "A";
	}
}
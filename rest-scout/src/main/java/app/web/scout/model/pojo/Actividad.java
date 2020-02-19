package app.web.scout.model.pojo;


import javax.persistence.*;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the actividades database table.
 * 
 */
@Entity
@Table(name="actividades")
@Where(clause = "estado='A'")
@NoArgsConstructor
public class Actividad  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_actividades")
	@Getter @Setter private Integer id;

	@Getter @Setter private String codigo;

	@Getter @Setter private String descripcion;

	@Getter @Setter private String estado;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="fecha_ingreso")
//	@Getter @Setter private Date fechaIngreso;

	@Getter @Setter private String nombre;

	@Version
	@Getter @Setter private Integer version;
	
	//RELACIONES
	//bi-directional many-to-one association to Asistencia
	@OneToMany(mappedBy="actividad", cascade=CascadeType.ALL)
	@Getter @Setter private List<Asistencia> asistencias;
	
	@PrePersist
	void preInsert() {
	   if (this.estado == null)
	       this.estado = "A";
	}

}
package app.web.scout.model.pojo;


import javax.persistence.*;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the asistencia database table.
 * 
 */
@Entity
@Table(name="asistencia")
@Where(clause = "estado='A'")
@NoArgsConstructor
public class Asistencia  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_asistencia")
	@Getter @Setter  private Integer id;
	
	@Column(name="id_actividades")
	@Getter @Setter private Integer idActividades;

	@Getter @Setter  private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_asistencia")
	@Getter @Setter private Date fechaAsistencia;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="fecha_ingreso")
//	@Getter @Setter  private Date fechaIngreso;

	@Getter @Setter  private String observacion;

	@Version
	@Getter @Setter private Integer version;

	//RELACIONES
	//bi-directional many-to-one association to Actividad
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_actividades", insertable=false, updatable = false)
	@JsonIgnore
	@Getter @Setter private Actividad actividad;

	//bi-directional many-to-one association to DetalleAsistencia
	@OneToMany(mappedBy="asistencia", cascade=CascadeType.ALL)
	@Getter @Setter private List<DetalleAsistencia> detalleAsistencias;
	
	@PrePersist
	void preInsert() {
	   if (this.estado == null)
	       this.estado = "A";
	}
}
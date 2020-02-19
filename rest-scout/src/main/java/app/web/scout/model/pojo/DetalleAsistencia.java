package app.web.scout.model.pojo;


import javax.persistence.*;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



/**
 * The persistent class for the detalle_asistencia database table.
 * 
 */
@Entity
@Table(name="detalle_asistencia")
@Where(clause = "estado='A'")
@NoArgsConstructor
public class DetalleAsistencia  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_detalle_asistencia")
	@Getter @Setter private Integer id;
	
	@Column(name="id_scout")
	@Getter @Setter private Integer idScout;
	
	@Column(name="id_asistencia")
	@Getter @Setter private Integer idAsistencia;

	@Getter @Setter private Boolean asistio;

	@Getter @Setter private String estado;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="fecha_ingreso")
//	@Getter @Setter private Date fechaIngreso;

	@Version
	@Getter @Setter private Integer version;

	//RELACIONES
	//bi-directional many-to-one association to Asistencia
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_asistencia", insertable=false, updatable = false)
	@JsonIgnore
	@Getter @Setter private Asistencia asistencia;

	//bi-directional many-to-one association to Scout
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_scout", insertable=false, updatable = false)
	@JsonIgnore
	@Getter @Setter private Scout scout;

	@PrePersist
	void preInsert() {
	   if (this.estado == null)
	       this.estado = "A";
	}

}
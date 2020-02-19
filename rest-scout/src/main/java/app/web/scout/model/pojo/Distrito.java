package app.web.scout.model.pojo;


import javax.persistence.*;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


/**
 * The persistent class for the distrito database table.
 * 
 */
@Entity
@Table(name="distrito")
@Where(clause = "estado='A'")
@NoArgsConstructor
public class Distrito  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_distrito")
	@Getter @Setter private Integer id;
	
	@Column(name="id_comisionado")
	@Getter @Setter private Integer idComisionado;

	@Getter @Setter private String email;

	@Getter @Setter private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_creacion")
	@Getter @Setter private Date fechaCreacion;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="fecha_ingreso")
//	@Getter @Setter private Date fechaIngreso;

	@Getter @Setter private String historia;

	@Getter @Setter private String mision;

	@Getter @Setter private String nombre;

	@Column(name="quienes_somos")
	@Getter @Setter private String quienesSomos;

	@Getter @Setter private String telefono;

	@Version
	@Getter @Setter private Integer version;

	@Getter @Setter private String vision;

	//RELACIONES
	//bi-directional many-to-one association to Comisionado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_comisionado", insertable=false, updatable = false)
	@JsonIgnore
	@Getter @Setter private Comisionado comisionado;

	@PrePersist
	void preInsert() {
	   if (this.estado == null)
	       this.estado = "A";
	}
	

}
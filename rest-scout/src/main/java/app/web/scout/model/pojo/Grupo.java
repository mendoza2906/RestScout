package app.web.scout.model.pojo;


import javax.persistence.*;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the grupo database table.
 * 
 */
@Entity
@Table(name="grupo")
@Where(clause = "estado='A'")
@NoArgsConstructor
public class Grupo  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_grupo")
	@Getter @Setter private Integer id;

	@Column(name="dia_actividades")
	@Getter @Setter private String diaActividades;

	@Getter @Setter private String email;
	
	@Getter @Setter private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_crea")
	@Getter @Setter private Date fechaCrea;
	
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="fecha_ingreso")
//	private Date fechaIngreso;

	@Getter @Setter private String historia;

	@Column(name="hora_activo")
	@Getter @Setter private Time horaActivo;

	@Getter @Setter private String nombre;

	@Getter @Setter private String panoleta;

	@Getter @Setter private String telefono;
	
	@Version
	@Getter @Setter private Integer version;

	//RELACIONES
	//bi-directional many-to-one association to GrupoRama
	@OneToMany(mappedBy="grupo", cascade=CascadeType.ALL)
	@Getter @Setter private List<GrupoRama> grupoRamas;

	@PrePersist
	void preInsert() {
	   if (this.estado == null)
	       this.estado = "A";
	}

}
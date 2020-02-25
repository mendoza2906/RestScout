package app.web.scout.model.pojo;


import javax.persistence.*;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the modulo database table.
 * 
 */
@Entity
@Table(name="modulo")
@Where(clause = "estado='A'")
@NoArgsConstructor
public class Modulo  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_modulo")
	@Getter @Setter private Integer id;
	
	@Column(name="id_insignia")
	@Getter @Setter private Integer idInsignia;

	@Getter @Setter private String codigo;

	@Getter @Setter private String descripcion;

	@Getter @Setter private String estado;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="fecha_ingreso")
//	private Date fechaIngreso;

	@Getter @Setter private String nombre;

	@Version
	@Getter @Setter private Integer version;

	//RELACIONES
	//bi-directional many-to-one association to Insignia
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_insignia", insertable=false, updatable = false)
	@JsonIgnore
	@Getter @Setter private Insignia insignia;

	//bi-directional many-to-one association to ScoutModulo
	@OneToMany(mappedBy="modulo", cascade=CascadeType.ALL)
	@JsonIgnore
	@Getter @Setter private List<ScoutModulo> scoutModulos;

	@PrePersist
	void preInsert() {
	   if (this.estado == null)
	       this.estado = "A";
	}
}
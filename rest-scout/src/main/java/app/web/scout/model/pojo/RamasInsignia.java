package app.web.scout.model.pojo;

import javax.persistence.*;
import org.hibernate.annotations.Where;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



/**
 * The persistent class for the ramas_insignias database table.
 * 
 */
@Entity
@Table(name="ramas_insignias")
@Where(clause = "estado='A'")
@NoArgsConstructor
public class RamasInsignia  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_ramas_insignias")
	@Getter @Setter private Integer id;
	
	@Column(name="id_rama")
	@Getter @Setter private Integer idRama;
	
	@Column(name="id_insignia")
	@Getter @Setter private Integer idInsignia;

	@Getter @Setter private String estado;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="fecha_ingreso")
//	private Date fechaIngreso;

	@Version
	@Getter @Setter private Integer version;

	//RELACIONES
	//bi-directional many-to-one association to Rama
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_rama", insertable=false, updatable = false)
	@JsonIgnore
	@Getter @Setter private Rama rama;

	//bi-directional many-to-one association to Insignia
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_insignia", insertable=false, updatable = false)
	@JsonIgnore
	@Getter @Setter private Insignia insignia;

	@PrePersist
	void preInsert() {
	   if (this.estado == null)
	       this.estado = "A";
	}

}
package app.web.scout.model.pojo;



import javax.persistence.*;
import org.hibernate.annotations.Where;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


/**
 * The persistent class for the scout_modulos database table.
 * 
 */
@Entity
@Table(name="scout_modulos")
@Where(clause = "estado='A'")
@NoArgsConstructor
public class ScoutModulo  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_scout_modulos")
	@Getter @Setter private Integer id;
	
	@Column(name="id_modulo")
	@Getter @Setter private Integer idModulo;
	
	@Column(name="id_scout")
	@Getter @Setter private Integer idScout;

	@Column(name="id_documento")
	@Getter @Setter private Integer idDocumento;
	
	@Getter @Setter private Boolean aprobado;

	@Getter @Setter private String estado;
	
	@Getter @Setter private String observacion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_entrega")
	@Getter @Setter private Date fechaEntrega;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="fecha_ingreso")
//	private Date fechaIngreso;

	@Version
	@Getter @Setter private Integer version;

	//RELACIONES
	//bi-directional many-to-one association to Scout
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_scout", insertable=false, updatable = false)
	@JsonIgnore
	@Getter @Setter private Scout scout;

	//bi-directional many-to-one association to Modulo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_modulo", insertable=false, updatable = false)
	@JsonIgnore
	@Getter @Setter private Modulo modulo;

	//bi-directional many-to-one association to Documento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_documento", insertable=false, updatable = false)
	@JsonIgnore
	@Getter @Setter private Documento documento;

	@PrePersist
	void preInsert() {
	   if (this.estado == null)
	       this.estado = "A";
	   
	   if (this.fechaEntrega == null)
	       this.fechaEntrega = new Date();
	}
}
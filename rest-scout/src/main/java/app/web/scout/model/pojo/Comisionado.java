package app.web.scout.model.pojo;


import javax.persistence.*;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the comisionado database table.
 * 
 */
@Entity
@Table(name="comisionado")
@Where(clause = "estado='A'")
@NoArgsConstructor
public class Comisionado  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_comisionado")
	@Getter @Setter private Integer id;
	
	@Column(name="id_scout")
	@Getter @Setter private Integer idScout;

	@Column(name="anio_desde")
	@Getter @Setter private String anioDesde;

	@Column(name="anio_hasta")
	@Getter @Setter private String anioHasta;

	@Getter @Setter private String estado;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="fecha_ingreso")
//	@Getter @Setter private Date fechaIngreso;

	@Lob
	@Getter @Setter private String imagen;

	@Version
	@Getter @Setter private Integer version;

	//RELACIONES
	//bi-directional many-to-one association to Scout
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_scout", nullable = false, insertable=false, updatable = false)
	@JsonIgnore
	@Getter @Setter private Scout scout;

	//bi-directional many-to-one association to Distrito
	@OneToMany(mappedBy="comisionado", cascade=CascadeType.ALL)
	@Getter @Setter private List<Distrito> distritos;

	@PrePersist
	void preInsert() {
	   if (this.estado == null)
	       this.estado = "A";
	}
}
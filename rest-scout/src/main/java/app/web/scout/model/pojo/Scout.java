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
 * The persistent class for the scout database table.
 * 
 */
@Entity
@Table(name="scout")
@Where(clause = "estado='A'")
@NoArgsConstructor
public class Scout  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_scout")
	@Getter @Setter private Integer id;
	
	@Column(name="id_tipo_scout")
	@Getter @Setter private Integer idTipoScout;
	
	@Column(name="id_grupo_rama")
	@Getter @Setter private Integer idGrupoRama;

	@Getter @Setter private String apellidos;

	@Getter @Setter private String celular;

	@Getter @Setter private String direccion;

	@Getter @Setter private String estado;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="fecha_ingreso")
//	private Date fechaIngreso;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_nace")
	@Getter @Setter private Date fechaNace;

	@Getter @Setter private String genero;

	@Getter @Setter private String identificacion;

	@Getter @Setter private String nombres;

	@Getter @Setter private String telefono;

	@Version
	@Getter @Setter private Integer version;

	//RELACIONES
	//bi-directional many-to-one association to Comisionado
	@OneToOne(mappedBy="scout", cascade=CascadeType.ALL)
	@Getter @Setter private Comisionado comisionado;

	//bi-directional many-to-one association to DetalleAsistencia
	@OneToMany(mappedBy="scout", cascade=CascadeType.ALL)
	@Getter @Setter private List<DetalleAsistencia> detalleAsistencias;

	//bi-directional many-to-one association to TipoScout
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tipo_scout", insertable=false, updatable = false)
	@JsonIgnore
	@Getter @Setter private TipoScout tipoScout;

	//bi-directional many-to-one association to GrupoRama
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_grupo_rama", insertable=false, updatable = false)
	@JsonIgnore
	@Getter @Setter private GrupoRama grupoRama;

	//bi-directional many-to-one association to ScoutModulo
	@OneToMany(mappedBy="scout", cascade=CascadeType.ALL)
	@Getter @Setter private List<ScoutModulo> scoutModulos;

	//bi-directional many-to-one association to Usuario
	@OneToOne(mappedBy="scout", cascade=CascadeType.ALL)
	@Getter @Setter private Usuario usuario;

	@PrePersist
	void preInsert() {
	   if (this.estado == null)
	       this.estado = "A";
	}
}
package app.web.scout.model.pojo;


import javax.persistence.*;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the grupo_rama database table.
 * 
 */
@Entity
@Table(name="grupo_rama")
@Where(clause = "estado='A'")
@NoArgsConstructor
public class GrupoRama  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_grupo_rama")
	@Getter @Setter private Integer id;
	
	@Column(name="id_grupo")
	@Getter @Setter private Integer idGrupo;
	
	@Column(name="id_rama")
	@Getter @Setter private Integer idRama;

	@Getter @Setter private String estado;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="fecha_ingreso")
//	private Date fechaIngreso;

	@Version
	@Getter @Setter private Integer version;

	//RELACIONES
	//bi-directional many-to-one association to Grupo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_grupo", insertable=false, updatable = false)
	@JsonIgnore
	@Getter @Setter private Grupo grupo;

	//bi-directional many-to-one association to Rama
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_rama", insertable=false, updatable = false)
	@JsonIgnore
	@Getter @Setter private Rama rama;

	//bi-directional many-to-one association to Scout
	@OneToMany(mappedBy="grupoRama", cascade=CascadeType.ALL)
	@Getter @Setter private List<Scout> scouts;

	@PrePersist
	void preInsert() {
	   if (this.estado == null)
	       this.estado = "A";
	}
}
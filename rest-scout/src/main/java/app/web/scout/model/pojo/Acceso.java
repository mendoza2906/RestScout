package app.web.scout.model.pojo;

import javax.persistence.*;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the acceso database table.
 * 
 */
@Entity
@Table(schema="seg", name="acceso")
@Where(clause = "estado='A'")
@NoArgsConstructor
public class Acceso{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_acceso")
	@Getter @Setter private Integer id;
	
	@Column(name="id_perfil")
	@Getter @Setter private Integer idPerfil; 
	
	@Column(name="id_menu")
	@Getter @Setter private Integer idMenu; 

	@Getter @Setter private String estado;

//	@Column(name="fecha_ingreso")
//	private Timestamp fechaIngreso;

	@Version
	@Getter @Setter private Integer version;

	//bi-directional many-to-one association to Menu
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_menu", insertable=false, updatable = false)
	@JsonIgnore
	@Getter @Setter private Menu menu;

	//bi-directional many-to-one association to Perfil
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_perfil", insertable=false, updatable = false)
	@JsonIgnore
	@Getter @Setter private Perfil perfil;

	@PrePersist
	void preInsert() {
	   if (this.estado == null)
	       this.estado = "A";
	}

}
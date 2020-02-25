package app.web.scout.model.pojo;

import javax.persistence.*;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the usuarios database table.
 * 
 */
@Entity
@Table(name="usuarios")
@Where(clause = "estado='A'")
@NoArgsConstructor
public class Usuario {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuario")
	@Getter @Setter private Integer id;
	
	@Column(name="id_scout")
	@Getter @Setter private Integer idScout;
	
	@Column(name="id_perfil")
	@Getter @Setter private Integer idPerfil;

	@Getter @Setter private String clave;

	@Getter @Setter private String estado;

//	@Column(name="fecha_ingreso")
//	private Timestamp fechaIngreso;

	@Getter @Setter private Boolean restablecer;

	@Getter @Setter private String token;
	
    @Transient // solo existe aqui el atributo es transitorios 
    @Getter @Setter	private String claveAuxiliar;
    
    @Transient 
    @Getter @Setter	private Boolean enviaEmail;

	@Getter @Setter private String usuario;
	
	@Version
	@Getter @Setter private Integer version;

	//bi-directional many-to-one association to Perfil
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_perfil", insertable=false, updatable = false)
	@JsonIgnore
	@Getter @Setter private Perfil perfil;
	
	//bi-directional many-to-one association to Scout
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_scout",  nullable = false, insertable=false, updatable = false)
	@JsonIgnore
	@Getter @Setter private Scout scout;
	
	@PrePersist
	void preInsert() {
	   if (this.estado == null)
	       this.estado = "A";
	}
	

}
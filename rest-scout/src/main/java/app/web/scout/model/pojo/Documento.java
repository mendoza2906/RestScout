package app.web.scout.model.pojo;


import javax.persistence.*;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;


/**
 * The persistent class for the documento database table.
 * 
 */
@Entity
@Table(name="documento")
@Where(clause = "estado='A'")
@NoArgsConstructor
public class Documento  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_documento")
	@Getter @Setter private Integer id;
	
	@Getter @Setter private String titulo;

	@Getter @Setter private String descripcion;
	
	@Lob
	@Column(name="ruta_archivo")
	@Getter @Setter private String rutaArchivo;

	@Getter @Setter private String estado;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="fecha_ingreso")
//	@Getter @Setter private Date fechaIngreso;

	@Version
	@Getter @Setter private Integer version;

	//RELACIONES
	//bi-directional many-to-one association to ScoutModulo
	@OneToMany(mappedBy="documento")
	@Getter @Setter private List<ScoutModulo> scoutModulos;

	@PrePersist
	void preInsert() {
	   if (this.estado == null)
	       this.estado = "A";
	}

}
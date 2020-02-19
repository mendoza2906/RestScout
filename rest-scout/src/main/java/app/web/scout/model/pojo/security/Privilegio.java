package app.web.scout.model.pojo.security;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(schema="sch")
@NoArgsConstructor
@Where(clause="estado IS NULL")
@JsonIdentityInfo(generator=JSOGGenerator.class)
public class Privilegio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter @Setter private Integer id;

	@Getter @Setter private String estado;

	@Version
	@Getter @Setter private Integer version;

	@Getter @Setter private String codigo;

	@Getter @Setter private String descripcion;

//	@OneToMany(mappedBy="privilegio")
//	@Where(clause="estado IS NULL")
//	@Getter @Setter private List<ItemCalificacionPrivilegio> itemCalificacionPrivilegios = new ArrayList<ItemCalificacionPrivilegio>();

}
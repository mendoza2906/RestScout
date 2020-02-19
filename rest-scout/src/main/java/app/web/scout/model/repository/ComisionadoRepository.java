package app.web.scout.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.web.scout.model.pojo.Comisionado;

@Repository
public interface ComisionadoRepository extends JpaRepository<Comisionado, Integer>{

//	@Query(value=" select p.id as idPersona,m.id as idMedico,concat(p.nombres,' ',p.apellidos) as nombres "+
//			" from Medico m " + 
//			" inner join Persona p on p.id = m.idPersona " + 
//			" where m.idEspecialidad =(?1)")
//	List<CustObjtListarMedicos> listarMedicos(Integer idEspecialidad);
//	interface CustObjtListarMedicos{
//		Integer getIdPersona();
//		String getIdMedico();
//		String getNombres();
//
//	}
//
//	@Query(value=" select p.id as idPersona, m.id as idMedico," +
//			" p.identificacion as identificacion, concat(p.apellidos,' ',p.nombres) as nombresCompletos," + 
//			" e.descripcion as especialidad,p.direccion as direccion,p.celular as celular from Medico m " + 
//			" inner join Persona p on p.id = m.idPersona " + 
//			" inner join Especialidad e on e.id = m.idEspecialidad " + 
//			" where m.idEspecialidad =(?1)")
//	List<ListPacientes> listadoMedicos(Integer idEspecialidad);
//	interface ListPacientes{ 
//		Integer getIdPersona(); 
//		Integer getIdMedico(); 
//		String getIdentificacion(); 
//		String getNombresCompletos(); 
//		String getEspecialidad(); 
//		String getDireccion();
//		String getCelular();
//	}

}

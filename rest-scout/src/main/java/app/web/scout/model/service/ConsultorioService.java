package app.web.scout.model.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import app.web.scout.model.pojo.Comisionado;
import app.web.scout.model.pojo.Scout;
import app.web.scout.model.pojo.Usuario;
import app.web.scout.model.repository.AsistenciaRepository;
import app.web.scout.model.repository.ActividadRepository;
import app.web.scout.model.repository.ScoutRepository;



@Service
@Transactional
public class ConsultorioService {

	@Autowired private AsistenciaRepository citaRepository;
	@Autowired private ActividadRepository consultaRepository;
	//	@Autowired private CitaRepository ConsultaRepository;
	@Autowired private ScoutRepository scoutRepository;

	@PersistenceContext
	private EntityManager em;

	//refresca la funcion fn_listar_docentes_asignaturas
//	public List<CustObjCitasProc> refreshFnListarCitas(Integer id_estudiante_oferta,String fechaCita) {
//		em.getEntityManagerFactory().getCache().evict(Cita.class);
//		em.getEntityManagerFactory().getCache().evict(Medico.class);
//		em.getEntityManagerFactory().getCache().evict(Paciente.class);
//		em.getEntityManagerFactory().getCache().evict(Hora.class);
//		List<CustObjCitasProc> listarCitasMedicos = citaRepository.fnListarCitas(id_estudiante_oferta, fechaCita);
//		if(listarCitasMedicos != null) {
//			return listarCitasMedicos;
//		}
//
//		return listarCitasMedicos;
//	}

	//Graba las consultas a partir de una cita
//	public void grabarConsultas(Consulta consulta) {
//
//		Long idConsulta=consulta.getId();
//
//		if(idConsulta==null) {
//			Consulta _consulta = new Consulta();			
//			BeanUtils.copyProperties(consulta, _consulta, "detallesConsulta");
//			idConsulta  = consultaRepository.saveAndFlush(_consulta).getId();
//		}
//		for (int i = 0; i < consulta.getDetallesConsulta().size(); i++) {
//			consulta.getDetallesConsulta().get(i).setIdConsulta(idConsulta);
//		}
//
//		Consulta _consultaV1= new Consulta();
//		_consultaV1 = consultaRepository.findById(idConsulta).get();
//		Integer version = _consultaV1.getVersion();
//		consulta.setId(idConsulta);
//		consulta.setVersion(version);
//		consultaRepository.save(consulta);
//
//		em.getEntityManagerFactory().getCache().evict(Consulta.class);
//		em.getEntityManagerFactory().getCache().evict(DetalleConsulta.class);
//
//	}

	//Graba una persona-Medico-Paciente-Usuario
	public void grabarScout(Scout scout) {
		Integer idScout = scout.getId() ;
		if (idScout== null) {    
			Scout _scout = new Scout();
			BeanUtils.copyProperties(scout, _scout,"usuario","comisionado","paciente");
			idScout=scoutRepository.saveAndFlush(_scout).getId();
		}
		if(scout.getComisionado()!=null) {
			scout.getComisionado().setIdScout(idScout);
		}
		if(scout.getUsuario()!=null) {
			scout.getUsuario().setIdScout(idScout);
		}

		Scout _scout = new Scout();
		_scout = scoutRepository.findById(idScout).get();
		Integer version = _scout.getVersion();
		scout.setId(idScout);
		scout.setVersion(version);
		if(scout.getComisionado()!=null) {
			Integer versionM = scout.getComisionado().getVersion();
			scout.getComisionado().setVersion(versionM);
		}else if(scout.getUsuario()!=null) {
			Integer versionU = scout.getUsuario().getVersion();
			scout.getUsuario().setVersion(versionU);
		} 
		System.out.println(scout);
		scoutRepository.save(scout);

		em.getEntityManagerFactory().getCache().evict(Scout.class);
		em.getEntityManagerFactory().getCache().evict(Comisionado.class);
		em.getEntityManagerFactory().getCache().evict(Usuario.class);
	}
}

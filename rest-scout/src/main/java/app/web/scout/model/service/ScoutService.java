package app.web.scout.model.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.web.scout.model.pojo.Asistencia;
import app.web.scout.model.pojo.Comisionado;
import app.web.scout.model.pojo.DetalleAsistencia;
import app.web.scout.model.pojo.Documento;
import app.web.scout.model.pojo.Scout;
import app.web.scout.model.pojo.ScoutModulo;
import app.web.scout.model.pojo.Usuario;
import app.web.scout.model.repository.AsistenciaRepository;
import app.web.scout.model.repository.DocumentoRepository;
import app.web.scout.model.repository.ScoutModuloRepository;
import app.web.scout.model.repository.ActividadRepository;
import app.web.scout.model.repository.ScoutRepository;



@Service
@Transactional
public class ScoutService {

	@Autowired private AsistenciaRepository asistenciaRepository;
	@Autowired private ActividadRepository actividadRepository;
	@Autowired private ScoutModuloRepository scoutModuloRepository;
	@Autowired private DocumentoRepository documentoRepository;
	//	@Autowired private CitaRepository ConsultaRepository;
	@Autowired private ScoutRepository scoutRepository;

	@PersistenceContext
	private EntityManager em;

//	Graba la asistencia y el detalle de la asistencia
	public void grabarAsistencia(Asistencia asistencia) {
		Integer idAsistencia=asistencia.getId();

		if(idAsistencia==null) {
			Asistencia _asistencia = new Asistencia();			
			BeanUtils.copyProperties(asistencia, _asistencia, "detalleAsistencias");
			idAsistencia  = asistenciaRepository.saveAndFlush(_asistencia).getId();
		}
		for (int i = 0; i < asistencia.getDetalleAsistencias().size(); i++) {
			asistencia.getDetalleAsistencias().get(i).setIdAsistencia(idAsistencia);
		}

		Asistencia _asistenciaV1= new Asistencia();
		_asistenciaV1 = asistenciaRepository.findById(idAsistencia).get();
		Integer version = _asistenciaV1.getVersion();
		asistencia.setId(idAsistencia);
		asistencia.setVersion(version);
		asistenciaRepository.save(asistencia);

		em.getEntityManagerFactory().getCache().evict(Asistencia.class);
		em.getEntityManagerFactory().getCache().evict(DetalleAsistencia.class);

	}
	
	//	Graba los documentos de un modulo asociado
	public void grabarDocumentoModulos(Documento documento) {

		Integer idDocumento=documento.getId();

		if(idDocumento==null) {
			Documento _documento = new Documento();			
			BeanUtils.copyProperties(documento, _documento, "scoutModulos");
			idDocumento  = documentoRepository.saveAndFlush(_documento).getId();
			System.out.println(_documento.toString());
		}
		for (int i = 0; i < documento.getScoutModulos().size(); i++) {
//			documento.getScoutModulos().get(i).setIdDocumento(idDocumento);
			documento.getScoutModulos().get(i).setIdDocumento(idDocumento);
			scoutModuloRepository.save(documento.getScoutModulos().get(i));
			System.err.println("si entro esta webada"+ idDocumento);
		}

		Documento _documentoV1= new Documento();
		_documentoV1 = documentoRepository.findById(idDocumento).get();
		Integer version = _documentoV1.getVersion();
		documento.setId(idDocumento);
		documento.setVersion(version);
		documentoRepository.save(documento);
	 	
		em.getEntityManagerFactory().getCache().evict(ScoutModulo.class);
		em.getEntityManagerFactory().getCache().evict(Documento.class);

	}

	//Graba un Scout-Comisionado-Usuario
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
	
	public void borraScout(Integer idScout) {
		Scout scout = scoutRepository.findById(idScout).get();
		if (scout !=null) {
			scout.setEstado("I");
			scout.getUsuario().setEstado("I");
			scoutRepository.save(scout);
		}
	}

	public void borraComisionado(Integer idScout) {
		Scout scout = scoutRepository.findById(idScout).get();
		if (scout !=null) {
			scout.setEstado("I");
			scout.getUsuario().setEstado("I");
			scout.getComisionado().setEstado("I");
			scoutRepository.save(scout);
		}
	}
	
	//Funcion para borrar por completo a estudiante
//	public void borrarEstudiante(Integer idPersona) {
//		Persona _persona = personaRepository.findById(idPersona).get();
//		Persona persona = new Persona();
//		BeanUtils.copyProperties(_persona, persona,"personaDepartamento","miembro","personasCargo","usuario","docente");
//		if (persona !=null) {
//			persona.setEstado("IN");
//			persona.getEstudiante().setEstado("I");
//			for (int i = 0; i < persona.getEstudiante().getEstudianteOfertas().size(); i++) {
//				EstudianteOferta  _estudianteOferta=  persona.getEstudiante().getEstudianteOfertas().get(i);
//				if (_estudianteOferta.getId() != null)
//					_estudianteOferta.setEstado("I");
//			}
//			personaRepository.save(persona);
//		}
//	}
	
	//Funcion para borrar por completo una Movilidad
//	public void borrarMovilidadEstudiante(Integer idMovilidad) {
//		Movilidad movilidad =  movilidadRepository.findById(idMovilidad).get();
//		if (movilidad !=null) {
//			movilidad.setEstado("I");
//			for (int i = 0; i < movilidad.getDetalleMovilidad().size(); i++) {
//				DetalleMovilidad  _detalleMovilidad = movilidad.getDetalleMovilidad().get(i);
//				if (_detalleMovilidad.getId() != null)
//					_detalleMovilidad.setEstado("I");
//			}
//			movilidadRepository.save(movilidad);
//		}
//	}

}

package app.web.scout.model.service;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.web.scout.model.pojo.Scout;
import app.web.scout.model.pojo.Usuario;
import app.web.scout.model.repository.ScoutRepository;
import app.web.scout.model.repository.UsuarioRepository;

@Service
@Transactional
public class PersonaService{
	@Autowired private ScoutRepository personaRepository;
	
	@Autowired private UsuarioRepository usuarioRepository;
	//@Autowired private ActividadesDocenciaRepository actividadesDocenciaRepository;

	
	@PersistenceContext
	private EntityManager em;
	
	public void borraPersona(Integer id) {
		Scout persona = personaRepository.findById(id).get();
		if (persona !=null) {
			persona.setEstado("IN");
			personaRepository.save(persona);
			/* Codigo adicional */
		}
	}
	
	public void borraUsuario(Integer id) {
		Usuario usuario = usuarioRepository.findById(id).get();
		if (usuario !=null) {
			usuario.setEstado("IN");
			usuarioRepository.save(usuario);
			/* Codigo adicional */
		}
	}
	
	
//	public void grabaPersonaUsuario(Persona1 persona) {
//				
//	    if (persona.getId() != null) {
//	      
//			this.editPersonaUsuario(persona);
//		}
//		//caso contrario se trata de un registro nuevo
//		else {
//		 this.newPersonaUsuario(persona);
//		}
//	   
//	   
//	  }
//	
//	public void editPersonaUsuario(Persona persona) {
//		//se intancia objeto auxiliar de personas
//		Persona _persona = new Persona();
//		//se instancia un objeto de BCryptPasswordEncoder para funciones de encriptado
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		//si id de persona es diferente de null actualiza registro;
//		_persona = personaRepository.findById(persona.getId()).get();
//		if (_persona.getUsuario()!=null) {
//			//persona.getUsuario().setPersona_id(persona.getId());	
//		if (persona.getUsuario().getClaveAuxiliar()!=null) {
//			//para actualizar la clave en el caso de que este en null o sea diferente de la almacenada
//			if ((_persona.getUsuario().getClave() == null)||(!encoder.matches(persona.getUsuario().getClaveAuxiliar(), _persona.getUsuario().getClave()))) {
//				//encripta la clave
//				persona.getUsuario().setClave(encoder.encode(persona.getUsuario().getClaveAuxiliar()));
//			}
//		 }else {
//			 /*si es que la clave recibida del usuario viene en null del usuario entonces se mantiene 
//			   la vigente en el repositorio*/
//			 persona.getUsuario().setClave(_persona.getUsuario().getClave());
//		 }
//		 BeanUtils.copyProperties(persona, _persona);
//		
//		}
//		
//		else {
//			if (persona.getUsuario().getClaveAuxiliar()!=null) {
//				  persona.getUsuario().setClave(encoder.encode(persona.getUsuario().getClaveAuxiliar()));
//			 	 }
//			
//			BeanUtils.copyProperties(persona, _persona,"usuario");  
//			//personaRepository.save(_persona);
//			persona.getUsuario().setPersona_id(persona.getId());
//			Usuario _usuario = new Usuario();
//			//_usuario.setPersona_id(persona.getId());
//			BeanUtils.copyProperties(persona.getUsuario(), _usuario, "moduloRolUsuario");
//			Integer usuario_id=usuarioRepository.saveAndFlush(_usuario).getId();
//			 //em.getEntityManagerFactory().getCache().evict(Usuario.class);
//			//iteracion para sincronizar relacion uno a varios con moduloRolUsuario
//			for (int i = 0; i < persona.getUsuario().getModuloRolUsuario().size(); i++) {
//				Integer modulo_rol_id = persona.getUsuario().getModuloRolUsuario().get(i).getModulo_rol_id();
//				ModuloRolUsuario _moduloRolUsuario= new ModuloRolUsuario();
//				_moduloRolUsuario.setUsuario_id(usuario_id);
//				_moduloRolUsuario.setModulo_rol_id(modulo_rol_id);
//				moduloRolUsuarioRepository.saveAndFlush(_moduloRolUsuario);
//			}
//			em.refresh(_usuario);
//						
//		}
//		personaRepository.saveAndFlush(_persona);
//		em.getEntityManagerFactory().getCache().evict(Persona1.class);
//		
//	}
//	 
//	public void newPersonaUsuario(Persona1 persona) {
//		//se instancia un objeto de BCryptPasswordEncoder para funciones de encriptado
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		Persona1 _persona = new Persona1();
//		//encripto la clave
//		if (persona.getUsuario()!=null) {
//			 if (persona.getUsuario().getClaveAuxiliar()!=null) {
//			  persona.getUsuario().setClave(encoder.encode(persona.getUsuario().getClaveAuxiliar()));
//		 	 }
//	        }
//		//copia propiedades de persona en objeto auxuliar _persona excluye clase usuario para grabado de cabecera inicial
//		BeanUtils.copyProperties(persona, _persona,"usuario","personaDepartamento");
//		//afecto persona y obtengo id
//		Integer persona_id=personaRepository.saveAndFlush(_persona).getId();
//		persona.getUsuario().setPersona_id(persona_id);
//		Usuario _usuario = new Usuario();
//		BeanUtils.copyProperties(persona.getUsuario(), _usuario, "moduloRolUsuario");
//		Integer usuario_id=usuarioRepository.saveAndFlush(_usuario).getId();
//		//iteracion para sincronizar relacion una a varios con personaDepartamento
//		
//		for (int i = 0; i < persona.getPersonaDepartamento().size(); i++) {
//			Integer departamento_id=persona.getPersonaDepartamento().get(i).getDepartamento_id();
//			PersonaDepartamento _personaDepartamento=new PersonaDepartamento();
//			_personaDepartamento.setPersona_id(persona_id);
//			_personaDepartamento.setDepartamento_id(departamento_id);
//			personaDepartamentoReposirory.saveAndFlush(_personaDepartamento);
//		}
//		
//		//iteracion para sincronizar relacion uno a varios con moduloRolUsuario
//		for (int i = 0; i < persona.getUsuario().getModuloRolUsuario().size(); i++) {
//			Integer modulo_rol_id = persona.getUsuario().getModuloRolUsuario().get(i).getModulo_rol_id();
//			ModuloRolUsuario _moduloRolUsuario= new ModuloRolUsuario();
//			_moduloRolUsuario.setUsuario_id(usuario_id);
//			_moduloRolUsuario.setModulo_rol_id(modulo_rol_id);
//			moduloRolUsuarioRepository.saveAndFlush(_moduloRolUsuario);
//		}
//		em.refresh(_persona);	
//	}
//		
//	
//	
}
	
	
	    

	

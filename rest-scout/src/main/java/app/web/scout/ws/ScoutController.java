package app.web.scout.ws;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.web.scout.model.pojo.Usuario;
import app.web.scout.model.repository.ComisionadoRepository;
import app.web.scout.model.repository.PerfilRepository;
import app.web.scout.model.repository.ScoutRepository;
import app.web.scout.model.repository.UsuarioRepository;
import app.web.scout.model.service.ConsultorioService;
import app.web.scout.model.service.ReportService;
import app.web.scout.model.service.SecurityService;
import lombok.Getter;


@RestController
@RequestMapping("/api/personas")
@CrossOrigin
public class ScoutController {

	@Autowired private ScoutRepository personaRepository;
	@Autowired private UsuarioRepository usuarioRepository;
	@Autowired private ComisionadoRepository medicoRepository;
//	@Autowired private PacienteRepository pacienteRepository;
	@Autowired private PerfilRepository perfilRepository;
	@Autowired private SecurityService securityService;
	@Autowired private ConsultorioService consultorioService;
	@Autowired private ReportService reportService;

	//****************** SERVICIOS PARA PACIENTES************************//

//	//lista los pacientes
//	@RequestMapping(value = "/listadoPacientes", method = RequestMethod.GET)
//	public ResponseEntity<?> listadoPacientes(@RequestHeader(value = "Authorization") String Authorization ){
//		if (!securityService.isTokenValido(Authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		return ResponseEntity.ok(pacienteRepository.listadoPacientes());
//	}
//
//	//lista los pacientes
//	@RequestMapping(value = "/listarPerfiles", method = RequestMethod.GET)
//	public ResponseEntity<?> listarPerfiles(@RequestHeader(value = "Authorization") String Authorization ){
//		if (!securityService.isTokenValido(Authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		return ResponseEntity.ok(perfilRepository.listarPerfiles());
//	}
//
//	//recupera el ultimo numero de paciente 
//	@RequestMapping(value="/recuperarUltimoPaciente", method=RequestMethod.GET)
//	public ResponseEntity<?> recuperarUltimoPaciente(@RequestHeader(value="Authorization") String authorization) {
//		if (!securityService.isTokenValido(authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		return ResponseEntity.ok(pacienteRepository.ultimoPaciente());	
//	}
//
//	//recupera persona por cedula
//	@RequestMapping(value = "/recuperarPorCedula/{identificacion}", method = RequestMethod.GET)
//	public ResponseEntity<?> recuperarPorCedula(@RequestHeader(value = "Authorization") String Authorization,
//			@PathVariable("identificacion") String identificacion) {
//		if (!securityService.isTokenValido(Authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		Persona _persona;
//		_persona = personaRepository.findByIdentificacion(identificacion);
//		if (_persona!=null) {
//			if(_persona.getMedico()!=null){
//				Medico medico = new Medico();
//				BeanUtils.copyProperties(_persona.getMedico(), medico, "citas");
//				_persona.setMedico(medico);
//			}
//			if(_persona.getPaciente()!=null){
//				Paciente paciente = new Paciente();
//				BeanUtils.copyProperties(_persona.getPaciente(),paciente, "citas");
//				_persona.setPaciente(paciente);
//			}
//			return ResponseEntity.ok(_persona);
//		} else {
//			return ResponseEntity.ok().build();
//		}
//	}
//
//	//Graba una persona-Medico-Paciente-Usuario
//	@RequestMapping(value = "/grabarPersona", method = RequestMethod.POST)
//	public ResponseEntity<?> grabarPersona(@RequestHeader(value = "Authorization") String Authorization, 	
//			@RequestBody Persona persona) {
//		if (!securityService.isTokenValido(Authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		consultorioService.grabarPersona(persona);
//		return ResponseEntity.ok().build();
//	}
//	//****************** SERVICIOS PARA PACIENTES************************//
//
//
//	//****************** SERVICIOS PARA MEDICOS************************//
//	//lista medicos
//	@RequestMapping(value = "/listadoMedicos/{idEspecialidad}", method = RequestMethod.GET)
//	public ResponseEntity<?> listadoMedicos(@RequestHeader(value = "Authorization") String Authorization,
//			@PathVariable("idEspecialidad") Integer idEspecialidad) {
//		if (!securityService.isTokenValido(Authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		return ResponseEntity.ok(medicoRepository.listadoMedicos(idEspecialidad));
//
//	}
//
//	// busca un usuario por nombre usuario
//	@RequestMapping(value = "/buscarUsuario/{usuario}", method = RequestMethod.GET)
//	public ResponseEntity<?> buscarUsuario(@RequestHeader(value = "Authorization") String authorization,
//			@PathVariable("usuario") String usuario) {
//		if (!securityService.isTokenValido(authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		Usuario _usuario;
//		_usuario = usuarioRepository.findByUsuario(usuario);
//		if (_usuario!=null) {
//			return ResponseEntity.ok(_usuario);
//		} else {
//			return ResponseEntity.ok().build();
//		}
//	}
//
//	private static final String PATH_REPORT = File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"rep";
//
//	//reporte agenda
//	@RequestMapping(value="/getAgendaMedico/{idMedico}/{pi_fecha_inicio}/{pi_fecha_fin}", method=RequestMethod.GET)
//	public ResponseEntity<?> getAgendaMedico(@RequestHeader(value="Authorization") String authorization,
//
//			@PathVariable("idMedico") Integer idMedico, 
//			@PathVariable("pi_fecha_inicio") String pi_fecha_inicio, 
//			@PathVariable("pi_fecha_fin") String pi_fecha_fin,
//			HttpServletRequest request) throws Exception {
//
//		if (!securityService.isTokenValido(authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		Map<String, Object> parametros = new HashMap<String, Object>();
//		parametros.put("pi_id_medico", idMedico);
//		parametros.put("pi_fecha_inicio", pi_fecha_inicio);
//		parametros.put("pi_fecha_fin", pi_fecha_fin);
//
//		Resource resource = reportService.generaReporteParametros("rpt_agenda_medico.jasper",parametros);
//
//		// Determina el contenido
//		String contentType = null;
//		try {
//			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
//
//		// Si no se determina el tipo, asjjume uno por defecto.
//		if(contentType == null) {
//			contentType = "application/octet-stream";
//		}
//
//		return ResponseEntity.ok()
//				.contentType(MediaType.parseMediaType(contentType))
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//				.body(resource);
//	}
//
//	//reporte historial
//	@RequestMapping(value="/getHistorialPaciente/{pi_id_paciente}", method=RequestMethod.GET)
//	public ResponseEntity<?> getHistorialPaciente(@RequestHeader(value="Authorization") String authorization,
//
//			@PathVariable("pi_id_paciente") Integer pi_id_paciente, 
//			HttpServletRequest request) throws Exception {
//
//		if (!securityService.isTokenValido(authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		Map<String, Object> parametros = new HashMap<String, Object>();
//		parametros.put("pi_id_paciente", pi_id_paciente);
//		Resource resource = reportService.generaReporteParametros("rpt_historial_medico.jasper",parametros);
//
//		// Determina el contenido
//		String contentType = null;
//		try {
//			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
//
//		// Si no se determina el tipo, asjjume uno por defecto.
//		if(contentType == null) {
//			contentType = "application/octet-stream";
//		}
//
//		return ResponseEntity.ok()
//				.contentType(MediaType.parseMediaType(contentType))
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//				.body(resource);
//	}
//
//	//****************** SERVICIOS PARA MEDICOS************************//
}

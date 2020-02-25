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

import app.web.scout.model.pojo.Asistencia;
import app.web.scout.model.pojo.Comisionado;
import app.web.scout.model.pojo.Documento;
import app.web.scout.model.pojo.Scout;
import app.web.scout.model.pojo.Usuario;
import app.web.scout.model.repository.ActividadRepository;
import app.web.scout.model.repository.AsistenciaRepository;
import app.web.scout.model.repository.ComisionadoRepository;
import app.web.scout.model.repository.GrupoRepository;
import app.web.scout.model.repository.PerfilRepository;
import app.web.scout.model.repository.ScoutRepository;
import app.web.scout.model.repository.TipoScoutRepository;
import app.web.scout.model.repository.UsuarioRepository;
import app.web.scout.model.service.ScoutService;
import app.web.scout.model.service.ReportService;
import app.web.scout.model.service.SecurityService;
import lombok.Getter;


@RestController
@RequestMapping("/api/personas")
@CrossOrigin
public class ScoutController {

	@Autowired private ScoutRepository scoutRepository;
	@Autowired private UsuarioRepository usuarioRepository;
	@Autowired private GrupoRepository grupoRepository;
	@Autowired private ComisionadoRepository comisionadoRepository;
	@Autowired private TipoScoutRepository tipoScoutRepository;
	@Autowired private PerfilRepository perfilRepository;
	@Autowired private SecurityService securityService;
	@Autowired private ActividadRepository actividadRepository;
	@Autowired private AsistenciaRepository asistenciaRepository;
	@Autowired private ScoutService scoutService;
	@Autowired private ReportService reportService;

	//****************** SERVICIOS PARA PACIENTES************************//

	
	//lista tipos de scouts
	@RequestMapping(value = "/listarTiposScouts", method = RequestMethod.GET)
	public ResponseEntity<?> listarTiposScouts(@RequestHeader(value = "Authorization") String Authorization) {
		if (!securityService.isTokenValido(Authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(tipoScoutRepository.listarTiposScouts());
	}
	
	//lista scouts
	@RequestMapping(value = "/listadoScouts", method = RequestMethod.GET)
	public ResponseEntity<?> listadoScouts(@RequestHeader(value = "Authorization") String Authorization) {
		if (!securityService.isTokenValido(Authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(scoutRepository.listadoScouts());
	}
	
	//lista scouts
	@RequestMapping(value = "/listadoComisionados", method = RequestMethod.GET)
	public ResponseEntity<?> listadoComisionados(@RequestHeader(value = "Authorization") String Authorization) {
		if (!securityService.isTokenValido(Authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(comisionadoRepository.listadoComisionados());
	}


	// servicio que devuelve un scout
	@RequestMapping(value = "/recuperarScoutId/{idScout}", method = RequestMethod.GET)
	public ResponseEntity<?> recuperarScoutId(@RequestHeader(value = "Authorization") String authorization,
			@PathVariable("idScout") Integer idScout) {
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Scout _scout;
		if (scoutRepository.findById(idScout).isPresent()) {
			_scout = scoutRepository.findById(idScout).get();
			return ResponseEntity.ok(_scout);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	//recupera id combos
	@RequestMapping(value = "/recuperarCombosGrupoRama/{idGrupoRama}", method = RequestMethod.GET)
	public ResponseEntity<?> recuperarCombosGrupoRama(@RequestHeader(value = "Authorization") String authorization,
			@PathVariable("idGrupoRama") Integer idGrupoRama) {
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(grupoRepository.recuperarCombosGrupoRama(idGrupoRama));
	}
	
	//lista los pacientes
	@RequestMapping(value = "/listarPerfiles", method = RequestMethod.GET)
	public ResponseEntity<?> listarPerfiles(@RequestHeader(value = "Authorization") String Authorization ){
		if (!securityService.isTokenValido(Authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(perfilRepository.listarPerfiles());
	}
	
	//recupera persona por cedula
	@RequestMapping(value = "/recuperarPorCedula/{identificacion}", method = RequestMethod.GET)
	public ResponseEntity<?> recuperarPorCedula(@RequestHeader(value = "Authorization") String Authorization,
			@PathVariable("identificacion") String identificacion) {
		if (!securityService.isTokenValido(Authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Scout _persona;
		_persona = scoutRepository.findByIdentificacion(identificacion);
		if (_persona!=null) {
			return ResponseEntity.ok(_persona);
		} else {
			return ResponseEntity.ok().build();
		}
	}

	// busca un usuario por nombre usuario
	@RequestMapping(value = "/buscarUsuario/{usuario}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarUsuario(@RequestHeader(value = "Authorization") String authorization,
			@PathVariable("usuario") String usuario) {
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Usuario _usuario;
		_usuario = usuarioRepository.findByUsuario(usuario);
		if (_usuario!=null) {
			return ResponseEntity.ok(_usuario);
		} else {
			return ResponseEntity.ok().build();
		}
	}
	
	// servicio para grabar Scouts 
	@RequestMapping(value = "/grabarScout", method = RequestMethod.POST)
	public ResponseEntity<?> grabarScout(@RequestHeader(value = "Authorization") String Authorization,
			@RequestBody Scout scout) {
		if (!securityService.isTokenValido(Authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		scoutService.grabarScout(scout);
		return ResponseEntity.ok().build();
	}

//***************************PARA PANTALLA ASISTENCIA**************************************/////
	
	// servicio que devuelve el objeto asistencia
	@RequestMapping(value = "/recuperarAsistenciaId/{idAsistencia}", method = RequestMethod.GET)
	public ResponseEntity<?> listarConsultaId(@RequestHeader(value = "Authorization") String authorization,
			@PathVariable("idAsistencia") Integer idAsistencia) {
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Asistencia _asistencia;
		if (asistenciaRepository.findById(idAsistencia).isPresent()) {
			_asistencia = asistenciaRepository.findById(idAsistencia).get();
			return ResponseEntity.ok(_asistencia);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	//lista las actividades
	@RequestMapping(value = "/listarActividades", method = RequestMethod.GET)
	public ResponseEntity<?> listarActividades(@RequestHeader(value = "Authorization") String authorization) {
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(actividadRepository.listarActividades());
	}
	
	//lista las asistencias
	@RequestMapping(value = "/listarAsistencias", method = RequestMethod.GET)
	public ResponseEntity<?> listarAsistencias(@RequestHeader(value = "Authorization") String authorization) {
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(actividadRepository.listarAsistencias());
	}
	
	//recupera combos scout
	@RequestMapping(value = "/recuperarCombosScouts/{idScout}", method = RequestMethod.GET)
	public ResponseEntity<?> recuperarCombosScouts(@RequestHeader(value = "Authorization") String authorization,
			@PathVariable("idScout") Integer idScout) {
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(grupoRepository.recuperarCombosScouts(idScout));
	}
	
	// servicio para grabar Scouts 
	@RequestMapping(value = "/grabarAsistencia", method = RequestMethod.POST)
	public ResponseEntity<?> grabarAsistencia(@RequestHeader(value = "Authorization") String Authorization,
			@RequestBody Asistencia asistencia) {
		if (!securityService.isTokenValido(Authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		scoutService.grabarAsistencia(asistencia);
		return ResponseEntity.ok().build();
	}
	
	
//***************************PARA PANTALLA ASISTENCIA**************************************/////
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

package app.web.scout.ws;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.noneDSA;
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
import app.web.scout.model.pojo.Noticia;
import app.web.scout.model.pojo.Scout;
import app.web.scout.model.pojo.Usuario;
import app.web.scout.model.repository.ActividadRepository;
import app.web.scout.model.repository.AsistenciaRepository;
import app.web.scout.model.repository.ComisionadoRepository;
import app.web.scout.model.repository.GrupoRepository;
import app.web.scout.model.repository.InsigniaRepository;
import app.web.scout.model.repository.NoticiaRepository;
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
	@Autowired private InsigniaRepository insigniaRepository;
	@Autowired private PerfilRepository perfilRepository;
	@Autowired private SecurityService securityService;
	@Autowired private ActividadRepository actividadRepository;
	@Autowired private AsistenciaRepository asistenciaRepository;
	@Autowired private NoticiaRepository noticiaRepository;
	@Autowired private ScoutService scoutService;
	@Autowired private ReportService reportService;
	@Getter private String imagen="fotoMenu.png";
	@Getter private String imagenLogo="florLis.png";
	@Getter private String rutaImagen="";
	@Getter private String rutaImagenLogo="";
	private static final String PATH_IMAGEN = "/src/main/resources/img";

	//****************** SERVICIOS PARA PACIENTES************************//


	//lista tipos de scouts
	@RequestMapping(value = "/listarTiposScouts", method = RequestMethod.GET)
	public ResponseEntity<?> listarTiposScouts(@RequestHeader(value = "Authorization") String Authorization) {
		if (!securityService.isTokenValido(Authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(tipoScoutRepository.listarTiposScouts());
	}
	
	//listas insignias
	@RequestMapping(value = "/listarInsignias", method = RequestMethod.GET)
	public ResponseEntity<?> listarInsignias(@RequestHeader(value = "Authorization") String Authorization) {
		if (!securityService.isTokenValido(Authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(insigniaRepository.listarInsignias());
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

	//***************************PARA NOTICIAS**************************************/////	
	// servicio que devuelve el objeto noticias
	@RequestMapping(value = "/recuperarNoticiaId/{idNoticia}", method = RequestMethod.GET)
	public ResponseEntity<?> recuperarNoticiaId(@RequestHeader(value = "Authorization") String authorization,
			@PathVariable("idNoticia") Integer idNoticia) {
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Noticia _noticia;
		if (noticiaRepository.findById(idNoticia).isPresent()) {
			_noticia = noticiaRepository.findById(idNoticia).get();
			return ResponseEntity.ok(_noticia);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	// servicio para grabar Documento
	@RequestMapping(value="/grabarNoticia", method=RequestMethod.POST)
	public ResponseEntity<?> grabarNoticia(@RequestHeader(value="Authorization") String Authorization, 
			@RequestBody Noticia noticia) {
		if (!securityService.isTokenValido(Authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Noticia _noticia = new Noticia();
		if (noticia.getId() != null) {
			_noticia = noticiaRepository.findById(noticia.getId()).get();
		}
		BeanUtils.copyProperties(noticia, _noticia);
		noticiaRepository.save(_noticia);
		return ResponseEntity.ok(_noticia);
	}

	//lista las noticias
	@RequestMapping(value = "/listadoNoticias", method = RequestMethod.GET)
	public ResponseEntity<?> listadoNoticias(@RequestHeader(value = "Authorization") String authorization) {
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(noticiaRepository.listadoNoticias());
	}
	//***************************PARA REPORTES**************************************/////7


	//***************************PARA REPORTES**************************************/////
	
	@RequestMapping(value="/getRankings", method=RequestMethod.GET)
	public ResponseEntity<?> getRankings(@RequestHeader(value="Authorization") String authorization,
//			@PathVariable("pi_id_insignia") Integer pi_id_insignia, 
			HttpServletRequest request) throws Exception {

		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Map<String, Object> parametros = new HashMap<String, Object>();
//		parametros.put("pi_id_insignia", pi_id_insignia);

		// Carga el archivo
		File file =new File(imagen);
		String ruta =file.getAbsoluteFile().getParent();
		rutaImagen  = ruta+PATH_IMAGEN+File.separator+imagen; 
		parametros.put("rutaImagen", rutaImagen);

		File fileLogo =new File(imagenLogo);
		String rutaLogo =fileLogo.getAbsoluteFile().getParent();
		rutaImagenLogo  = rutaLogo+PATH_IMAGEN+File.separator+imagenLogo; 
		parametros.put("rutaLogo", rutaImagenLogo);


		// Determina el contenido
		Resource resource = reportService.generaReporteParametros("rpt_ranking_insignias.jasper",parametros);

		// Determina el contenido
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// Si no se determina el tipo, asjjume uno por defecto.
		if(contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@RequestMapping(value="/getListadoAsistencia/{pi_id_asistencia}", method=RequestMethod.GET)
	public ResponseEntity<?> getListadoAsistencia(@RequestHeader(value="Authorization") String authorization,
			@PathVariable("pi_id_asistencia") Integer pi_id_asistencia, 
			HttpServletRequest request) throws Exception {

		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("pi_id_asistencia", pi_id_asistencia);

		// Carga el archivo
		File file =new File(imagen);
		String ruta =file.getAbsoluteFile().getParent();
		rutaImagen  = ruta+PATH_IMAGEN+File.separator+imagen; 
		parametros.put("rutaImagen", rutaImagen);

		File fileLogo =new File(imagenLogo);
		String rutaLogo =fileLogo.getAbsoluteFile().getParent();
		rutaImagenLogo  = rutaLogo+PATH_IMAGEN+File.separator+imagenLogo; 
		parametros.put("rutaLogo", rutaImagenLogo);


		// Determina el contenido
		Resource resource = reportService.generaReporteParametros("rpt_asistencia.jasper",parametros);

		// Determina el contenido
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// Si no se determina el tipo, asjjume uno por defecto.
		if(contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@RequestMapping(value="/getListadoProyectos/{pi_id_insignia}", method=RequestMethod.GET)
	public ResponseEntity<?> getListadoProyectos(@RequestHeader(value="Authorization") String authorization,
			@PathVariable("pi_id_insignia") Integer pi_id_insignia, 
			HttpServletRequest request) throws Exception {

		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("pi_id_insignia", pi_id_insignia);

		// Carga el archivo
		File file =new File(imagen);
		String ruta =file.getAbsoluteFile().getParent();
		rutaImagen  = ruta+PATH_IMAGEN+File.separator+imagen; 
		parametros.put("rutaImagen", rutaImagen);

		File fileLogo =new File(imagenLogo);
		String rutaLogo =fileLogo.getAbsoluteFile().getParent();
		rutaImagenLogo  = rutaLogo+PATH_IMAGEN+File.separator+imagenLogo; 
		parametros.put("rutaLogo", rutaImagenLogo);


		// Determina el contenido
		Resource resource = reportService.generaReporteParametros("rpt_lista_actividades.jasper",parametros);

		// Determina el contenido
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// Si no se determina el tipo, asjjume uno por defecto.
		if(contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	//	//****************** SERVICIOS PARA MEDICOS************************//
}

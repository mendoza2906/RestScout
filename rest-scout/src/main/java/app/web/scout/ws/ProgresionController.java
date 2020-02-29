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


import app.web.scout.model.repository.AsistenciaRepository;
import app.web.scout.model.pojo.Documento;
import app.web.scout.model.pojo.Scout;
import app.web.scout.model.pojo.ScoutModulo;
import app.web.scout.model.repository.ActividadRepository;
import app.web.scout.model.repository.RamaRepository;
import app.web.scout.model.repository.ScoutModuloRepository;
import app.web.scout.model.repository.ComisionadoRepository;
import app.web.scout.model.repository.DocumentoRepository;
import app.web.scout.model.repository.GrupoRepository;
import app.web.scout.model.repository.ScoutRepository;
import app.web.scout.model.repository.TipoScoutRepository;
import app.web.scout.model.service.ReportService;
import app.web.scout.model.service.ScoutService;
import app.web.scout.model.service.SecurityService;
import lombok.Getter;


@RestController
@RequestMapping("/api/scout")
@CrossOrigin
public class ProgresionController {

	@Autowired private ScoutRepository scoutRepository;
	@Autowired private TipoScoutRepository tipoScoutRepository;
	@Autowired private RamaRepository ramaRepository;
	@Autowired private GrupoRepository grupoRepository;
	@Autowired private DocumentoRepository documentoRepository;
	@Autowired private ScoutModuloRepository scoutModuloRepository;
	//	@Autowired private EspecialidadRepository especialidadRepository;
	@Autowired private ScoutService scoutService;
	@Autowired private SecurityService securityService;
	
//	@Autowired private ReportService reportService;
//
//	@Getter private String imagen="fotoMenu.png";
//	@Getter private String imagenLogo="florLis.png";
//	@Getter private String rutaImagen="";
//	@Getter private String rutaImagenLogo="";
//	private static final String PATH_IMAGEN = "/src/main/resources/img";


	//****************** SERVICIOS PARA CITAS************************//
	//recupera datos delusuario que se logea
	@RequestMapping(value = "/recuperarDatosScout/{usuario}", method = RequestMethod.GET)
	public ResponseEntity<?> recuperarDatosScout(@RequestHeader(value = "Authorization") String authorization,
			@PathVariable("usuario") String usuario) {
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(scoutRepository.recuperarDatosScout(usuario));

	}
	
	//Buscar todos las personas para listar en angular
	@RequestMapping(value="/buscarTiposScouts", method=RequestMethod.GET)
	public ResponseEntity<?> buscarTiposScouts(@RequestHeader(value="Authorization") String Authorization) {
		if (!securityService.isTokenValido(Authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(tipoScoutRepository.findAll());
	}

	//lista las ramas
	@RequestMapping(value = "/listarRamas", method = RequestMethod.GET)
	public ResponseEntity<?> listarRamas(@RequestHeader(value = "Authorization") String Authorization ){
		if (!securityService.isTokenValido(Authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(ramaRepository.listarRamas());
	}

	//lista los grupos
	@RequestMapping(value = "/listarGrupos/{idRama}", method = RequestMethod.GET)
	public ResponseEntity<?> listarGrupos(@RequestHeader(value = "Authorization") String authorization,
			@PathVariable("idRama") Integer idRama) {
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(grupoRepository.listarGrupos(idRama));
	}

	//lista los scouts por ramas
	@RequestMapping(value = "/listarScouts/{idGrupoRama}", method = RequestMethod.GET)
	public ResponseEntity<?> listarScouts(@RequestHeader(value = "Authorization") String authorization,
			@PathVariable("idGrupoRama") Integer idGrupoRama) {
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(scoutRepository.listarScouts(idGrupoRama));

	}

	//recupera el scout con su ultima insignia
	@RequestMapping(value = "/recuperarUltimaInsigniaScout/{idScout}", method = RequestMethod.GET)
	public ResponseEntity<?> recuperarUltimaInsigniaScout(@RequestHeader(value = "Authorization") String authorization,
			@PathVariable("idScout") Integer idScout) {
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(scoutRepository.recuperarUltimaInsigniaScout(idScout));

	}

	//recupera insignias obtenidas
	@RequestMapping(value = "/recuperarInsigniasObtenidas/{idScout}", method = RequestMethod.GET)
	public ResponseEntity<?> recuperarInsigniasObtenidas(@RequestHeader(value = "Authorization") String authorization,
			@PathVariable("idScout") Integer idScout) {
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(scoutRepository.recuperarInsigniasObtenidas(idScout));

	}
	
	//recupera detalle insignias obtenidas
	@RequestMapping(value = "/recuperarDetalleInsignias/{idScout}", method = RequestMethod.GET)
	public ResponseEntity<?> recuperarDetalleInsignias(@RequestHeader(value = "Authorization") String authorization,
			@PathVariable("idScout") Integer idScout) {
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(scoutRepository.recuperarDetalleInsignias(idScout));

	}
	
	//recupera el modulo que le toca subir 
	@RequestMapping(value = "/recuperarUltimaModuloInsignia/{idScout}", method = RequestMethod.GET)
	public ResponseEntity<?> recuperarUltimaModuloInsignia(@RequestHeader(value = "Authorization") String authorization,
			@PathVariable("idScout") Integer idScout) {
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(scoutRepository.recuperarUltimaModuloInsignia(idScout));

	}

	// servicio para grabar Documento
	@RequestMapping(value="/grabarDocumento", method=RequestMethod.POST)
	public ResponseEntity<?> grabarDocumento(@RequestHeader(value="Authorization") String Authorization, 
			@RequestBody Documento documento) {
		if (!securityService.isTokenValido(Authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Documento _documento = new Documento();
		if (documento.getId() != null) {
			_documento = documentoRepository.findById(documento.getId()).get();
		}
		BeanUtils.copyProperties(documento, _documento);
		documentoRepository.save(_documento);
		return ResponseEntity.ok(_documento);
	}

	// servicio que devuelve el objeto documento
	@RequestMapping(value = "/recuperarDocumentoId/{idDocumento}", method = RequestMethod.GET)
	public ResponseEntity<?> listarConsultaId(@RequestHeader(value = "Authorization") String authorization,
			@PathVariable("idDocumento") Integer idDocumento) {
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Documento _consulta;
		if (documentoRepository.findById(idDocumento).isPresent()) {
			_consulta = documentoRepository.findById(idDocumento).get();
			return ResponseEntity.ok(_consulta);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	//recuper el modulo de un scout
	@RequestMapping(value = "/buscarPorScoutModulo/{idModulo}/{idScout}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarPorScoutModulo(@RequestHeader(value = "Authorization") String authorization,
			@PathVariable("idModulo") Integer idModulo, @PathVariable("idScout") Integer idScout) {
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(scoutModuloRepository.findByIdModuloAndIdScout(idModulo, idScout));

	}
	
	// servicio para grabar ScoutModulos
	@RequestMapping(value="/grabarScoutsModulos", method=RequestMethod.POST)
	public ResponseEntity<?> grabarScoutsModulos(@RequestHeader(value="Authorization") String Authorization, 
			@RequestBody ScoutModulo scoutModulo) {
		if (!securityService.isTokenValido(Authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		ScoutModulo _scoutModulo = new ScoutModulo();
		if (scoutModulo.getId() != null) {
			_scoutModulo = scoutModuloRepository.findById(scoutModulo.getId()).get();
		}
		BeanUtils.copyProperties(scoutModulo, _scoutModulo);
		scoutModuloRepository.save(_scoutModulo);
		return ResponseEntity.ok(_scoutModulo);
	}
	
	
	// servicio para grabar Documento Modulo
	@RequestMapping(value = "/grabarDocumentoModulos", method = RequestMethod.POST)
	public ResponseEntity<?> grabarDocumentoModulos(@RequestHeader(value = "Authorization") String Authorization,
			@RequestBody Documento documento) {
		if (!securityService.isTokenValido(Authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		scoutService.grabarDocumentoModulos(documento);
		return ResponseEntity.ok().build();
	}
	
	
}

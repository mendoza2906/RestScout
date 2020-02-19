package app.web.scout.ws;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import app.web.scout.model.repository.ActividadRepository;
import app.web.scout.model.repository.RamaRepository;
import app.web.scout.model.repository.ComisionadoRepository;
import app.web.scout.model.repository.DocumentoRepository;
import app.web.scout.model.repository.ScoutRepository;
import app.web.scout.model.repository.TipoScoutRepository;
import app.web.scout.model.service.ConsultorioService;
import app.web.scout.model.service.SecurityService;


@RestController
@RequestMapping("/api/scout")
@CrossOrigin
public class ConsultorioController {

	@Autowired private ScoutRepository personaRepository;
	@Autowired private ActividadRepository consultaRepository;
	@Autowired private TipoScoutRepository tipoScoutRepository;
	@Autowired private RamaRepository horaRepository;
	@Autowired private DocumentoRepository documentoRepository;
//	@Autowired private PacienteRepository pacienteRepository;
	@Autowired private ComisionadoRepository medicoRepository;
	@Autowired private AsistenciaRepository citaRepository;
//	@Autowired private EspecialidadRepository especialidadRepository;
	@Autowired private ConsultorioService consultorioService;
	@Autowired private SecurityService securityService;


	//****************** SERVICIOS PARA CITAS************************//

	//Buscar todos las personas para listar en angular
	@RequestMapping(value="/buscarTiposScouts", method=RequestMethod.GET)
	public ResponseEntity<?> buscarTiposScouts(@RequestHeader(value="Authorization") String Authorization) {
		if (!securityService.isTokenValido(Authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(tipoScoutRepository.findAll());
	}


	// servicio para grabar Citas
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


	
//	//****************** SERVICIOS PARA CITAS************************//
//	
//	//****************** SERVICIOS PARA CONSULTAS************************//
//	//	Recuepera los datos de la cita
//	@RequestMapping(value = "/recuperarDatosCita/{idCita}", method = RequestMethod.GET)
//	public ResponseEntity<?> recuperarDatosCita(@RequestHeader(value = "Authorization") String authorization,
//			@PathVariable("idCita") Long idCita) {
//		if (!securityService.isTokenValido(authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		return ResponseEntity.ok(citaRepository.fnRecuperarDatosCita(idCita));
//
//	}
//	
	// servicio que devuelve el objeto consulta
//	@RequestMapping(value = "/listarConsultaId/{idConsulta}", method = RequestMethod.GET)
//	public ResponseEntity<?> listarConsultaId(@RequestHeader(value = "Authorization") String authorization,
//			@PathVariable("idConsulta") Long idConsulta) {
//		if (!securityService.isTokenValido(authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		Consulta _consulta;
//		if (consultaRepository.findById(idConsulta).isPresent()) {
//			_consulta = consultaRepository.findById(idConsulta).get();
//			return ResponseEntity.ok(_consulta);
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}
//
//	//lista procedimientos
//	@RequestMapping(value = "/listarProcedimientos", method = RequestMethod.GET)
//	public ResponseEntity<?> listarProcedimientos(@RequestHeader(value = "Authorization") String Authorization ){
//		if (!securityService.isTokenValido(Authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		return ResponseEntity.ok(procedimientoRepository.listarProcedimientos());
//	}
//	
//	
//	// servicio para grabar Consulta
//	@RequestMapping(value = "/grabarConsulta", method = RequestMethod.POST)
//	public ResponseEntity<?> grabarConsulta(@RequestHeader(value = "Authorization") String Authorization,
//			@RequestBody Consulta consulta) {
//		if (!securityService.isTokenValido(Authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		consultorioService.grabarConsultas(consulta);
//		return ResponseEntity.ok().build();
//	}
//	
//	//****************** SERVICIOS PARA CONSULTAS************************//
}

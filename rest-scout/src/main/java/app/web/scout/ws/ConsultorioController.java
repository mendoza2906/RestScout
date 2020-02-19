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
import app.web.scout.model.repository.ActividadRepository;
import app.web.scout.model.repository.RamaRepository;
import app.web.scout.model.repository.ComisionadoRepository;
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

//	// servicio que devuelve una personas por id con sus dependencias
//	@RequestMapping(value = "/buscarPersonaId/{idPersona}", method = RequestMethod.GET)
//	public ResponseEntity<?> buscarPersonaId(@RequestHeader(value = "Authorization") String authorization,
//			@PathVariable("idPersona") Integer idPersona) {
//		if (!securityService.isTokenValido(authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//
//		Persona _persona;
//		if (personaRepository.findById(idPersona).isPresent()) {
//			_persona = personaRepository.findById(idPersona).get();
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
//			return ResponseEntity.notFound().build();
//		}
//	}
//	//lista las especialidades
//	@RequestMapping(value = "/listarEspecialidades", method = RequestMethod.GET)
//	public ResponseEntity<?> listarEspecialidades(@RequestHeader(value = "Authorization") String Authorization ){
//		if (!securityService.isTokenValido(Authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		return ResponseEntity.ok(especialidadRepository.listarEspecialidades());
//	}
//
//	//lista pacientes
//	@RequestMapping(value = "/listarPacientes", method = RequestMethod.GET)
//	public ResponseEntity<?> listarPacientes(@RequestHeader(value = "Authorization") String Authorization ){
//		if (!securityService.isTokenValido(Authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		return ResponseEntity.ok(pacienteRepository.listarPacientes());
//	}
//
//	//lista medicos
//	@RequestMapping(value = "/listarMedicos/{idEspecialidad}", method = RequestMethod.GET)
//	public ResponseEntity<?> listarMedicos(@RequestHeader(value = "Authorization") String Authorization,
//			@PathVariable("idEspecialidad") Integer idEspecialidad) {
//		if (!securityService.isTokenValido(Authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		return ResponseEntity.ok(medicoRepository.listarMedicos(idEspecialidad));
//
//	}
//
//	//lista citas
//	@RequestMapping(value = "/listarCitas/{fechaCita}", method = RequestMethod.GET)
//	public ResponseEntity<?> listarCitas(@RequestHeader(value = "Authorization") String Authorization,
//			@PathVariable("fechaCita") String fechaCita) {
//		if (!securityService.isTokenValido(Authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		return ResponseEntity.ok(citaRepository.listarCitas(fechaCita));
//
//	}
//
//
//	//	 Procedimiento que genera las horas y citas
//	@RequestMapping(value = "/listarCitasProc/{idMedico}/{fechaCita}", method = RequestMethod.GET)
//	public ResponseEntity<?> listarCitasProc(@RequestHeader(value = "Authorization") String authorization,
//			@PathVariable("idMedico") Integer idMedico,@PathVariable("fechaCita") String fechaCita) {
//		if (!securityService.isTokenValido(authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		return ResponseEntity.ok(consultorioService.refreshFnListarCitas(idMedico,fechaCita));
//
//	}
//	
////	 Listar todas citas por rango de fechas
//	@RequestMapping(value = "/listarCitasFechas/{fechaCitaInicio}/{fechaCitaFin}", method = RequestMethod.GET)
//	public ResponseEntity<?> listarCitasFechas(@RequestHeader(value = "Authorization") String authorization,
//			@PathVariable("fechaCitaInicio") String fechaCitaInicio,@PathVariable("fechaCitaFin") String fechaCitaFin) {
//		if (!securityService.isTokenValido(authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		return ResponseEntity.ok(citaRepository.fnListarCitasFechas(fechaCitaInicio, fechaCitaFin));
//
//	}
//
////	Devuleve las horas disponibles de un dia
//	@RequestMapping(value = "/listarHoras/{idMedico}/{fechaCita}/{idHora}", method = RequestMethod.GET)
//	public ResponseEntity<?> listarHoras(@RequestHeader(value = "Authorization") String authorization,
//			@PathVariable("idMedico") Integer idMedico,@PathVariable("fechaCita") String fechaCita,
//			@PathVariable("idHora") Integer idHora) {
//		if (!securityService.isTokenValido(authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		return ResponseEntity.ok(horaRepository.fnListarHoras(idMedico, fechaCita,idHora));
//
//	}
//	
//	// servicio que devuelve el objeto Cita por id
//	@RequestMapping(value = "/listarCitaId/{idCita}", method = RequestMethod.GET)
//	public ResponseEntity<?> listarCitaId(@RequestHeader(value = "Authorization") String authorization,
//			@PathVariable("idCita") Long idCita) {
//		if (!securityService.isTokenValido(authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		Cita _cita;
//		if (citaRepository.findById(idCita).isPresent()) {
//			_cita = citaRepository.findById(idCita).get();
//			return ResponseEntity.ok(_cita);
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}
//
//	// servicio para grabar Citas
//	@RequestMapping(value="/grabarCita", method=RequestMethod.POST)
//	public ResponseEntity<?> grabarCita(@RequestHeader(value="Authorization") String Authorization, 
//			                        @RequestBody Cita cita) {
//		if (!securityService.isTokenValido(Authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		Cita _cita = new Cita();
//		if (cita.getId() != null) {
//			_cita = citaRepository.findById(cita.getId()).get();
//		}
//		BeanUtils.copyProperties(cita, _cita);
//		citaRepository.save(_cita);
//		return ResponseEntity.ok(_cita);
//	}
//	
//
//
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
//	// servicio que devuelve el objeto consulta
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

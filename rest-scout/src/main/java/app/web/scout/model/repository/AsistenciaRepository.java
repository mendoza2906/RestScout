package app.web.scout.model.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.web.scout.model.pojo.Asistencia;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Integer> {
//	@Query(value="select p.id as idPersona,pa.id as idPaciente,c.idMedico as idMedico,concat(p.nombres,' ',p.apellidos) as nombres, "+
//			" pa.historialMedico as historialMedico,c.motivoConsulta as motivoConsulta," +
//			" c.fechaCita as fechaCita, c.id as idCita, " +
//			" CASE WHEN c.estadoCita='ATE' THEN 'Atendido' " + 
//			"    WHEN c.estadoCita='ANU' THEN 'Anulado' " + 
//			"	WHEN c.estadoCita='PEN' THEN 'Pendiente' " + 
//			"	WHEN c.estadoCita='ATD' THEN 'Atendiendose' " + 
//			"	WHEN c.estadoCita='CIT' THEN 'Citado' " + 
//			"	WHEN c.estadoCita='CON' THEN 'Confirmado por Teléfono' " + 
//			"	WHEN c.estadoCita='SAL' THEN 'En sala de espera' " + 
//			"	WHEN c.estadoCita='NOA' THEN 'No asiste' " + 
//			"    ELSE 'NO confirmado' END as estadoCita " + 
//			" from Persona p  " + 
//			" inner join Paciente pa on pa.idPersona = p.id " + 
//			" inner join Cita c on c.idPaciente = pa.id " + 
//			" where c.fechaCita = CAST((?1) AS date)")
//	List<CustObjCitas> listarCitas(String fechaCita);
//	interface CustObjCitas{
//		Integer getIdPersona();
//		Long getIdCita();
//		Integer getIdPaciente();
//		Integer getIdMedico();
//		String getNombres();
//		String getHistorialMedico();
//		String getMotivoConsulta();
////		String getHoraCita();
//		String getEstadoCita();		
//	}
//	
//	@Query(value = "select d.id_persona as idPersona, d.id_cita as idCita, d.id_paciente as idPaciente, d.id_medico as idMedico, " + 
//			" d.id_hora as idHora, d.hora as horaCita, d.nombres as nombres, d.historial_medico as historialMedico, "+
//			" d.observacion as observacion, d.motivo_consulta as motivoConsulta , d.fecha_cita as fechaCita, " +
//			" d.estado_cita as estadoCita from  med.fn_listar_citas "+
//			" (:pi_id_medico,:pi_fechaCita) as d ", nativeQuery = true)
//	public List<CustObjCitasProc> fnListarCitas(@Param("pi_id_medico") Integer pi_id_medico,
//			@Param("pi_fechaCita") String pi_fechaCita);
//
//	public interface CustObjCitasProc{
//		Integer getIdPersona();
//		Long getIdCita();
//		Integer getIdPaciente();
//		Integer getIdMedico();
//		Integer getIdHora();
//		String getHoraCita();
//		String getNombres();
//		String getHistorialMedico();
//		String getObservacion();
//		String getMotivoConsulta();
//		Date getFechaCita();
//		String getEstadoCita();		
//	}
//	
//	
//	@Query(value = "select d.id_persona as idPersona, d.id_cita as idCita, d.id_paciente as idPaciente, d.id_medico as idMedico, " + 
//			" d.id_hora as idHora, d.hora as horaCita, d.nombres as nombres, d.historial_medico as historialMedico,d.nombresMedico as nombresMedico, "+
//			" d.observacion as observacion, d.motivo_consulta as motivoConsulta, d.fecha_cita as fechaCita, " +
//			" d.estado_cita as estadoCita, d.id_consulta as idConsulta, d.diagnostico as diagnostico, d.obsCon as obsCon "+
//			" from  med.fn_listar_citas_fechas (:pi_fecha_inicio,:pi_fecha_fin) as d ", nativeQuery = true)
//	public List<CustObjCitasFechas> fnListarCitasFechas(@Param("pi_fecha_inicio") String pi_fecha_inicio,
//			@Param("pi_fecha_fin") String pi_fecha_fin);
//
//	public interface CustObjCitasFechas{
//		Integer getIdPersona();
//		Long getIdCita();
//		Integer getIdPaciente();
//		Integer getIdMedico();
//		Integer getIdHora();
//		String getHoraCita();
//		String getNombres();
//		String getNombresMedico();
//		String getHistorialMedico();
//		String getObservacion();
//		String getMotivoConsulta();
//		Date getFechaCita();
//		String getEstadoCita();	
//		Long getIdConsulta();
//		String getDiagnostico();	
//		String getObsCon();	
//	}
//	
//	@Query(value = "select  d.id_cita as idCita, d.nombrePaciente as nombrePaciente, d.nombreMedico as nombreMedico, " + 
//			" d.historial_medico as historialMedico,d.observacion as observacion, "+
//			" d.motivo_consulta as motivoConsulta, d.hora as hora, d.fecha_cita as fechaCita, " +
//			" d.estado_cita as estadoCita "+
//			" from  med.fn_recuperar_datos_cita (:pi_id_cita) as d ", nativeQuery = true)
//	public List<CustObjRecCitas> fnRecuperarDatosCita(@Param("pi_id_cita") Long pi_id_cita);
//
//	public interface CustObjRecCitas{
//		Long getIdCita();
//		String getNombrePaciente();
//		String getNombreMedico();
//		String getHistorialMedico();
//		String getObservacion();
//		String getMotivoConsulta();
//		String getHora();
//		Date getFechaCita();
//		String getEstadoCita();	
//	}
}

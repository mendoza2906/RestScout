package app.web.scout.ws;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.web.scout.model.service.ReportService;
import app.web.scout.model.service.SecurityService;
import lombok.Getter;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin
@MultipartConfig
public class ReportController {
	

	@Autowired private SecurityService securityService;
	@Autowired private ReportService reportService;
	
	private static final String PATH_IMAGEN = "/src/main/resources/img";
	private static final String PATH_REPORT = File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"rep";
	//@Getter private String rutaImagenBanerLocal="C:/Desarrollo/workspace/siiarestv02/siiarest/src/main/resources/img/inicioSesion.png";
	@Getter private String imagen="inicioSesion.png";
	@Getter private String rutaImagenBanerLocal="";
	//@Getter private String rutaImagenBanerLocal="../img/inicioSesion.png";
	
	@Getter private String rutaArchivoLocal="";
	
	//@Getter private String rutaArchivoLocal="../rep/";;

	@Getter private String carpetaIndicadores="indicadoresCalidad";
	@Getter private String carpetaDistributivo="distributivo";

	@RequestMapping(value="/getReport/{filterIdOferta}", method=RequestMethod.GET)
    public ResponseEntity<?> getReportMallaCarrera(@RequestHeader(value="Authorization") String authorization,				 
    		@PathVariable("filterIdOferta") Integer filterIdOferta,   
    								   HttpServletRequest request) throws Exception {
	
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Map<String, Object> parametros = new HashMap<String, Object>();
	     parametros.put("idOferta", filterIdOferta);
	     System.out.println(parametros);
        // Carga el archivo
        Resource resource = reportService.generaReporteParametros("rptMallaComponenteAprendizaje.jasper",parametros);

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
	
	//reporte de indicadores de calidad
	@RequestMapping(value="/getReportIndicadoresCalidadPorDedicacion/{filterIdPeriodo}/{filterIdTipoOferta}/{filterIdDepartamento}", method=RequestMethod.GET)
    public ResponseEntity<?> getReportIndicadoresCalidadPorDedicacion(@RequestHeader(value="Authorization") String authorization,
    								 
    		@PathVariable("filterIdPeriodo") Integer filterIdPeriodo, 
    		@PathVariable("filterIdTipoOferta") Integer filterIdTipoOferta, 
    		@PathVariable("filterIdDepartamento") Integer filterIdDepartamento, 
    								   HttpServletRequest request) throws Exception {
		
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Map<String, Object> parametros = new HashMap<String, Object>();
	     parametros.put("pPeriodo", filterIdPeriodo);
	     parametros.put("tipoOferta", filterIdTipoOferta);
	     if(filterIdDepartamento!=0) {
	    	 parametros.put("departamento", filterIdDepartamento);
	     }
	 	
	     
	    File file =new File(imagen);
     	String ruta =file.getAbsoluteFile().getParent();
     	rutaImagenBanerLocal  = ruta+PATH_IMAGEN+File.separator+imagen; 
     	rutaArchivoLocal  = ruta+PATH_REPORT+File.separator+carpetaIndicadores+File.separator;
		parametros.put("rutaImagen", rutaImagenBanerLocal);
		parametros.put("rutaArchivo", rutaArchivoLocal);
	  
        // Carga el archivo
        Resource resource = reportService.generaReporteParametrosCarpeta(carpetaIndicadores,"rptPorcentajeDocenteDedicacion.jasper",parametros);
        // Determina el contenido
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Si no se determina el tipo, asume uno por defecto.
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
	@RequestMapping(value="/getReportIndicadoresCalidadPorCategoria/{filterIdPeriodo}/{filterIdTipoOferta}/{filterIdDepartamento}", method=RequestMethod.GET)
    public ResponseEntity<?> getReportIndicadoresCalidadPorCategoria(@RequestHeader(value="Authorization") String authorization,
    								 
    		@PathVariable("filterIdPeriodo") Integer filterIdPeriodo, 
    		@PathVariable("filterIdTipoOferta") Integer filterIdTipoOferta, 
    		@PathVariable("filterIdDepartamento") Integer filterIdDepartamento, 
    								   HttpServletRequest request) throws Exception {
		
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Map<String, Object> parametros = new HashMap<String, Object>();
	     parametros.put("pPeriodo", filterIdPeriodo);
	     parametros.put("tipoOferta", filterIdTipoOferta);
	     if(filterIdDepartamento!=0) {
	    	 parametros.put("departamento", filterIdDepartamento);
	     }
	 
		    File file =new File(imagen);
	     	String ruta =file.getAbsoluteFile().getParent();
	     	rutaImagenBanerLocal  = ruta+PATH_IMAGEN+File.separator+imagen; 
	     	rutaArchivoLocal  = ruta+PATH_REPORT+File.separator+carpetaIndicadores+File.separator;
			parametros.put("rutaImagen", rutaImagenBanerLocal);
			parametros.put("rutaArchivo", rutaArchivoLocal);
	     
	     
        // Carga el archivo
        Resource resource = reportService.generaReporteParametrosCarpeta(carpetaIndicadores,"rptPorcentajeDocenteCategoria.jasper",parametros);

        // Determina el contenido
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Si no se determina el tipo, asume uno por defecto.
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
	
	//reporte de indicadores de calidad
	@RequestMapping(value="/getReportIndicadoresCalidadPorGenero/{filterIdPeriodo}/{filterIdTipoOferta}/{filterIdDepartamento}", method=RequestMethod.GET)
    public ResponseEntity<?> getReportIndicadoresCalidadPorGenero(@RequestHeader(value="Authorization") String authorization,
    								 
    		@PathVariable("filterIdPeriodo") Integer filterIdPeriodo, 
    		@PathVariable("filterIdTipoOferta") Integer filterIdTipoOferta, 
    		@PathVariable("filterIdDepartamento") Integer filterIdDepartamento, 
    								   HttpServletRequest request) throws Exception {
		
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Map<String, Object> parametros = new HashMap<String, Object>();
	     parametros.put("pPeriodo", filterIdPeriodo);
	     parametros.put("tipoOferta", filterIdTipoOferta);
	     if(filterIdDepartamento!=0) {
	    	 parametros.put("departamento", filterIdDepartamento);
	     }
		    File file =new File(imagen);
	     	String ruta =file.getAbsoluteFile().getParent();
	     	rutaImagenBanerLocal  = ruta+PATH_IMAGEN+File.separator+imagen; 
	     	rutaArchivoLocal  = ruta+PATH_REPORT+File.separator+carpetaIndicadores+File.separator;
			parametros.put("rutaImagen", rutaImagenBanerLocal);
			parametros.put("rutaArchivo", rutaArchivoLocal);
	    // System.out.println(parametros.toString());
	  
        // Carga el archivo
        Resource resource = reportService.generaReporteParametrosCarpeta(carpetaIndicadores,"rptPorcentajeDocenteGenero.jasper",parametros);

        // Determina el contenido
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Si no se determina el tipo, asume uno por defecto.
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
	
	
	@RequestMapping(value="/getReportIndicadoresCalidadPorActividad/{filterIdPeriodo}/{filterIdTipoOferta}/{filterIdDepartamento}", method=RequestMethod.GET)
    public ResponseEntity<?> getReportIndicadoresCalidadPorActividad(@RequestHeader(value="Authorization") String authorization,
    								 
    		@PathVariable("filterIdPeriodo") Integer filterIdPeriodo, 
    		@PathVariable("filterIdTipoOferta") Integer filterIdTipoOferta, 
    		@PathVariable("filterIdDepartamento") Integer filterIdDepartamento, 
    								   HttpServletRequest request) throws Exception {
		
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Map<String, Object> parametros = new HashMap<String, Object>();
	     parametros.put("pPeriodo", filterIdPeriodo);
	     parametros.put("tipoOferta", filterIdTipoOferta);
	     if(filterIdDepartamento!=0) {
	    	 parametros.put("departamento", filterIdDepartamento);
	     }

		    File file =new File(imagen);
	     	String ruta =file.getAbsoluteFile().getParent();
	     	rutaImagenBanerLocal  = ruta+PATH_IMAGEN+File.separator+imagen; 
	     	rutaArchivoLocal  = ruta+PATH_REPORT+File.separator+carpetaIndicadores+File.separator;
			parametros.put("rutaImagen", rutaImagenBanerLocal);
			parametros.put("rutaArchivo", rutaArchivoLocal);
	    // System.out.println(parametros.toString());
	  
        // Carga el archivo
        Resource resource = reportService.generaReporteParametrosCarpeta(carpetaIndicadores,"rptPorcentajeDocenteActividad.jasper",parametros);

        // Determina el contenido
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Si no se determina el tipo, asume uno por defecto.
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
	

	
	
	//reporte de indicadores de calidad
	@RequestMapping(value="/getReportIndicadoresCalidadPromedioHorasClases/{filterIdPeriodo}/{filterIdTipoOferta}/{filterIdDepartamento}", method=RequestMethod.GET)
    public ResponseEntity<?> getReportIndicadoresCalidadPromedioHorasClases(@RequestHeader(value="Authorization") String authorization,
    		@PathVariable("filterIdPeriodo") Integer filterIdPeriodo,   
    		@PathVariable("filterIdTipoOferta") Integer filterIdTipoOferta, 
    		@PathVariable("filterIdDepartamento") Integer filterIdDepartamento, 
    								   HttpServletRequest request) throws Exception {
		
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Map<String, Object> parametros = new HashMap<String, Object>();
	    parametros.put("periodoAcademico", filterIdPeriodo);
	    parametros.put("tipoOferta", filterIdTipoOferta);
	     if(filterIdDepartamento!=0) {
	    	 parametros.put("departamento", filterIdDepartamento);
	     }
	
		    File file =new File(imagen);
	     	String ruta =file.getAbsoluteFile().getParent();
	     	rutaImagenBanerLocal  = ruta+PATH_IMAGEN+File.separator+imagen; 
	     	rutaArchivoLocal  = ruta+PATH_REPORT+File.separator+carpetaIndicadores+File.separator;
			parametros.put("rutaImagen", rutaImagenBanerLocal);
			parametros.put("rutaArchivo", rutaArchivoLocal);
		System.out.println(parametros.toString());
        // Carga el archivo
        Resource resource = reportService.generaReporteParametrosCarpeta(carpetaIndicadores,"rptPromedioHorasDocentePorDedicacion.jasper",parametros);

        //MANDE A GENERAR UN JASPER VACIO 
        
        // Determina el contenido
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Si no se determina el tipo, asume uno por defecto.
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
	//reporte de indicadores de calidad
	@RequestMapping(value="/getReportIndicadoresCalidadPromedioEstudiante/{filterIdPeriodo}/{filterIdTipoOferta}/{filterIdDepartamento}", method=RequestMethod.GET)
    public ResponseEntity<?> getReportIndicadoresCalidadPromedioEstudiante(@RequestHeader(value="Authorization") String authorization,
    								 
    		@PathVariable("filterIdPeriodo") Integer filterIdPeriodo,  
    		@PathVariable("filterIdTipoOferta") Integer filterIdTipoOferta, 
    		@PathVariable("filterIdDepartamento") Integer filterIdDepartamento, 
    								   HttpServletRequest request) throws Exception {
		
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("periodoAcademico", filterIdPeriodo);
	    parametros.put("tipoOferta", filterIdTipoOferta);
	     if(filterIdDepartamento!=0) {
	    	 parametros.put("departamento", filterIdDepartamento);
	     }

		    File file =new File(imagen);
	     	String ruta =file.getAbsoluteFile().getParent();
	     	rutaImagenBanerLocal  = ruta+PATH_IMAGEN+File.separator+imagen; 
	     	rutaArchivoLocal  = ruta+PATH_REPORT+File.separator+carpetaIndicadores+File.separator;
			parametros.put("rutaImagen", rutaImagenBanerLocal);
			parametros.put("rutaArchivo", rutaArchivoLocal);
        // Carga el archivo
        Resource resource = reportService.generaReporteParametrosCarpeta(carpetaIndicadores,"rptPromedioEstudiantePorProfesorDedicacion.jasper",parametros);

        // Determina el contenido
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Si no se determina el tipo, asume uno por defecto.
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
	@RequestMapping(value="/getReportDistributivoDocente/{filterIdPeriodo}/{filterIdDepartamento}/{filterIdOferta}/{filterIdDistOfertaVersion}/{formato}", method=RequestMethod.GET)
    public ResponseEntity<?> getReportDistributivoDocente(@RequestHeader(value="Authorization") String authorization,
    								 
    		@PathVariable("filterIdPeriodo") Integer filterIdPeriodo,  
    		@PathVariable("filterIdDepartamento") Integer filterIdDepartamento,  
    		@PathVariable("filterIdOferta") Integer filterIdOferta,  
    		@PathVariable("filterIdDistOfertaVersion") Integer filterIdDistOfertaVersion,  
    		@PathVariable("formato") String formato,  
    								   HttpServletRequest request) throws Exception {
		
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Map<String, Object> parametros = new HashMap<String, Object>();
	     parametros.put("id_periodo_academico", filterIdPeriodo);
	     parametros.put("id_departamento", filterIdDepartamento);
		if(filterIdOferta!=0) {
			 parametros.put("id_oferta", filterIdOferta);
		}
	    
		if(filterIdDistOfertaVersion!=0) {
			   parametros.put("id_distributivo", filterIdDistOfertaVersion);
		}
		
		
		
	    File file =new File(imagen);
     	String ruta =file.getAbsoluteFile().getParent();
     	rutaImagenBanerLocal  = ruta+PATH_IMAGEN+File.separator+imagen; 
 
		parametros.put("rutaImagen", rutaImagenBanerLocal);

	     
	   
	    // System.out.println(parametros);
        // Carga el archivo
        Resource resource = reportService.generaReporteParametrosFormatoCarpeta(carpetaDistributivo,"rptDistributivoDocentePorCarrera.jasper",parametros,formato);

        // Determina el contenido
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Si no se determina el tipo, asume uno por defecto.
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }	
	
	

	@RequestMapping(value="/getReportMallaPorCarrera/{filterIdCarrera}/{filterIdMallaVersion}/{filterIdPeriodo}/{formato}", method=RequestMethod.GET)
    public ResponseEntity<?> getReportMallaPorCarrera(@RequestHeader(value="Authorization") String authorization,				 
    		@PathVariable("filterIdCarrera") Integer filterIdCarrera,  
    		@PathVariable("filterIdMallaVersion") Integer filterIdMallaVersion,  
    		@PathVariable("filterIdPeriodo") Integer filterIdPeriodo, 
    		@PathVariable("formato") String formato,  
    								   HttpServletRequest request) throws Exception {
		
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Map<String, Object> parametros = new HashMap<String, Object>();
	     parametros.put("Carrera", filterIdCarrera);
	     parametros.put("mallaVersion", filterIdMallaVersion);
	     parametros.put("periodo", filterIdPeriodo);
	    File file =new File(imagen);
     	String ruta =file.getAbsoluteFile().getParent();
     	rutaImagenBanerLocal  = ruta+PATH_IMAGEN+File.separator+imagen; 
    
		parametros.put("rutaImagen", rutaImagenBanerLocal);
	  
	    // System.out.println(parametros);
        // Carga el archivo
        Resource resource = reportService.generaReporteParametrosFormato("rptMallaPorCarrera.jasper",parametros, formato);

        // Determina el contenido
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Si no se determina el tipo, asume uno por defecto.
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
	
	
	@RequestMapping(value="/getReportHorasSemanalesPorFacultad/{filterIdFacultad}/{filterIdPeriodo}/{formato}", method=RequestMethod.GET)
    public ResponseEntity<?> getReportHorasSemanalesPorFacultad(@RequestHeader(value="Authorization") String authorization,				 
    		@PathVariable("filterIdFacultad") Integer filterIdFacultad,  
    		@PathVariable("filterIdPeriodo") Integer filterIdPeriodo, 
    		@PathVariable("formato") String formato,  
    								   HttpServletRequest request) throws Exception {
		
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Map<String, Object> parametros = new HashMap<String, Object>();
	    parametros.put("facultad", filterIdFacultad);
	    parametros.put("periodo", filterIdPeriodo);
	    
	    File file =new File(imagen);
     	String ruta =file.getAbsoluteFile().getParent();
     	rutaImagenBanerLocal  = ruta+PATH_IMAGEN+File.separator+imagen; 

		parametros.put("rutaImagen", rutaImagenBanerLocal);
	
	
	    // System.out.println(parametros);
        // Carga el archivo
        Resource resource = reportService.generaReporteParametrosFormato("rptMallaHorasSemanalesPorFacultad.jasper",parametros, formato);

        // Determina el contenido
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Si no se determina el tipo, asume uno por defecto.
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
	
	@RequestMapping(value="/getReportMallaPorCarreraRequisito/{filterIdCarrera}/{filterIdMallaVersion}/{filterIdPeriodo}/{formato}", method=RequestMethod.GET)
    public ResponseEntity<?> getReportMallaPorCarreraRequisito(@RequestHeader(value="Authorization") String authorization,				 
    		@PathVariable("filterIdCarrera") Integer filterIdCarrera,  
    		@PathVariable("filterIdMallaVersion") Integer filterIdMallaVersion,  
    		@PathVariable("filterIdPeriodo") Integer filterIdPeriodo, 
    		@PathVariable("formato") String formato,  
    								   HttpServletRequest request) throws Exception {
		
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Map<String, Object> parametros = new HashMap<String, Object>();
	     parametros.put("Carrera", filterIdCarrera);
	     parametros.put("mallaVersion", filterIdMallaVersion);
	     parametros.put("periodo", filterIdPeriodo);
	  
	    // System.out.println(parametros);
        // Carga el archivo
        Resource resource = reportService.generaReporteParametrosFormato("rptMallaPorCarreraRequisito.jasper",parametros, formato);

        // Determina el contenido
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // Si no se determina el tipo, asume uno por defecto.
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
	
	@RequestMapping(value="/getReportDocenteNoCumplenNumHoras/{filterIdPeriodo}/{filterIdDepartamento}/{filterIdOferta}/{filterIdCategoria}/{filterIdDedicacion}/{filterNumHoras}/{filterAux}", method=RequestMethod.GET)
    public ResponseEntity<?> getReportDocenteNoCumplenNumHoras(@RequestHeader(value="Authorization") String authorization,				 
    		@PathVariable("filterIdPeriodo") Integer filterIdPeriodo,  
    		@PathVariable("filterIdDepartamento") Integer filterIdDepartamento,  
    		@PathVariable("filterIdOferta") Integer filterIdOferta,  
    		@PathVariable("filterIdCategoria") Integer filterIdCategoria,  
    		@PathVariable("filterIdDedicacion") Integer filterIdDedicacion,  
    		@PathVariable("filterNumHoras") Integer filterNumHoras,  
    		@PathVariable("filterAux") Boolean filterAux,  
    								   HttpServletRequest request) throws Exception {
		
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		if(filterIdPeriodo!=0) {
			parametros.put("periodo", filterIdPeriodo);
		}
		if(filterIdDepartamento!=0) {
			parametros.put("departamento", filterIdDepartamento);
		}
		if(filterIdOferta!=0) {
			parametros.put("oferta", filterIdOferta);
		}
		if(filterIdCategoria!=0) {
			 parametros.put("categoria", filterIdCategoria);
		}
		if(filterIdDedicacion!=0) {
			parametros.put("dedicacion", filterIdDedicacion);
		}
		if(filterNumHoras!=0) {
			  parametros.put("numHoras", filterNumHoras);
			  
		}
		
		parametros.put("aux", filterAux);
		
	    File file =new File(imagen);
     	String ruta =file.getAbsoluteFile().getParent();
     	rutaImagenBanerLocal  = ruta+PATH_IMAGEN+File.separator+imagen; 
    
		parametros.put("rutaImagen", rutaImagenBanerLocal);

		
		System.out.println(parametros.toString());
	   
	    // System.out.println(parametros);
        // Carga el archivo
        Resource resource = reportService.generaReporteParametros("rptDocenteNoCumplenNumHoras.jasper",parametros);

        // Determina el contenido
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Si no se determina el tipo, asume uno por defecto.
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
	
	@RequestMapping(value="/getReportTasaPermanenciaEst/{filterIdPeriodoAnterior}/{filterIdPeriodoActual}/{filterIdOferta}", method=RequestMethod.GET)
    public ResponseEntity<?> getReportTasaPermanenciaEst(@RequestHeader(value="Authorization") String authorization,				 
    		@PathVariable("filterIdPeriodoAnterior") Integer filterIdPeriodoAnterior,  
    		@PathVariable("filterIdPeriodoActual") Integer filterIdPeriodoActual,  
    		@PathVariable("filterIdOferta") Integer filterIdOferta,
    								   HttpServletRequest request) throws Exception {
		
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Map<String, Object> parametros = new HashMap<String, Object>();
	     parametros.put("id_periodo_academico_anterior", filterIdPeriodoAnterior);
	     parametros.put("id_periodo_academico_actual", filterIdPeriodoActual);
	     parametros.put("id_oferta", filterIdOferta);
	  
	    // System.out.println(parametros);
        // Carga el archivo
        Resource resource = reportService.generaReporteParametros("rpt_tasa_permanencia_estudiante.jasper",parametros);

        // Determina el contenido
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // Si no se determina el tipo, asume uno por defecto.
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}

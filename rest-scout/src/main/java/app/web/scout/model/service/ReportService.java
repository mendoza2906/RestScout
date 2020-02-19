package app.web.scout.model.service;

import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.XlsxReportConfiguration;

@Service
public class ReportService {
	
	@Autowired private DataSource dataSource;
	@Autowired private ServletContext context; 

	private static final String PATH_REPORTES = "/src/main/resources/rep";
	private static final String PATH_TEMPORAL = "/src/main/resources/tmp";

    private String getRealPath(String resource) {
    	return context.getRealPath("") + File.separator + resource;
    }
    public  String fechaSistema(){
		Calendar fecha = new GregorianCalendar();
		int anio = fecha.get(Calendar.YEAR);
		int mes = fecha.get(Calendar.MONTH);
		int dia = fecha.get(Calendar.DAY_OF_MONTH);
		int hora= fecha.get(Calendar.HOUR_OF_DAY);
		int min= fecha.get(Calendar.MINUTE);
		String fechaActual=(hora+"_"+ min+ "_"+dia + "_"+  (mes+1) + "_" + anio );
		return fechaActual;
	}
    

    public Resource generaReporte(String fileName,Integer filterIdMallaVersion) throws Exception {
        try {
        	String fechaSist=fechaSistema();
        	//System.out.println("formula: "+ fechaSist);
        	//System.out.println("random: "+UUID.randomUUID() );
        	//String horaSist=horaSistema();
        	//     String filePath = getRealPath(PATH_TEMPORAL) + File.separator + UUID.randomUUID() + ".pdf";
            String filePath = getRealPath(PATH_TEMPORAL) + File.separator + UUID.randomUUID()+ ".pdf";
            String fileReport = getRealPath(PATH_REPORTES) + File.separator + fileName;
            
			byte[] b = null;
			 Map<String, Object> parametros = new HashMap<String, Object>();
		     parametros.put("idMallaVersion", filterIdMallaVersion);
		     System.out.println(parametros);
		     
			b = JasperRunManager.runReportToPdf(fileReport.toString(), parametros, dataSource.getConnection());
			FileOutputStream fos = new FileOutputStream(filePath);
			fos.write(b);
			fos.close();
			fos.flush();
            
            Resource resource = new UrlResource(Paths.get(filePath).toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new Exception("Archivo no encontrado " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new Exception("Archivo no encontrado " + fileName, ex);
        }
    }
    
   //reportes con parametros
    public Resource generaReporteParametros(String fileName, Map<String, Object> parametros ) throws Exception {
        try {
        	
        	// String filePath = getRealPath(PATH_TEMPORAL) + File.separator + UUID.randomUUID() + ".pdf";
           // String fileReport = getRealPath(PATH_REPORTES) + File.separator + fileName;
           	File file =new File(fileName);
        	String ruta =file.getAbsoluteFile().getParent();
        	String fileReport = ruta+PATH_REPORTES + File.separator +  fileName;
        	
        	File filePathh = new File(ruta+PATH_TEMPORAL);
        	//CREA LA CARPETA SI NO EXISTE
    		if(!filePathh.exists()) {
    			filePathh.mkdir();
    		}		
        	String filePath=filePathh+File.separator +  UUID.randomUUID()+ ".pdf";
			byte[] b = null;
			//System.out.println(parametros);
		     
			b = JasperRunManager.runReportToPdf(fileReport.toString(), parametros, dataSource.getConnection());
			FileOutputStream fos = new FileOutputStream(filePath);
			fos.write(b);
			fos.close();
			fos.flush();
			System.out.println("ruta: "+ filePath);
            
            Resource resource = new UrlResource(Paths.get(filePath).toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new Exception("Archivo no encontrado " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new Exception("Archivo no encontrado " + fileName, ex);
        }
    }

    public Resource generaReporteParametrosCarpeta(String carpeta,String fileName, Map<String, Object> parametros ) throws Exception {
        try {
        	//String fechaSist=fechaSistema();
        	
        	//System.out.println("random: "+UUID.randomUUID() );
        	//String filePath = getRealPath(PATH_TEMPORAL) + File.separator+ carpeta+ fechaSist.toString() + ".pdf";
           // String fileReport = getRealPath(PATH_REPORTES) + File.separator + carpeta+ fileName;
        	File file =new File(fileName);
        	String ruta =file.getAbsoluteFile().getParent();
        	String fileReport = ruta+PATH_REPORTES + File.separator + carpeta+File.separator+ fileName;
        	
        	File filePathh = new File(ruta+PATH_TEMPORAL+ File.separator + carpeta);
        	//CREA LA CARPETA SI NO EXISTE
    		if(!filePathh.exists()) {
    			filePathh.mkdir();
    		}		
        	String filePath=filePathh+File.separator + fileName;
        	
        	
			byte[] b = null;
			//System.out.println(parametros);
		     
			b = JasperRunManager.runReportToPdf(fileReport.toString(), parametros, dataSource.getConnection());
			FileOutputStream fos = new FileOutputStream(filePath);
			fos.write(b);
			fos.close();
			fos.flush();
			
            
            Resource resource = new UrlResource(Paths.get(filePath).toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new Exception("Archivo no encontrado " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new Exception("Archivo no encontrado " + fileName, ex);
        }
    }
    
    //reportes con parametros

	public Resource generaReporteParametrosFormato(String fileName, Map<String, Object> parametros ,String formato) throws Exception {
        try {
        
        	
        	//String fileReport = getRealPath(PATH_REPORTES) + File.separator + fileName;
        	
         	File file =new File(fileName);
        	String ruta =file.getAbsoluteFile().getParent();
        	File fileReporte = new File(ruta+PATH_REPORTES);
        	//CREA LA CARPETA SI NO EXISTE
    		if(!fileReporte.exists()) {
    			fileReporte.mkdir();
    		}		
        	String fileReport=fileReporte+File.separator + fileName;
        	
        	String filePath="";
        	if(formato.equals("xlsx")) {
        		File filePathh = new File(ruta+PATH_TEMPORAL);
        		if(!filePathh.exists()) {
        			filePathh.mkdir();
        		}	
            		 
        		filePath = filePathh+File.separator +  UUID.randomUUID()  + ".xlsx";
        		// filePath = getRealPath(PATH_TEMPORAL) + File.separator + UUID.randomUUID() + ".xlsx";
    			JasperPrint jasperPrint;
    			jasperPrint = JasperFillManager.fillReport(fileReport.toString(), parametros, dataSource.getConnection());
    	        JRXlsxExporter exporterXls = new JRXlsxExporter();
    	        exporterXls.setExporterInput(new SimpleExporterInput(jasperPrint));
    	        exporterXls.setExporterOutput(new SimpleOutputStreamExporterOutput(filePath));
    	        XlsxReportConfiguration configuration = null ;
    			exporterXls.setConfiguration(configuration);;
    	        exporterXls.exportReport();
        	}else if (formato.equals("pdf")) {
        	  	// filePath = getRealPath(PATH_TEMPORAL) + File.separator + UUID.randomUUID()  + ".pdf";
        		File filePathh = new File(ruta+PATH_TEMPORAL);
        		if(!filePathh.exists()) {
        			filePathh.mkdir();
        		}	
            		 
        		filePath = filePathh+File.separator +  UUID.randomUUID()  + ".pdf";
        		
        		
        		byte[] b = null;
    			//System.out.println(parametros);
    		     
    			b = JasperRunManager.runReportToPdf(fileReport.toString(), parametros, dataSource.getConnection());
    			FileOutputStream fos = new FileOutputStream(filePath);
    			fos.write(b);
    			fos.close();
    			fos.flush();
    			//System.out.println("ruta: "+ filePath);
        	}
  
            Resource resource = new UrlResource(Paths.get(filePath).toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new Exception("Archivo no encontrado " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new Exception("Archivo no encontrado " + fileName, ex);
        }
    }

	
	/****
	 * SERVICIO QUE PERMITE OBTENER EL REPORTE PASANDO POR PARAMETROS LA CARPETA DONDE SE ENCUENTRE ALOJADO EL JASPERTREPORT
	 * @param carpeta
	 * @param fileName
	 * @param parametros
	 * @param formato
	 * @return
	 * @throws Exception
	 */
	public Resource generaReporteParametrosFormatoCarpeta(String carpeta,String fileName, Map<String, Object> parametros ,String formato) throws Exception {
        try {
        
        	File file =new File(fileName);
        	String ruta =file.getAbsoluteFile().getParent();
        	File fileReporte = new File(ruta+PATH_REPORTES+File.separator+ carpeta);
        	//CREA LA CARPETA SI NO EXISTE
    		if(!fileReporte.exists()) {
    			fileReporte.mkdir();
    		}		
        	String fileReport=fileReporte+File.separator + fileName;		
        //	String fileReport = getRealPath(PATH_REPORTES) + File.separator+ carpeta+File.separator + fileName;
        	String filePath="";
        	if(formato.equals("xlsx")) {
        		// filePath = getRealPath(PATH_TEMPORAL) + File.separator+ carpeta +File.separator+ UUID.randomUUID() + ".xlsx";
        		File filePathh = new File(ruta+PATH_TEMPORAL+File.separator+ carpeta);
        		if(!filePathh.exists()) {
        			filePathh.mkdir();
        		}	
            		 
        		filePath = filePathh+File.separator +  UUID.randomUUID()  + ".xlsx";
        		
        		JasperPrint jasperPrint;
    			jasperPrint = JasperFillManager.fillReport(fileReport.toString(), parametros, dataSource.getConnection());
    	        JRXlsxExporter exporterXls = new JRXlsxExporter();
    	        exporterXls.setExporterInput(new SimpleExporterInput(jasperPrint));
    	        exporterXls.setExporterOutput(new SimpleOutputStreamExporterOutput(filePath));
    	        XlsxReportConfiguration configuration = null ;
    			exporterXls.setConfiguration(configuration);;
    	        exporterXls.exportReport();
        	}else if (formato.equals("pdf")) {
        	  	 //filePath = getRealPath(PATH_TEMPORAL) + File.separator+ carpeta +File.separator+ UUID.randomUUID()  + ".pdf";
        		File filePathh = new File(ruta+PATH_TEMPORAL+File.separator+ carpeta);
        		if(!filePathh.exists()) {
        			filePathh.mkdir();
        		}	
            		 
        		filePath = filePathh+File.separator +  UUID.randomUUID()  + ".pdf";
        		byte[] b = null;
    			//System.out.println(parametros);
    		     
    			b = JasperRunManager.runReportToPdf(fileReport, parametros, dataSource.getConnection());
    			FileOutputStream fos = new FileOutputStream(filePath);
    			fos.write(b);
    			fos.close();
    			fos.flush();
        	}
  
            Resource resource = new UrlResource(Paths.get(filePath).toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new Exception("Archivo no encontrado " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new Exception("Archivo no encontrado " + fileName, ex);
        }
    }
}
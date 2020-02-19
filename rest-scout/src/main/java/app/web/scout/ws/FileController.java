package app.web.scout.ws;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.web.scout.model.service.FileStorageService;
import app.web.scout.model.service.SecurityService;

@RestController
@RequestMapping("/api/files")
@CrossOrigin
@MultipartConfig
public class FileController {

	@Autowired private FileStorageService fileStorageService;
	@Autowired private SecurityService securityService;
    
    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestHeader(value="Authorization") String authorization,
    									@RequestParam("file") MultipartFile file) {
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
    	UploadFileResponse carga = upload(file);
    	return ResponseEntity.ok(carga);
    }
    
    @PostMapping("/uploadMultipleFiles")
    public ResponseEntity<?> uploadMultipleFiles(@RequestHeader(value="Authorization") String authorization,
    											 @RequestParam("files") MultipartFile[] files) {
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
    	List<UploadFileResponse> retorno = Arrays.asList(files)
                .stream()
                .map(file -> upload(file))
                .collect(Collectors.toList());
    	
    	return ResponseEntity.ok(retorno);
    }

    @GetMapping("/uploadFile/{fileName:.+}")
    public ResponseEntity<?> downloadFile(@RequestHeader(value="Authorization") String authorization,
    									  @PathVariable String fileName, 
    									  HttpServletRequest request) throws Exception {
		if (!securityService.isTokenValido(authorization)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
        // Carga el archivo
        Resource resource = fileStorageService.loadFileAsResource(fileName);

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

    private UploadFileResponse upload(MultipartFile file) {
    	try {
            String fileName = fileStorageService.storeFile(file);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    //.path("/files/downloadFile/")
            		.path("/files/uploadFile/")
                    .path(fileName)
                    .toUriString();

            return new UploadFileResponse(fileName, fileDownloadUri,
                    file.getContentType(), file.getSize());
		} catch (Exception e) {
			return null;
		}
    }
    
}
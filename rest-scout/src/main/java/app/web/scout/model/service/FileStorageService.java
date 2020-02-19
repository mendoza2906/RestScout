package app.web.scout.model.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

	private static final String PATH_REPOSITORIO = "/";
	
    private final Path fileStorageLocation;

    public FileStorageService() throws Exception {
        this.fileStorageLocation = Paths.get(PATH_REPOSITORIO)
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new Exception("No se pudo crear el directorio para almacenar los archivos.", ex);
        }
    }

    public String storeFile(MultipartFile file) throws Exception {
        // Normaliza el nombre del archivo.
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Verifica si tiene caracteres especiales
            if(fileName.contains("..")) {
                throw new Exception("El nombre de archivo no es valido. " + fileName);
            }

            // Copia el archivo al destino.
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new Exception("No se pudo almacenar el archivo " + fileName + ".", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) throws Exception {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
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
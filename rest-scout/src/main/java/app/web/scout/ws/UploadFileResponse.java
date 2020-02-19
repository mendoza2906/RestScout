package app.web.scout.ws;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class UploadFileResponse {
    @Getter @Setter private String fileName;
    @Getter @Setter private String fileDownloadUri;
    @Getter @Setter private String fileType;
    @Getter @Setter private long size;
}
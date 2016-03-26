package be.oklw.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;


import javax.ejb.Local;
import javax.ejb.Stateless;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Stateless
public class FileService implements IFileService {

    final String UPLOAD_PATH = "/home/java/development/upload";

    @Override
    public String upload(byte[] fileContent, String fileName, String relativePath) throws IOException {

        Path folder = Paths.get(UPLOAD_PATH + "/" + relativePath);
        String fileBaseName = FilenameUtils.getBaseName(fileName);
        String fileExtension = FilenameUtils.getExtension(fileName);
        Path tempFile = Files.createTempFile(folder, fileBaseName + "-", "." + fileExtension);

        FileUtils.writeByteArrayToFile(tempFile.toFile(), fileContent);

        return tempFile.getFileName().toString();
    }
}

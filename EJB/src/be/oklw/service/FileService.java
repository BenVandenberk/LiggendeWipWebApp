package be.oklw.service;

import be.oklw.exception.BusinessException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.ejb.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FileService implements IFileService {

    final String UPLOAD_PATH = "/home/oklwbb/upload";

    @Override
    public String upload(byte[] fileContent, String fileName, String relativePath) throws IOException {

        Path folder = Paths.get(UPLOAD_PATH + "/" + relativePath);
        String fileBaseName = FilenameUtils.getBaseName(fileName);
        String fileExtension = FilenameUtils.getExtension(fileName);
        Path tempFile = Files.createTempFile(folder, fileBaseName + "-", "." + fileExtension);

        FileUtils.writeByteArrayToFile(tempFile.toFile(), fileContent);

        return tempFile.getFileName().toString();
    }

    @Override
    public void delete(String fileName, String relativePath) throws BusinessException {

        try {
            Path path = Paths.get(String.format("%s/%s/%s", UPLOAD_PATH, relativePath, fileName));
            Files.delete(path);
        } catch(NoSuchFileException nsfEx) {
            throw new BusinessException("De file die je wou deleten bestaat niet");
        } catch (IOException ioEx) {
            throw new BusinessException("Er liep iets mis: " + ioEx.getMessage());
        }
    }
}

package be.oklw.service;

import javax.ejb.Local;

import java.io.IOException;

@Local
public interface IFileService {

    /**
     * Load een file up naar de uploadfolder van de server
     *
     * @param fileContent de up te loaden file
     * @param fileName de filename
     * @param relativePath het relatieve pad vanaf de uploadfolder van de server
     * @return het absolute pad naar de upgeloade file
     */
    String upload(byte[] fileContent, String fileName, String relativePath) throws IOException;
}

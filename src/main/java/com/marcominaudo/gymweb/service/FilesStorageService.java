package com.marcominaudo.gymweb.service;

import com.marcominaudo.gymweb.exception.exceptions.FileStorageException;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FilesStorageService {
    private final Path root = Paths.get("uploads");

    @SneakyThrows
    // Create directory if not exist
    public void init(Path directory) {
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            throw new FileStorageException(FileStorageException.ExceptionCodes.DIRECTORY_NOT_CREATED);
        }
    }

    @SneakyThrows
    public String save(MultipartFile file, String uuid) {
        String fileName = UUID.randomUUID().toString() + ".pdf";
        Path pathDirectory = root.resolve(uuid);
        Path pathFile = pathDirectory.resolve(fileName);
        if(!Files.exists(pathDirectory))
            init(pathDirectory);
        try {
            Files.copy(file.getInputStream(), pathFile);
        } catch (Exception e) {
            throw new FileStorageException(FileStorageException.ExceptionCodes.FILE_NOT_SAVED);
        }
        return Paths.get(uuid, fileName).toString();
    }

    @SneakyThrows
    public Resource load(String folder, String filename) {
        Path folderNamePath = Paths.get(folder, filename);
        Path completePath = root.resolve(folderNamePath);
        Resource resource = null;
        try {
            resource = new UrlResource(completePath.toUri());
        }
        catch (Exception e){
            throw new FileStorageException(FileStorageException.ExceptionCodes.URI_INVALID);
        }
        if (resource.exists() || resource.isReadable()) {
            return resource;
        }
        throw new FileStorageException(FileStorageException.ExceptionCodes.FILE_NOT_EXIST);
    }
}


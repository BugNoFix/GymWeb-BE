package com.marcominaudo.gymweb.service;

import com.marcominaudo.gymweb.exception.exceptions.FileStorageException;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FilesStorageService {
    private final Path root = Paths.get("uploads");

    @SneakyThrows
    public void init(Path directory) {
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            throw new FileStorageException("Directory not created");
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
            throw new FileStorageException("File not saved");
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
                throw new FileStorageException("File not exist");
            }
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            throw new FileStorageException("Can't load the file");
    }

    @SneakyThrows
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new FileStorageException("Could not load the files");
        }
    }

    @SneakyThrows
    public Stream<Path> loadAllOfUser(String uuid) {
        try {
            Path pathFile = root.resolve(uuid);
            return Files.walk(this.root, 2).filter(path -> !path.equals(this.root) && path.equals(pathFile)).map(this.root::relativize);
        } catch (IOException e) {
            throw new FileStorageException("Could not load the files");
        }
    }
}


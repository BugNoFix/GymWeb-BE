package com.marcominaudo.gymweb.unitTest.service;

import com.marcominaudo.gymweb.exception.exceptions.FileStorageException;
import com.marcominaudo.gymweb.service.FilesStorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class FileStorageServiceTest {


    @Mock
    MultipartFile multipartFile;

    @InjectMocks
    FilesStorageService filesStorageService;

    // --------- Init (Create directory) ----------
    @Test
    void initThrowDirectoryNotCreated(){
        try (MockedStatic<Files> utilities = Mockito.mockStatic(Files.class)) {
            utilities.when(() -> Files.createDirectories(any(Path.class))).thenThrow(new IOException());
            FileStorageException thrown = assertThrows(FileStorageException.class, () -> filesStorageService.init(Paths.get("uploads")));
            assertEquals(FileStorageException.ExceptionCodes.DIRECTORY_NOT_CREATED.name(), thrown.getMessage());

        }
    }

    @Test
    void initSuccessful(){
        try (MockedStatic<Files> utilities = Mockito.mockStatic(Files.class)) {
            utilities.when(() -> Files.createDirectories(any(Path.class))).thenReturn(null);
            assertDoesNotThrow(() -> filesStorageService.init(Paths.get("uploads")));

        }
    }
    // ----------------------------------------

    // --------- Save file ----------
    @Test
    // Case: directory non exist and IOEXCEPTION throwed for generic error
    void saveFileThrowFileNotSaved(){
        try (MockedStatic<Files> utilities = Mockito.mockStatic(Files.class)) {
            // Mock
            utilities.when(() -> Files.exists(any(Path.class))).thenReturn(false);
            utilities.when(() -> Files.copy(any(Path.class), any(OutputStream.class))).thenThrow(new IOException());

            // Test
            FileStorageException thrown = assertThrows(FileStorageException.class, () -> filesStorageService.save(null, "uuid"));
            assertEquals(FileStorageException.ExceptionCodes.FILE_NOT_SAVED.name(), thrown.getMessage());

        }
    }
    @Test
    // Case: directory exist and IOEXCEPTION throwed for generic error
    void saveFileThrowFileNotSaved2(){
        try (MockedStatic<Files> utilities = Mockito.mockStatic(Files.class)) {
            // Mock
            utilities.when(() -> Files.exists(any(Path.class))).thenReturn(true);
            utilities.when(() -> Files.copy(any(Path.class), any(OutputStream.class))).thenThrow(new IOException());

            // Test
            FileStorageException thrown = assertThrows(FileStorageException.class, () -> filesStorageService.save(null, "uuid"));
            assertEquals(FileStorageException.ExceptionCodes.FILE_NOT_SAVED.name(), thrown.getMessage());

        }
    }

    @Test
    // Case: directory not exist
    void saveFileSuccessful(){
        try (MockedStatic<Files> utilities = Mockito.mockStatic(Files.class)) {
            // Mock
            utilities.when(() -> Files.exists(any(Path.class))).thenReturn(false);
            utilities.when(() -> Files.copy(any(Path.class), any(OutputStream.class))).thenReturn(1L);

            // Test
            assertDoesNotThrow(() -> filesStorageService.save(multipartFile, "uuid"));
        }
    }

    @Test
    // Case: directory exist
    void saveFileSuccessful2() {
        try (MockedStatic<Files> utilities = Mockito.mockStatic(Files.class)) {
            // Mock
            utilities.when(() -> Files.exists(any(Path.class))).thenReturn(true);
            utilities.when(() -> Files.copy(any(Path.class), any(OutputStream.class))).thenReturn(1L);

            // Test
            assertDoesNotThrow(() -> filesStorageService.save(multipartFile, "uuid"));
        }
    }
    // ----------------------------------------

    // --------- Load File ----------
    @Test
    void loadFileThrowFileNotExist() {
        FileStorageException thrown = assertThrows(FileStorageException.class, () -> filesStorageService.load("folder", "filename"));
        assertEquals(FileStorageException.ExceptionCodes.FILE_NOT_EXIST.name(), thrown.getMessage());
    }

    @Test
    void loadFileThrowUriInvalid(){
        try (MockedConstruction<UrlResource> mocked = mockConstruction(UrlResource.class, (mock, context) -> {
            when(mock).thenThrow(new FileStorageException(FileStorageException.ExceptionCodes.URI_INVALID));
        })) {
            // Test
            FileStorageException thrown = assertThrows(FileStorageException.class, () -> filesStorageService.load("folder", "filename"));
            assertEquals(FileStorageException.ExceptionCodes.URI_INVALID.name(), thrown.getMessage());
        }
    }

    @Test
    void loadFileSuccessful(){
        try (MockedConstruction<UrlResource> mocked = mockConstruction(UrlResource.class, (mock, context) -> {
            when(mock.exists()).thenReturn(true);
        })) {
            // Test
            assertDoesNotThrow(() -> filesStorageService.load("folder", "filename"));
        }
    }

    // ----------------------------------------



}

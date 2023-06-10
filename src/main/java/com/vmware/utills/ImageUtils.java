package com.vmware.utills;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ImageUtils {
    public static void saveImage(MultipartFile file, String folderPath, String fileName) throws IOException {
        Path filePath = Path.of(folderPath, fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    }
}


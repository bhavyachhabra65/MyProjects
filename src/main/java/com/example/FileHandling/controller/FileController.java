package com.example.FileHandling.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private static final String UPLOAD_DIR = "D:\\uploads/";

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Please select a file to upload.";
        }

        try {
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File targetFile = new File(directory, file.getOriginalFilename());
            file.transferTo(targetFile);

            return "File uploaded successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload the file.";
        }
    }

    @GetMapping("/{fileName}")
    public String readFile(@PathVariable String fileName) {
        try (FileReader reader = new FileReader(UPLOAD_DIR + fileName)) {
            StringBuilder content = new StringBuilder();
            int character;
            while ((character = reader.read()) != -1) {
                content.append((char) character);
            }
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to read the file.";
        }
    }

    @PutMapping("/{fileName}")
    public String updateFile(@PathVariable String fileName, @RequestBody String content) {
        try (FileWriter writer = new FileWriter(UPLOAD_DIR + fileName)) {
            writer.write(content);
            return "File updated successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to update the file.";
        }
    }

    @DeleteMapping("/{fileName}")
    public String deleteFile(@PathVariable String fileName) {
        File file = new File(UPLOAD_DIR + fileName);
        if (file.delete()) {
            return "File deleted successfully.";
        } else {
            return "Failed to delete the file.";
        }
    }
}

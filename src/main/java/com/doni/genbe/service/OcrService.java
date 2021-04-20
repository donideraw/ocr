package com.doni.genbe.service;

import com.doni.genbe.helper.SuccessType;
import com.doni.genbe.model.entity.Document;
import com.doni.genbe.repository.DocumentRepository;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class OcrService {
    private final DocumentRepository repository;
    private final Path newFolder = Paths.get("file/new");
    private final Path finishFolder = Paths.get("file/finish");

    public OcrService(DocumentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Document saveDocument(MultipartFile file) throws Exception {
        if (repository.findByDocName(file.getOriginalFilename()) != null) {
            throw new Exception("file sudah pernah di input");
        }
        Document document = new Document();
        document.setStartingPath(uploadDocument(file, newFolder));
        document.setScanned(false);
        document.setDocName(file.getOriginalFilename());
        return repository.save(document);
    }

    private String uploadDocument(MultipartFile file, Path paths) throws Exception {
        checkFolder(paths);
        Path path = paths;
        String newFilename = file.getOriginalFilename();
        Path filePath = path.resolve(newFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        file.getInputStream().close();
        return filePath.toString();
    }

    private void checkFolder(Path path) {
        File directory = new File(path.toUri());
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    @Transactional
    public void scanDocument() throws Exception {

        List<Document> documentList = repository.findAllScannedFalse();
        if (documentList.isEmpty()) {
            throw new Exception("file tidak ditemukan");
        }

        documentList.forEach(val -> {
            ITesseract image = new Tesseract();
            File theDocument = new File(val.getStartingPath());

                try {
                    val.setResult(image.doOCR(theDocument));
                    val.setScanned(true);
                    val.setSuccess(SuccessType.SUCCEED);
                    val.setFinishingPath(finishFolder + "\\" + theDocument.getName());
                    repository.save(val);
                    Files.copy(Paths.get(val.getStartingPath()), Paths.get("file/finish/" + theDocument.getName()), StandardCopyOption.COPY_ATTRIBUTES);
                    Files.deleteIfExists(Paths.get(val.getStartingPath()));
                } catch (TesseractException | IOException e) {
                    val.setScanned(true);
                    val.setSuccess(SuccessType.FAILED);
                    System.out.println(e);
                }
        });
    }

    public List<Document> getAll() {
        return repository.findAllScannedIs();
    }

    public List<Document> getAllUploaded() {
        return repository.findAllScannedFalse();
    }

    public String fixString(Long id) {
        Document document = repository.findById(id).orElse(null);
        String old = document.getResult();
        String newString = old.replaceAll("\\W+", " ");
        newString = newString.replaceAll("  ", " ");
        newString = newString.replaceAll("   ", " ");
        document.setResult(newString);
        repository.save(document);
        return newString;
    }

    public String getOne(Long id) {
        return repository.findById(id).orElse(null).getResult();
    }

    public Document getDocument(Long id) {
        return repository.findById(id).orElse(null);
    }


}

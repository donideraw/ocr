package com.doni.genbe.service;

import com.doni.genbe.helper.SuccessType;
import com.doni.genbe.model.entity.Document;
import com.doni.genbe.model.entity.FolderPath;
import com.doni.genbe.repository.DocumentRepository;
import com.doni.genbe.repository.FolderPathRepository;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class OcrService {
    private final DocumentRepository repository;
    private final FolderPathRepository folderPathRepository;
    private final Path newFolder = Paths.get("D:\\project\\genbe\\file\\upload\\");
    private final Path finishFolder = Paths.get("D:\\project\\genbe\\file\\finish");

    public OcrService(DocumentRepository repository, FolderPathRepository folderPathRepository) {
        this.repository = repository;
        this.folderPathRepository = folderPathRepository;
    }

    @Transactional
    public Document saveDocument(MultipartFile file) throws Exception {
        if (repository.findByDocName(file.getOriginalFilename()) != null) {
            throw new Exception("file sudah pernah di input");
        }
        Document document = new Document();
        document.setStartingPath(uploadDocument(file, Paths.get(folderPathRepository.findById(1L).orElse(null).getUploadPath())));
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
                    val.setFinishingPath(Paths.get(folderPathRepository.findById(1L).orElse(null).getFinishPath()) + "\\" + theDocument.getName());
                    repository.save(val);
                    Files.copy(Paths.get(val.getStartingPath()), Paths.get(folderPathRepository.findById(1L).orElse(null).getFinishPath() + "\\" + theDocument.getName()), StandardCopyOption.COPY_ATTRIBUTES);
                    Files.deleteIfExists(Paths.get(val.getStartingPath()));
                } catch (TesseractException | IOException e) {
                    val.setScanned(true);
                    val.setSuccess(SuccessType.FAILED);
                    System.out.println(e);
                }
        });
    }

    @Transactional
    public void scanDocumentThisFolder() throws Exception {
        File directoryPath = new File(folderPathRepository.findById(1L).orElse(null).getNewPath());
        File filesList[] = directoryPath.listFiles();
        System.out.println("List of files and directories in the specified directory:");
        for(File file : filesList) {
            Document document = new Document();
            document.setStartingPath(file.getAbsolutePath());
            document.setDocName(file.getName());

            ITesseract image = new Tesseract();
            File theDocument = new File(file.getAbsolutePath());

            try {
                document.setResult(image.doOCR(theDocument));
                document.setScanned(true);
                document.setSuccess(SuccessType.SUCCEED);
                document.setFinishingPath(Paths.get(folderPathRepository.findById(1L).orElse(null).getFinishPath()) + "\\" + theDocument.getName());
                repository.save(document);
                Files.copy(Paths.get(file.getAbsolutePath()), Paths.get(folderPathRepository.findById(1L).orElse(null).getFinishPath() + theDocument.getName()), StandardCopyOption.COPY_ATTRIBUTES);
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            } catch (TesseractException | IOException e) {
                document.setScanned(true);
                document.setSuccess(SuccessType.FAILED);
                System.out.println(e);
            }

        }
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

    public Document setSent(Long id) {
        Document document = repository.findById(id).orElse(null);
        document.setSent(true);
        return repository.save(document);
    }

    public Document getDocument(Long id) {
        return repository.findById(id).orElse(null);
    }

    public FolderPath getPath() {
        return folderPathRepository.findById(1L).orElse(null);
    }

    public FolderPath savePath(FolderPath req) {
        req.setId(1L);
        return folderPathRepository.save(req);
    }


}

package com.doni.genbe.controller.restapi;

import com.doni.genbe.model.entity.FolderPath;
import com.doni.genbe.service.OcrService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class OcrController {

    private final OcrService service;

    public OcrController(OcrService service) {
        this.service = service;
    }

    @GetMapping(value = "/scan")
    public void scanDocument() throws Exception{
        service.scanDocument();
    }

    @GetMapping(value = "/scanFolder")
    public void scanDocumentFolder() throws Exception{
        service.scanDocumentThisFolder();
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadDocument(@RequestParam("file") MultipartFile file) throws Exception {

        return ResponseEntity.ok(service.saveDocument(file));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/path")
    public ResponseEntity<?> getPath() {
        return ResponseEntity.ok(service.getPath());
    }

    @PostMapping("/path")
    public ResponseEntity<?> savePath(@RequestBody FolderPath req) {
        return ResponseEntity.ok(service.savePath(req));
    }

    @GetMapping("/false")
    public ResponseEntity<?> getFalse() {
        return ResponseEntity.ok(service.getAllUploaded());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAll(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @GetMapping("/doc/{id}")
    public ResponseEntity<?> getDocument(@PathVariable Long id) {
        return ResponseEntity.ok(service.getDocument(id));
    }

    @GetMapping("/sent/{id}")
    public ResponseEntity<?> sendData(@PathVariable Long id) {
        return ResponseEntity.ok(service.setSent(id));
    }

    @GetMapping("/fix/{id}")
    public ResponseEntity<?> fix(@PathVariable Long id) {
        return ResponseEntity.ok(service.fixString(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Sukses");
    }

    @GetMapping("test")
    public ResponseEntity<?> test() {
        service.makeApiCallMassive();
        return ResponseEntity.ok("sukses");
    }
}

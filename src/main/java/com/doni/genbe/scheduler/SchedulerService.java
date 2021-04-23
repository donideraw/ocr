package com.doni.genbe.scheduler;

import com.doni.genbe.model.entity.Scheduler;
import com.doni.genbe.repository.SchedulerRepository;
import com.doni.genbe.service.OcrService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class SchedulerService {
    private final OcrService ocrService;
    private final SchedulerRepository repository;

    public SchedulerService(OcrService ocrService, SchedulerRepository repository) {
        this.ocrService = ocrService;
        this.repository = repository;
    }

    @Async
    @Scheduled(cron = "0 0,15,30,45  * * * *") // run at 15 every minute
    public void scanRoutine() throws Exception {
        if (!repository.findValueById("Scan")) {
            // set scheduler is running = true
            Scheduler scheduler = repository.findById("Scan").orElse(null);
            scheduler.setRunning(true);
            repository.save(scheduler);

            // running scanning
            ocrService.scanDocumentThisFolder();
            System.out.println("running scheduler " + LocalTime.now());

            // set scheduler is running = false
            scheduler.setRunning(false);
            repository.save(scheduler);
        }
    }

    @Async
    @Scheduled(cron = "0 3,18,33,48  * * * *") // run at 15 every minute
    public void sendRoutine() throws Exception {
        if (!repository.findValueById("Send")) {
            // set scheduler is running = true
            Scheduler scheduler = repository.findById("Send").orElse(null);
            scheduler.setRunning(true);
            repository.save(scheduler);

            // running scanning
            ocrService.makeApiCallMassive();
            System.out.println("running scheduler " + LocalTime.now());

            // set scheduler is running = false
            scheduler.setRunning(false);
            repository.save(scheduler);
        }
    }
}

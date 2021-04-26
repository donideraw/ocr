package com.doni.genbe.scheduler;

import com.doni.genbe.model.entity.Scheduler;
import com.doni.genbe.repository.SchedulerRepository;
import com.doni.genbe.repository.SettingRepository;
import com.doni.genbe.service.OcrService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class SchedulerService {
    private final OcrService ocrService;
    private final SchedulerRepository repository;
    private final SettingRepository settingRepository;

    public SchedulerService(OcrService ocrService, SchedulerRepository repository, SettingRepository settingRepository) {
        this.ocrService = ocrService;
        this.repository = repository;
        this.settingRepository = settingRepository;
    }

    @Async
    @Scheduled(cron = "0 * * * * *") // run at 15 every minute
    public void scanRoutine() throws Exception {
        Scheduler scheduler = repository.findById("Scan").orElse(null);
        // cek waktu dan status scheduler
        if (!scheduler.isRunning() && LocalTime.now().isAfter(scheduler.getRunningAt())) {
            // set scheduler is running = true
            scheduler.setRunning(true);
            repository.save(scheduler);

            // running scanning
            ocrService.scanDocumentThisFolder();
            System.out.println("running scheduler scan at " + LocalTime.now());

            // set scheduler is running = false
            scheduler.setRunning(false);
            scheduler.setRunningAt(LocalTime.now().plusMinutes(Long.valueOf(settingRepository.findValue("SCAN_TIME").getValue())));
            repository.save(scheduler);
        }
    }

    @Async
    @Scheduled(cron = "0 * * * * *")
    public void sendRoutine() throws Exception {
        Scheduler scheduler = repository.findById("Send").orElse(null);
        // cek waktu dan status scheduler
        if (!scheduler.isRunning() && LocalTime.now().isAfter(scheduler.getRunningAt())) {
            // set scheduler is running = true
            scheduler.setRunning(true);
            repository.save(scheduler);

            // running scanning
            ocrService.makeApiCallMassive();
            System.out.println("running scheduler send at " + LocalTime.now());

            // set scheduler is running = false
            scheduler.setRunning(false);
            scheduler.setRunningAt(LocalTime.now().plusMinutes(Long.valueOf(settingRepository.findValue("SEND_TIME").getValue())));
            repository.save(scheduler);
        }
    }
}

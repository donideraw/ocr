package com.doni.genbe.scheduler;

import com.doni.genbe.service.OcrService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class SchedulerService {
    private final OcrService ocrService;

    public SchedulerService(OcrService ocrService) {
        this.ocrService = ocrService;
    }

//    @Async
//    @Scheduled(cron = "0 0,5,10,15,20,25,30,35,40,45,50,55  * * * *") // run at every minute
//    public void scanRoutine() throws Exception {
////        ocrService.scanDocumentThisFolder();
//        System.out.println("running scheduler " + LocalTime.now());
//    }
}

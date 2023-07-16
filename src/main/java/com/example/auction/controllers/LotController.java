package com.example.auction.controllers;

import com.example.auction.dto.CreateLot;
import com.example.auction.dto.FullLot;
import com.example.auction.dto.LotDTO;
import com.example.auction.model.Bid;
import com.example.auction.model.Lot;
import com.example.auction.model.Status;
import com.example.auction.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lots")
public class LotController {
    private final LotService lotService;

    @Autowired
    public LotController(LotService lotService) {
        this.lotService = lotService;
    }

    @GetMapping("/{id}/first")
    public ResponseEntity<Lot> getFirstBidder(@PathVariable("id") Long lotId) {
        Lot firstBidder = lotService.getLotById(lotId);
        if (firstBidder != null) {
            return ResponseEntity.ok(firstBidder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/frequent")
    public ResponseEntity<Bid> getMostFrequentBidder(@PathVariable("id") Long lotId) {
        Bid mostFrequentBidder = lotService.getMostFrequentBidder(lotId);
        if (mostFrequentBidder != null) {
            return ResponseEntity.ok(mostFrequentBidder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<Void> startBidding(@PathVariable("id") Long lotId) {
        boolean started = lotService.startBidding(lotId);
        if (started) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/stop")
    public ResponseEntity<Void> stopBidding(@PathVariable("id") Long lotId) {
        boolean stopped = lotService.stopBidding(lotId);
        if (stopped) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/{lotId}/bids")
    public ResponseEntity<Void> createBid(@PathVariable("lotId") Long lotId, @RequestBody @Valid CreateBid createBid) {
        boolean created = lotService.createBid(lotId, createBid.getBidderName(), createBid.getAmount());
        if (created) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    public ResponseEntity<List<LotDTO>> getAllLotsByStatusFilterAndPageNumber(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "page", defaultValue = "0") int page) {
        List<LotDTO> lots = lotService.getAllLotsByStatusFilterAndPageNumber(Status.valueOf(status), page);
        return ResponseEntity.ok(lots);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportAllLotsToCSVFile() {
        byte[] csvFile = lotService.exportAllLotsToCSVFile();
        if (csvFile != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=lots.csv")
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(csvFile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullLot> getFullInformation(@PathVariable("id") Long lotId) {
        FullLot fullLot = lotService.getFullInformation(lotId);
        if (fullLot != null) {
            return ResponseEntity.ok(fullLot);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Lot> createLot(@RequestBody @Valid CreateLot createLot) {
        Lot lot = lotService.createLot(createLot);
        return ResponseEntity.ok(lot);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLot(@PathVariable("id") Long lotId) {
        boolean deleted = lotService.deleteLot(lotId);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

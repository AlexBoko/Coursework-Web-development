package com.example.auction.controllers;

import com.example.auction.dto.BidDTO;
import com.example.auction.dto.CreateLotDTO;
import com.example.auction.dto.FullLotDTO;
import com.example.auction.dto.LotDTO;
import com.example.auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lots")
public class LotController {

    private final AuctionService auctionService;

    @Autowired
    public LotController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping
    public ResponseEntity<LotDTO> createLot(@RequestBody CreateLotDTO createLotDTO) {
        LotDTO createdLot = auctionService.createLot(createLotDTO);
        return new ResponseEntity<>(createdLot, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullLotDTO> getLot(@PathVariable Long id) {
        FullLotDTO lot = auctionService.getFullLot(id);
        if (lot == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lot, HttpStatus.OK);
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<String> startLot(@PathVariable Long id) {
        boolean success = auctionService.startLot(id);
        if (success) {
            return new ResponseEntity<>("Lot started successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to start lot", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{id}/stop")
    public ResponseEntity<String> stopLot(@PathVariable Long id) {
        boolean success = auctionService.stopLot(id);
        if (success) {
            return new ResponseEntity<>("Lot stopped successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to stop lot", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{id}/bid")
    public ResponseEntity<String> placeBid(@PathVariable Long id, @RequestBody BidDTO bidDTO) {
        boolean success = auctionService.placeBid(id, bidDTO);
        if (success) {
            return new ResponseEntity<>("Bid placed successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to place bid", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}/first")
    public ResponseEntity<BidDTO> getFirstBidder(@PathVariable Long id) {
        BidDTO firstBidder = auctionService.getFirstBidder(id);
        if (firstBidder == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(firstBidder, HttpStatus.OK);
    }

    @GetMapping("/{id}/frequent")
    public ResponseEntity<BidDTO> getMostFrequentBidder(@PathVariable Long id) {
        BidDTO frequentBidder = auctionService.getMostFrequentBidder(id);
        if (frequentBidder == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(frequentBidder, HttpStatus.OK);
    }
}

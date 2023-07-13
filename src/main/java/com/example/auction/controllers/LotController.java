package com.example.auction.controllers;

import com.example.auction.dto.BidDTO;
import com.example.auction.dto.CreateLot;
import com.example.auction.dto.FullLot;
import com.example.auction.dto.LotDTO;
import com.example.auction.model.Lot;
import com.example.auction.model.Status;
import com.example.auction.service.BidService;
import com.example.auction.service.LotService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lots")
public class LotController {
    private final BidService bidService;

    private final LotService lotService;

    public LotController(BidService bidService, LotService lotService) {
        this.bidService = bidService;
        this.lotService = lotService;
    }


    @GetMapping("/{id}/first")
    public BidDTO getInformationAboutTheFirstBidder(@PathVariable Integer id) {
        return bidService.getInformationAboutTheFirstBidder(id);
    }

    @GetMapping("/{id}/frequent")
    public BidDTO returnsTheNameOfThePersonWhoBetOnThisLotTheMostNumberOfTimes(@PathVariable Integer id) {
        return bidService.returnsTheNameOfThePersonWhoBetOnThisLotTheMostNumberOfTimes(id);
    }

    @GetMapping("/{id}")
    public FullLot getFullInformation(@PathVariable Integer id) {
        return lotService.getFullInformation(id);
    }

    @PostMapping("/{id}/start")
    public void startBidding(@PathVariable Integer id) {
        lotService.startBidding(id);
    }

    @PostMapping("/{id}/stop")
    public void stopBidding(@PathVariable Integer id) {
        lotService.stopBidding(id);
    }

    @PostMapping("/{id}/bid")
    public void placeBet(@PathVariable Integer id, @RequestBody @Valid BidDTO bidDTO) {
        bidService.placeBet(id, bidDTO);
    }


    @PostMapping
    public Lot createNewLot(@RequestBody @Valid CreateLot createLot) {
        return lotService.createLot(createLot);
    }

    @GetMapping
    public List<LotDTO> getAllLotsByStatusFilterAndPageNumber(@RequestParam(value = "status", required = false) Status status,
                                                              @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return lotService.getAllLotsByStatusFilterAndPageNumber(status, page);
    }


    @DeleteMapping("/{id}")
    public void deleteLot(@PathVariable("id") Long id) {
        lotService.deleteLot(id);
    }

    @GetMapping("/status/{status}")
    public List<Lot> getLotsByStatus(@PathVariable("status") String status, @RequestParam("limit") int limit) {
        return lotService.getLotsByStatus(status, limit);
    }
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportAllLotsToCSVFile() {
        byte [] result = lotService.exportAllLotsToCSVFile();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "text/cvs")
                .body(result);
    }
}

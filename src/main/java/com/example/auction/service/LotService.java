package com.example.auction.service;

import com.example.auction.dto.CreateLot;
import com.example.auction.dto.FullLot;
import com.example.auction.dto.LotDTO;
import com.example.auction.model.Bid;
import com.example.auction.model.Lot;
import com.example.auction.model.Status;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

public interface LotService {
    Lot createLot(@Valid CreateLot lotRequest);

    List<Lot> findLotsByStatusAndPage(String status, int page);

    FullLot getFullLotById(Integer lotId);

    FullLot getFullLotById(Long lotId);

    void startBidding(Integer lotId);

    void stopBidding(Integer lotId);

    boolean startBidding(Long lotId);

    boolean stopBidding(Long lotId);

    List<Lot> getAllLots();

    Lot updateLot(Long id, Lot lot);

    boolean deleteLot(Long id);

    List<Lot> getLotsByStatus(String status, int limit);

    List<LotDTO> getAllLotsByStatusFilterAndPageNumber(Status status, Integer page);

    byte[] exportAllLotsToCSVFile();

    FullLot getFullInformation(Integer id);

    FullLot getFullInformation(Long lotId);

    Bid getMostFrequentBidder(Long lotId);

    Lot getLotById(Long lotId);

    boolean createBid(Long lotId, String bidderName, BigDecimal amount);
}


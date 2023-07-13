package com.example.auction.service;

import com.example.auction.dto.CreateLot;
import com.example.auction.dto.FullLot;
import com.example.auction.dto.LotDTO;
import com.example.auction.model.Lot;
import com.example.auction.model.Status;

import javax.validation.Valid;
import java.util.List;

public interface LotService {
    Lot createLot(@Valid CreateLot lotRequest);

    List<Lot> findLotsByStatusAndPage(String status, int page);

    FullLot getFullLotById(Integer lotId);

    FullLot getFullLotById(Long lotId);

    void startBidding(Integer lotId);

    void stopBidding(Integer lotId);

    void startBidding(Long lotId);

    void stopBidding(Long lotId);

    List<Lot> getAllLots();

    Lot getLotById(Long id);

    Lot updateLot(Long id, Lot lot);

    void deleteLot(Long id);

    List<Lot> getLotsByStatus(String status, int limit);

    List<LotDTO> getAllLotsByStatusFilterAndPageNumber(Status status, Integer page);

    byte[] exportAllLotsToCSVFile();

    FullLot getFullInformation(Integer id);
}

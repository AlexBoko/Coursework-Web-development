package com.example.auction.service;

import com.example.auction.dto.BidDTO;
import com.example.auction.dto.CreateLotDTO;
import com.example.auction.dto.FullLotDTO;
import com.example.auction.dto.LotDTO;

public interface AuctionService {
    LotDTO createLot(CreateLotDTO createLotDTO);

    FullLotDTO getFullLot(Long id);

    boolean startLot(Long id);

    boolean stopLot(Long id);

    boolean placeBid(Long lotId, BidDTO bidDTO);

    BidDTO getFirstBidder(Long lotId);

    BidDTO getMostFrequentBidder(Long lotId);
}

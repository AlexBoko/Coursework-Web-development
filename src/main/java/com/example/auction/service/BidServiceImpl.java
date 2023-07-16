package com.example.auction.service;

import com.example.auction.exception.InvalidStatusException;
import com.example.auction.exception.NotFoundException;
import com.example.auction.model.Bid;
import com.example.auction.model.Lot;
import com.example.auction.model.Status;
import com.example.auction.repository.BidRepository;
import com.example.auction.repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class BidServiceImpl extends BidService {

    private final LotRepository lotRepository;
    private final BidRepository bidRepository;

    @Autowired
    public BidServiceImpl(LotRepository lotRepository, BidRepository bidRepository) {
        super(lotRepository, bidRepository);
        this.lotRepository = lotRepository;
        this.bidRepository = bidRepository;
    }

    @Override
    public void createBid(Integer lotId, String bidderName) {
        Lot lot = lotRepository.findById(Long.valueOf(lotId))
                .orElseThrow(() -> new NotFoundException("Lot not found"));

        if (lot.getStatus() != Status.STARTED) {
            throw new InvalidStatusException("Bidding is not allowed for this lot");
        }

        Bid bid = new Bid();
        bid.setBidderName(bidderName);
        bid.setBidDate(LocalDateTime.now());
        bid.setLot(lot);

        bidRepository.save(bid);
    }
}


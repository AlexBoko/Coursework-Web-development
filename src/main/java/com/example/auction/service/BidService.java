package com.example.auction.service;

import com.example.auction.dto.BidDTO;
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BidService {
    private final LotRepository lotRepository;
    private final BidRepository bidRepository;

    @Autowired
    public BidService(LotRepository lotRepository, BidRepository bidRepository) {
        this.lotRepository = lotRepository;
        this.bidRepository = bidRepository;
    }

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

    public BidDTO getInformationAboutTheFirstBidder(Integer id) {
        Lot lot = lotRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NotFoundException("Lot not found"));

        List<Bid> bids = bidRepository.findByLotIdOrderByBidDateAsc(lot.getId());

        if (bids.isEmpty()) {
            throw new NotFoundException("No bids found for this lot");
        }

        Bid firstBid = bids.get(0);

        return new BidDTO(firstBid.getBidderName(), firstBid.getBidDate(), firstBid.getAmount());
    }

    public BidDTO returnsTheNameOfThePersonWhoBetOnThisLotTheMostNumberOfTimes(Integer id) {
        Lot lot = lotRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NotFoundException("Lot not found"));

        List<Bid> bids = bidRepository.findByLotIdOrderByBidDateDesc(lot.getId());

        if (bids.isEmpty()) {
            throw new NotFoundException("No bids found for this lot");
        }

        List<String> bidderNames = bids.stream()
                .collect(Collectors.groupingBy(Bid::getBidderName, Collectors.counting()))
                .entrySet().stream()
                .sorted(Comparator.comparingLong(entry -> -entry.getValue()))
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());

        if (bidderNames.isEmpty()) {
            throw new NotFoundException("No bids found for this lot");
        }

        String mostFrequentBidderName = bidderNames.get(0);

        // Get the latest bid by the most frequent bidder
        Bid mostFrequentBid = bids.stream()
                .filter(bid -> bid.getBidderName().equals(mostFrequentBidderName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Bid not found for the most frequent bidder"));

        return new BidDTO(mostFrequentBid.getBidderName(), mostFrequentBid.getBidDate(), mostFrequentBid.getAmount());
    }

    public void placeBet(Integer id, BidDTO bidDTO) {
        Lot lot = lotRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NotFoundException("Lot not found"));

        if (lot.getStatus() != Status.STARTED) {
            throw new InvalidStatusException("Bidding is not allowed for this lot");
        }

        BigDecimal currentPrice = lot.getStartPrice().add(
                BigDecimal.valueOf(lot.getBids().size()).multiply(lot.getBidPrice())
        );


        BigDecimal bidAmount = new BigDecimal((BigInteger) bidDTO.getAmount());
        if (bidAmount.compareTo(currentPrice) <= 0) {
            throw new IllegalArgumentException("Bid amount must be greater than the current price");
        }

        Bid bid = new Bid();
        bid.setBidderName(bidDTO.getBidderName());
        bid.setBidDate(bidDTO.getBidDate());
        bid.setAmount(bidAmount);
        bid.setLot(lot);

        bidRepository.save(bid);
    }
}

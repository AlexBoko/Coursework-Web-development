package com.example.auction.service;

import com.example.auction.dto.BidDTO;
import com.example.auction.dto.CreateLotDTO;
import com.example.auction.dto.FullLotDTO;
import com.example.auction.dto.LotDTO;
import com.example.auction.exception.NotFoundException;
import com.example.auction.model.Bid;
import com.example.auction.model.Lot;
import com.example.auction.model.LotStatus;
import com.example.auction.repository.BidRepository;
import com.example.auction.repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuctionServiceImpl implements AuctionService {
    private final LotRepository lotRepository;
    private final BidRepository bidRepository;

    @Autowired
    public AuctionServiceImpl(LotRepository lotRepository, BidRepository bidRepository) {
        this.lotRepository = lotRepository;
        this.bidRepository = bidRepository;
    }

    @Override
    public LotDTO createLot(CreateLotDTO createLotDTO) {
        Lot lot = new Lot(createLotDTO.getTitle(), createLotDTO.getDescription(), createLotDTO.getStartPrice());
        Lot savedLot = lotRepository.save(lot);
        return mapToLotDTO(savedLot);
    }

    @Override
    public FullLotDTO getFullLot(Long id) {
        Lot lot = lotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lot not found"));
        List<Bid> bids = bidRepository.findByLotIdOrderByBidDateDesc(id);
        FullLotDTO fullLotDTO = mapToFullLotDTO(lot);
        fullLotDTO.setBids(mapToBidDTOList(bids));
        return fullLotDTO;
    }

    @Override
    public boolean startLot(Long id) {
        Lot lot = lotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lot not found"));
        if (lot.getStatus() == LotStatus.STARTED) {
            return false;
        }
        lot.setStatus(LotStatus.STARTED);
        lotRepository.save(lot);
        return true;
    }

    @Override
    public boolean stopLot(Long id) {
        Lot lot = lotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lot not found"));
        if (lot.getStatus() == LotStatus.STOPPED) {
            return false;
        }
        lot.setStatus(LotStatus.STOPPED);
        lotRepository.save(lot);
        return true;
    }

    @Override
    public boolean placeBid(Long lotId, BidDTO bidDTO) {
        Lot lot = lotRepository.findById(lotId)
                .orElseThrow(() -> new NotFoundException("Lot not found"));
        if (lot.getStatus() != LotStatus.STARTED) {
            return false;
        }
        Bid bid = new Bid(bidDTO.getBidderName(), LocalDateTime.now(), lot, (BigDecimal) bidDTO.getAmount());
        bidRepository.save(bid);
        return true;
    }

    @Override
    public BidDTO getFirstBidder(Long lotId) {
        Bid firstBid = bidRepository.findFirstByLotIdOrderByBidDateAsc(lotId)
                .orElseThrow(() -> new NotFoundException("No bids found for the lot"));
        return mapToBidDTO(firstBid);
    }

    @Override
    public BidDTO getMostFrequentBidder(Long lotId) {
        String mostFrequentBidderName = bidRepository.findMostFrequentBidderNameByLotId(lotId);
        Optional<Bid> mostFrequentBid = bidRepository.findFirstByLotIdAndBidderName(lotId, mostFrequentBidderName);
        Bid bid = mostFrequentBid.orElseThrow(() -> new NotFoundException("No bids found for the lot"));
        return mapToBidDTO(bid);
    }

    private LotDTO mapToLotDTO(Lot lot) {
        return new LotDTO(lot.getId(), lot.getTitle(), lot.getDescription(), lot.getStartPrice(), lot.getStatus());
    }


    private FullLotDTO mapToFullLotDTO(Lot lot) {
        FullLotDTO fullLotDTO = new FullLotDTO(lot.getId(), lot.getTitle(), lot.getDescription(),
                BigDecimal.valueOf(lot.getStartPrice()), lot.getStatus(), null, BigDecimal.ZERO);
        if (lot.getStatus() == LotStatus.STARTED) {
            BigDecimal sumAmount = BigDecimal.valueOf(bidRepository.sumAmountByLotId(lot.getId()));
            BigDecimal currentPrice = BigDecimal.valueOf(lot.getStartPrice()).add(sumAmount);
            fullLotDTO.setCurrentPrice(currentPrice);
        }
        return fullLotDTO;
    }


    private List<BidDTO> mapToBidDTOList(List<Bid> bids) {
        return bids.stream()
                .map(this::mapToBidDTO)
                .collect(Collectors.toList());
    }

    private BidDTO mapToBidDTO(Bid bid) {
        return new BidDTO(bid.getBidderName(), bid.getBidDate(), bid.getAmount());
    }
}

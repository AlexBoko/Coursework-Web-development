package com.example.auction.service;

import com.example.auction.dto.BidDTO;
import com.example.auction.dto.CreateLot;
import com.example.auction.dto.FullLot;
import com.example.auction.dto.LotDTO;
import com.example.auction.exception.NotFoundException;
import com.example.auction.model.Bid;
import com.example.auction.model.Lot;
import com.example.auction.model.Status;
import com.example.auction.repository.BidRepository;
import com.example.auction.repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public  class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;
    private final BidRepository bidRepository;

    @Autowired
    public LotServiceImpl(LotRepository lotRepository, BidRepository bidRepository) {
        this.lotRepository = lotRepository;
        this.bidRepository = bidRepository;
    }

    @Override
    public Lot createLot(@Valid CreateLot lotRequest) {
        Lot lot = new Lot();
        lot.setTitle(lotRequest.getTitle());
        lot.setDescription(lotRequest.getDescription());
        lot.setStartPrice(BigDecimal.valueOf(lotRequest.getStartPrice()));
        lot.setBidPrice(BigDecimal.valueOf(lotRequest.getBidPrice()));
        lot.setStatus(Status.CREATED);
        return lotRepository.save(lot);
    }

    @Override
    public List<Lot> findLotsByStatusAndPage(String status, int page) {
        int pageSize = 10;
        int offset = page * pageSize;
        return lotRepository.findByStatus(status, PageRequest.of(offset, pageSize));
    }

    @Override
    public FullLot getFullLotById(Integer lotId) {
        return null;
    }

    @Override
    public FullLot getFullLotById(Long lotId) {
        Lot lot = lotRepository.findById(lotId)
                .orElseThrow(() -> new NotFoundException("Lot not found"));
        FullLot fullLot = new FullLot(
                lot.getId(),
                lot.getTitle(),
                lot.getDescription(),
                lot.getStartPrice(),
                lot.getStatus(),
                new ArrayList<>(),
                BigDecimal.ZERO
        );

        List<Bid> bids = bidRepository.findByLotIdOrderByBidDateDesc(lot.getId());
        List<BidDTO> bidDTOs = new ArrayList<>();

        for (Bid bid : bids) {
            BidDTO bidDTO = new BidDTO(
                    bid.getBidderName(),
                    bid.getBidDate(),
                    bid.getAmount()
            );
            bidDTOs.add(bidDTO);
        }

        if (!bidDTOs.isEmpty()) {
            BidDTO lastBid = bidDTOs.get(0);
            BigDecimal currentPrice = lot.getStartPrice().add(
                    BigDecimal.valueOf(bidDTOs.size()).multiply(lot.getBidPrice())
            );
            fullLot.setCurrentPrice(currentPrice);
            fullLot.setLastBid(lastBid);
        } else {
            fullLot.setCurrentPrice(lot.getStartPrice());
        }

        fullLot.setBids(bidDTOs);
        return fullLot;
    }

    @Override
    public void startBidding(Integer lotId) {

    }

    @Override
    public void stopBidding(Integer lotId) {

    }

    @Override
    public void startBidding(Long lotId) {
        Lot lot = getLotById(lotId);
        lot.setStatus(Status.STARTED);
        lotRepository.save(lot);
    }

    @Override
    public void stopBidding(Long lotId) {
        Lot lot = getLotById(lotId);
        lot.setStatus(Status.STOPPED);
        lotRepository.save(lot);
    }

    @Override
    public List<Lot> getAllLots() {
        return lotRepository.findAll();
    }

    @Override
    public Lot updateLot(Long id, Lot lot) {
        Lot existingLot = getLotById(id);
        existingLot.setTitle(lot.getTitle());
        existingLot.setDescription(lot.getDescription());
        existingLot.setStartPrice(lot.getStartPrice());
        existingLot.setBidPrice(lot.getBidPrice());
        return lotRepository.save(existingLot);
    }

    @Override
    public void deleteLot(Long id) {
        Lot lot = getLotById(id);
        lotRepository.delete(lot);
    }

    @Override
    public List<Lot> getLotsByStatus(String status, int limit) {
        return lotRepository.findByStatus(status, PageRequest.of(0, limit));
    }

    @Override
    public List<LotDTO> getAllLotsByStatusFilterAndPageNumber(Status status, Integer page) {
        return null;
    }

    @Override
    public byte[] exportAllLotsToCSVFile() {
        return new byte[0];
    }

    @Override
    public FullLot getFullInformation(Integer id) {
        return null;
    }

    public Lot getLotById(Long lotId) {
        Optional<Lot> lotOptional = lotRepository.findById(lotId);
        return lotOptional.orElseThrow(() -> new NotFoundException("Lot not found"));
    }
}

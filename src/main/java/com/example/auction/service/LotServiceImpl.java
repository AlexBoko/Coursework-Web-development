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
import com.example.auction.util.CsvExporter;
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
public class LotServiceImpl implements LotService {

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
        lot.setStartPrice(lotRequest.getStartPrice());
        lot.setBidPrice(lotRequest.getBidPrice());
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
    public boolean startBidding(Long lotId) {
        Lot lot = getLotById(lotId);
        lot.setStatus(Status.STARTED);
        lotRepository.save(lot);
        return true;
    }

    @Override
    public boolean stopBidding(Long lotId) {
        Lot lot = getLotById(lotId);
        lot.setStatus(Status.STOPPED);
        lotRepository.save(lot);
        return true;
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
    public boolean deleteLot(Long id) {
        Lot lot = getLotById(id);
        lotRepository.delete(lot);
        return true;
    }

    @Override
    public List<Lot> getLotsByStatus(String status, int limit) {
        return lotRepository.findByStatus(status, PageRequest.of(0, limit));
    }

    @Override
    public List<LotDTO> getAllLotsByStatusFilterAndPageNumber(Status status, Integer page) {
        List<Lot> lots = findLotsByStatusAndPage(status.toString(), page);
        List<LotDTO> lotDTOs = new ArrayList<>();

        for (Lot lot : lots) {
            LotDTO lotDTO = new LotDTO(
                    lot.getId(),
                    lot.getTitle(),
                    lot.getStatus()
            );
            lotDTOs.add(lotDTO);
        }

        return lotDTOs;
    }

    @Override
    public byte[] exportAllLotsToCSVFile() {
        List<Lot> lots = getAllLots();
        String csvData = CsvExporter.exportLotsToCsv(lots);
        return csvData.getBytes();
    }

    @Override
    public FullLot getFullInformation(Integer id) {
        Long lotId = id.longValue();
        return getFullLotById(lotId);
    }

    @Override
    public FullLot getFullInformation(Long lotId) {
        return null;
    }

    @Override
    public Bid getMostFrequentBidder(Long lotId) {
        return null;
    }

    public Lot getLotById(Long lotId) {
        Optional<Lot> lotOptional = lotRepository.findById(lotId);
        return lotOptional.orElseThrow(() -> new NotFoundException("Lot not found"));
    }

    @Override
    public boolean createBid(Long lotId, String bidderName, BigDecimal amount) {
        return false;
    }
}

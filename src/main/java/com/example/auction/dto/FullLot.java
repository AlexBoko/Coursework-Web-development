package com.example.auction.dto;

import com.example.auction.model.Status;

import java.math.BigDecimal;
import java.util.List;

public class FullLot extends LotDTO {
    private List<BidDTO> bids;
    private BigDecimal currentPrice;
    private BidDTO lastBid;

    public FullLot(Long id, String title, String description, BigDecimal startPrice, Status status,
                   List<BidDTO> bids, BigDecimal currentPrice) {
        super(Long.valueOf(id), title, status);
        this.bids = bids;
        this.currentPrice = currentPrice;
    }

    public List<BidDTO> getBids() {
        return bids;
    }

    public void setBids(List<BidDTO> bids) {
        this.bids = bids;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setLastBid(BidDTO lastBid) {
        this.lastBid = lastBid;
    }

}


package com.example.auction.dto;

import com.example.auction.model.LotStatus;

import java.math.BigDecimal;
import java.util.List;

public class FullLotDTO extends LotDTO {
    private final List<BidDTO> bids;

    private BigDecimal currentPrice;

    public FullLotDTO(Long id, String title, String description, BigDecimal startPrice, LotStatus status, List<BidDTO> bids, BigDecimal currentPrice) {
        super(id, title, description, startPrice, status);
        this.bids = bids;
        this.currentPrice = currentPrice;
    }

    public void setBids(List<BidDTO> mapToBidDTOList) {

    }

    public List<BidDTO> getBids() {
        return null;
    }

}

package com.example.auction.dto;

import java.time.LocalDateTime;

public class BidDTO {

    private String bidderName;
    private LocalDateTime bidDate;

    public BidDTO(String bidderName, LocalDateTime bidDate, Object amount) {
    }

    public String getBidderName() {
        return bidderName;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    public LocalDateTime getBidDate() {
        return bidDate;
    }

    public void setBidDate(LocalDateTime bidDate) {
        this.bidDate = bidDate;
    }

    public Object getAmount() {
        return null;
    }
}

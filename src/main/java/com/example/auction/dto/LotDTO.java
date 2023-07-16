package com.example.auction.dto;

import com.example.auction.model.Status;

import java.math.BigDecimal;

public class LotDTO {

    private Long id;
    private String title;
    private String description;
    private BigDecimal startPrice;
    private BigDecimal bidPrice;
    private Status status;
    private String lastBidder;
    private BigDecimal currentPrice;

    public LotDTO(Long id, String title, Status status) {
    }

    public LotDTO(Long id, String title, String description, BigDecimal startPrice, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startPrice = startPrice;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(BigDecimal bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getLastBidder() {
        return lastBidder;
    }

    public void setLastBidder(String lastBidder) {
        this.lastBidder = lastBidder;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }
}

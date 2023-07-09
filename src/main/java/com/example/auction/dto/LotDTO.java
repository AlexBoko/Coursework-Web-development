package com.example.auction.dto;

import com.example.auction.model.LotStatus;

import java.math.BigDecimal;

public class LotDTO {

    private long id;
    private String title;
    private String description;
    private int startPrice;
    private int bidPrice;
    private String status;
    private String lastBidder;
    private BigDecimal currentPrice;

    public LotDTO(Long id, String title, String description, int startPrice, LotStatus status) {
    }

    public LotDTO(Long id, String title, String description, BigDecimal startPrice, LotStatus status) {
    }

    // Конструкторы, геттеры и сеттеры

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public int getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(int bidPrice) {
        this.bidPrice = bidPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public void setStatus(LotStatus status) {
    }
}

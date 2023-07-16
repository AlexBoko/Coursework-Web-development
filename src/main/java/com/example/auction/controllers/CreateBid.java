package com.example.auction.controllers;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CreateBid {
    @NotBlank(message = "Bidder name is required")
    private String bidderName;

    @NotNull(message = "Bid amount is required")
    private BigDecimal amount;

    public String getBidderName() {
        return bidderName;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

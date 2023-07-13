package com.example.auction.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateLot {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 64, message = "Title must be between 3 and 64 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 4096, message = "Description must be between 1 and 4096 characters")
    private String description;

    @NotNull
    @Size(min = 1, max = 100)
    private Integer startPrice;

    @NotNull
    @Size(min = 1, max = 100)
    private Integer bidPrice;

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

    public String getCategory() {
        return null;
    }
}

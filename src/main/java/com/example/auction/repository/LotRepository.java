package com.example.auction.repository;

import com.example.auction.model.Lot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LotRepository extends JpaRepository<Lot, Long> {
    List<Lot> findByBidId(Long bidId);
}

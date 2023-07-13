package com.example.auction.repository;

import com.example.auction.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByLotIdOrderByBidDateDesc(Long lotId);

    Optional<Bid> findFirstByLotIdOrderByBidDateAsc(Long lotId);

    @Query("SELECT SUM(b.amount) FROM Bid b WHERE b.lot.id = :lotId")
    long sumAmountByLotId(@Param("lotId") Long lotId);

    @Query("SELECT b.bidderName FROM Bid b WHERE b.lot.id = :lotId GROUP BY b.bidderName ORDER BY COUNT(b.bidderName) DESC")
    String findMostFrequentBidderNameByLotId(@Param("lotId") Long lotId);

    Optional<Bid> findFirstByLotIdAndBidderName(Long lotId, String bidderName);

    List<Bid> findByLotId(Long lotId);

    List<Bid> findByLotIdOrderByBidDateDesc(Integer id);
}

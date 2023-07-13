package com.example.auction.util;

import com.example.auction.dto.BidDTO;
import com.example.auction.dto.FullLot;
import com.opencsv.CSVWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class CSVUtil {
    private static final char DEFAULT_SEPARATOR = ',';

    public static byte[] convertFullLotDTOToCSV(FullLot fullLotDTO) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(outputStream))) {

            String[] header = {"Lot ID", "Title", "Description", "Start Price", "Status", "Current Price"};
            csvWriter.writeNext(header);

            String[] row = {String.valueOf(fullLotDTO.getId()), fullLotDTO.getTitle(), fullLotDTO.getDescription(),
                    String.valueOf(fullLotDTO.getStartPrice()), fullLotDTO.getStatus().toString(),
                    String.valueOf(fullLotDTO.getCurrentPrice())};
            csvWriter.writeNext(row);

            List<BidDTO> bids = fullLotDTO.getBids();
            if (bids != null && !bids.isEmpty()) {
                csvWriter.writeNext(new String[]{"Bids:"});
                String[] bidHeader = {"Bidder Name", "Bid Date", "Amount"};
                csvWriter.writeNext(bidHeader);

                for (BidDTO bid : bids) {
                    String[] bidRow = {bid.getBidderName(), bid.getBidDate().toString(), String.valueOf(bid.getAmount())};
                    csvWriter.writeNext(bidRow);
                }
            }

            csvWriter.flush();
            return outputStream.toByteArray();

        } catch (IOException e) {
            // Handle exception
            e.printStackTrace();
            return null;
        }
    }
}

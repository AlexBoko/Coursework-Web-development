package com.example.auction.util;

import com.example.auction.model.Lot;

import java.util.List;
import java.util.StringJoiner;

public class CsvExporter {

    private static final String CSV_SEPARATOR = ",";

    public static String exportLotsToCsv(List<Lot> lots) {
        StringJoiner joiner = new StringJoiner("\n");

        // Добавляем заголовок CSV
        joiner.add("id,title,status");

        // Добавляем каждый лот в CSV
        for (Lot lot : lots) {
            String csvRow = formatLotToCsvRow(lot);
            joiner.add(csvRow);
        }

        return joiner.toString();
    }

    private static String formatLotToCsvRow(Lot lot) {
        StringJoiner joiner = new StringJoiner(CSV_SEPARATOR);
        joiner.add(String.valueOf(lot.getId()));
        joiner.add(lot.getTitle());
        joiner.add(lot.getStatus().toString());
        return joiner.toString();
    }
}

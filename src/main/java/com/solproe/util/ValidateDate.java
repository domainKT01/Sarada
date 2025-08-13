package com.solproe.util;

import java.time.LocalDate;

public class ValidateDate {

    public boolean compareActualDate(String newDate) {
        LocalDate localDate = LocalDate.now().plusDays(1);
        System.out.println(newDate + "-----");
        LocalDate date = LocalDate.parse(newDate);
        return localDate.isEqual(date);
    }
}

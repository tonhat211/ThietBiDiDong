package test;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class test2 {
    public static void main(String[] args) {
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String priceString = numberFormat.format(10000000);
        System.out.println(priceString);

        double price = 10000000;
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedPrice = currencyFormatter.format(price);
        System.out.println(formattedPrice);
    }
}

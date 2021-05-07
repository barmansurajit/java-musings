package com.cognizant.triton.async;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

public class ShopClient {

    private static final Logger LOGGER = Logger.getLogger(ShopClient.class.getName());

    public static void main(String[] args) {
        ShopClient shopClient = new ShopClient();
        shopClient.whatsThePrice(4);
        shopClient.whatsThePrice(3);
        shopClient.whatsThePrice(2);
        shopClient.whatsThePrice(1);
    }

    private List<Shop> shops = Arrays.asList(
            new Shop("Shop1"),
            new Shop("Shop2"),
            new Shop("Shop3"),
            new Shop("Shop4"),
            new Shop("Shop5"),
            new Shop("Shop6"),
            new Shop("Shop7"),
            new Shop("Shop8"),
            new Shop("Shop9"),
            new Shop("Shop10"),
            new Shop("Shop11"),
            new Shop("Shop12"),
            new Shop("Shop13"),
            new Shop("Shop14"),
            new Shop("Shop15"),
            new Shop("Shop16"),
            new Shop("Shop17"),
            new Shop("Shop18"),
            new Shop("Shop19"),
            new Shop("Shop20"),
            new Shop("Shop21"),
            new Shop("Shop22"),
            new Shop("Shop23"),
            new Shop("Shop24"),
            new Shop("Shop25"),
            new Shop("Shop26"),
            new Shop("Shop27"),
            new Shop("Shop28"),
            new Shop("Shop29"),
            new Shop("Shop30"),
            new Shop("Shop31"),
            new Shop("Shop32"),
            new Shop("Shop33"),
            new Shop("Shop34"),
            new Shop("Shop35"),
            new Shop("Shop36"),
            new Shop("Shop37"),
            new Shop("Shop38"),
            new Shop("Shop39"),
            new Shop("Shop40"),
            new Shop("Shop41"),
            new Shop("Shop42"),
            new Shop("Shop43"),
            new Shop("Shop44"),
            new Shop("Shop45"),
            new Shop("Shop46"),
            new Shop("Shop47"),
            new Shop("Shop48"),
            new Shop("Shop49"),
            new Shop("Shop50")
    );




    private void whatsThePrice(int ind) {
        long start = System.nanoTime();

        switch (ind) {
            case 1:
                LOGGER.log(Level.INFO, findPrices("IPhone X").toString());
                break;
            case 2:
                LOGGER.log(Level.INFO, findPricesParallel("IPhone X").toString());
                break;
            case 3:
                LOGGER.log(Level.INFO, findPricesAsync1("IPhone X").toString());
                break;
            case 4:
                LOGGER.log(Level.INFO, findPricesAsync2("IPhone X").toString());
                break;
        }

        long duration = (System.nanoTime() - start) / 1_000_000;
        LOGGER.log(Level.INFO, "Done in " + duration + " msecs");

    }

    private List<String> findPrices(String product) {
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(toList());
    }

    private List<String> findPricesParallel(String product) {
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(toList());
    }

    private List<String> findPricesAsync1(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))))
                .collect(toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }

    private List<String> findPricesAsync2(String product) {
        final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });

        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)), executor))
                .collect(toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }
}

class Shop {
    private static final Logger LOGGER = Logger.getLogger(Shop.class.getName());

    private final String name;

    public Shop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getPrice(String product) {
        return calculatePrice(product);
    }


    private double calculatePrice(String product) {
        delay();
        return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
    }

    private static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE,e.getLocalizedMessage());
        }
    }
}
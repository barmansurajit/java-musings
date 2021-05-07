package com.cognizant.triton.bestpractices;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class ProductService {
    private ProductRepo productRepo;

    public List<Product> getFrequentOrderedProducts(List<Order> orders) {
        List<Long> hiddenProductIds = productRepo.getHiddenProductIds();
        Predicate<Product> productIsNotHidden = product -> !hiddenProductIds.contains(product.getId());

        Stream<Product> frequentProducts = this.getProductCountsOverTheLastYear(orders)
                .entrySet()
                .stream()
                .filter(productIntegerEntry -> productIntegerEntry.getValue() >= 10)
                .map(Map.Entry::getKey);

        return frequentProducts.filter(Product::isNotDeleted)
                .filter(productIsNotHidden)
                .collect(toList());
    }

    private Map<Product, Integer> getProductCountsOverTheLastYear(List<Order> orders) {
        Predicate<Order> inThePreviousYear = order -> order.getCreationDate()
                .isAfter(LocalDate.now().minusYears(1));
        return orders.stream()
                .filter(inThePreviousYear)
                .flatMap(order -> order.getOrderLines().stream())
                .collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItemCount)));
    }
}

class Product {
    private Long id;
    private boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isNotDeleted() {
        return !deleted;
    }
}

class Order {
    private Long id;
    private List<OrderLine> orderLines;
    private LocalDate creationDate;

    public Order(Long id, List<OrderLine> orderLines, LocalDate creationDate) {
        this.id = id;
        this.orderLines = orderLines;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}

class OrderLine {
    private Product product;
    private int itemCount;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
}

interface ProductRepo {
    List<Long> getHiddenProductIds();
}
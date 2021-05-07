package com.cognizant.triton.bestpractices;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class DiscountService {
    public String getDiscountLine(Customer customer) {
        return customer.getMemberCard()
                .flatMap(this::getApplicableDiscount)
                .map(aDouble -> "Discount: " + aDouble + "%")
                .orElse("");
    }

    private Optional<Double> getApplicableDiscount(MemberCard memberCard) {
        if (memberCard.getFidelityPoints() >= 100) {
            return of(5.00);
        }

        if (memberCard.getFidelityPoints() >= 50) {
            return of(2.00);
        }

        return empty();
    }
}

class Customer {
    private MemberCard memberCard;

    public Customer(MemberCard memberCard) {
        this.memberCard = memberCard;
    }

    public Optional<MemberCard> getMemberCard() {
        return Optional.ofNullable(memberCard);
    }
}

class MemberCard {
    private int fidelityPoints;

    public int getFidelityPoints() {
        return fidelityPoints;
    }

    public void setFidelityPoints(int fidelityPoints) {
        this.fidelityPoints = fidelityPoints;
    }
}
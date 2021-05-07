package com.cognizant.triton.ddd.aggregates;

public abstract class AggregateRoot<ID> {
    private final ID id;

    public AggregateRoot(ID id) {
        this.id = id;
    }

    public ID getId(){
        return id;
    }
}
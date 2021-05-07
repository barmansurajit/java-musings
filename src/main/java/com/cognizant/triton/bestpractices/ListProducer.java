package com.cognizant.triton.bestpractices;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ListProducer {

    public void producer(List<A> as, List<B> bs){
        List<C> cList = as.stream()
                .flatMap(a -> bs.stream()
                        .filter(b -> b.getLoadNumber().equalsIgnoreCase(a.getLoadNumber()))
                        .map(b -> new C(a.getStopNumber(), b.getOperationalPlanNumber(), b.getOperationalPlanId())))
                .collect(toList());

        cList.forEach(System.out::println);
    }

    private class C {
        private String stopNumber;
        private String operationalPlanNumber;
        private String operationalPlanId;

        public C(String stopNumber, String operationalPlanNumber, String operationalPlanId) {
            this.stopNumber = stopNumber;
            this.operationalPlanNumber = operationalPlanNumber;
            this.operationalPlanId = operationalPlanId;
        }

        public String getStopNumber() {
            return stopNumber;
        }

        public void setStopNumber(String stopNumber) {
            this.stopNumber = stopNumber;
        }

        public String getOperationalPlanNumber() {
            return operationalPlanNumber;
        }

        public void setOperationalPlanNumber(String operationalPlanNumber) {
            this.operationalPlanNumber = operationalPlanNumber;
        }

        public String getOperationalPlanId() {
            return operationalPlanId;
        }

        public void setOperationalPlanId(String operationalPlanId) {
            this.operationalPlanId = operationalPlanId;
        }

        @Override
        public String toString() {
            return "C{" +
                    "stopNumber='" + stopNumber + '\'' +
                    ", operationalPlanNumber='" + operationalPlanNumber + '\'' +
                    ", operationalPlanId='" + operationalPlanId + '\'' +
                    '}';
        }
    }

    private class A {
        private String loadNumber;
        private String stopNumber;

        public A(String loadNumber, String stopNumber) {
            this.loadNumber = loadNumber;
            this.stopNumber = stopNumber;
        }

        public String getLoadNumber() {
            return loadNumber;
        }

        public void setLoadNumber(String loadNumber) {
            this.loadNumber = loadNumber;
        }

        public String getStopNumber() {
            return stopNumber;
        }

        public void setStopNumber(String stopNumber) {
            this.stopNumber = stopNumber;
        }

        @Override
        public String toString() {
            return "A{" +
                    "loadNumber='" + loadNumber + '\'' +
                    ", stopNumber='" + stopNumber + '\'' +
                    '}';
        }
    }

    private class B {
        private String loadNumber;
        private String operationalPlanNumber;
        private String operationalPlanId;

        public B(String loadNumber, String operationalPlanNumber, String operationalPlanId) {
            this.loadNumber = loadNumber;
            this.operationalPlanNumber = operationalPlanNumber;
            this.operationalPlanId = operationalPlanId;
        }

        public String getLoadNumber() {
            return loadNumber;
        }

        public void setLoadNumber(String loadNumber) {
            this.loadNumber = loadNumber;
        }

        public String getOperationalPlanNumber() {
            return operationalPlanNumber;
        }

        public void setOperationalPlanNumber(String operationalPlanNumber) {
            this.operationalPlanNumber = operationalPlanNumber;
        }

        public String getOperationalPlanId() {
            return operationalPlanId;
        }

        public void setOperationalPlanId(String operationalPlanId) {
            this.operationalPlanId = operationalPlanId;
        }

        @Override
        public String toString() {
            return "B{" +
                    "loadNumber='" + loadNumber + '\'' +
                    ", operationalPlanNumber='" + operationalPlanNumber + '\'' +
                    ", operationalPlanId='" + operationalPlanId + '\'' +
                    '}';
        }
    }
}


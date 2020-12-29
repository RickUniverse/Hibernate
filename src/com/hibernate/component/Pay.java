package com.hibernate.component;

/**
 * @author lijichen
 * @date 2020/11/8 - 17:01
 */
public class Pay {
    private double yearPay;
    private Worker worker;

    public Pay(double yearPay, Worker worker) {
        this.yearPay = yearPay;
        this.worker = worker;
    }

    public Pay() {
    }

    public Worker getWorker() {
        return worker;
    }

    public double getYearPay() {
        return yearPay;
    }

    public void setYearPay(double yearPay) {
        this.yearPay = yearPay;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @Override
    public String toString() {
        return "Pay{" +
                "yearPay=" + yearPay +
                ", worker=" + worker +
                '}';
    }
}

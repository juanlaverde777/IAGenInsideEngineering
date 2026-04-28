package eci.edu.byteProgramming.ejercicio.paper.util;

import java.util.ArrayList;
import java.util.List;

public class ECIPayment implements PaymentSubject {
    private List<PaymentObserver> observers;

    public ECIPayment() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void addObserver(PaymentObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(PaymentObserver observer) {
        observers.remove(observer);
    }

    public boolean processPayment(PaymentFactory factory, double amount, String customerId,
                                  String description, String customerName, String customerEmail, String productId) {

        System.out.println("🚀 ECI Payments: Starting payment process...");
        System.out.println("Customer: " + customerName + " (" + customerEmail + ")");
        System.out.println("Amount: $" + amount);
        System.out.println("Description: " + description);
        System.out.println("----------------------------------------");

        if (factory == null) {
            System.out.println("Payment factory is null!");
            return false;
        }

        PaymentMethod payment = factory.createPaymentMethod(amount, customerId, description);

        if (payment == null) {
            System.out.println("Could not create payment method!");
            return false;
        }

        PaymentValidator validator = factory.createValidator();

        if (!validator.validate(payment)) {
            System.out.println("Payment failed!");
            notifyPaymentFailed(payment, customerEmail);
            return false;
        }

        boolean success = payment.processPayment();

        if (success) {
            System.out.println("Payment processed successfully!");
            notifyPaymentSuccess(payment, customerName, customerEmail, productId);
        } else {
            System.out.println("Payment failed!");
            notifyPaymentFailed(payment, customerEmail);
        }

        return success;
    }

    private void notifyPaymentSuccess(PaymentMethod payment, String customerName,
                                      String customerEmail, String productId) {
        for (PaymentObserver observer : observers) {
            observer.onPaymentSuccess(payment, customerName, customerEmail, productId);
        }
    }

    private void notifyPaymentFailed(PaymentMethod payment, String customerEmail) {
        for (PaymentObserver observer : observers) {
            observer.onPaymentFailed(payment, customerEmail);
        }
    }
}
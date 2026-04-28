package eci.edu.byteProgramming.ejercicio.paper.util;

public class Notification implements PaymentObserver {
    private static final String COMPANY_NAME = "ECI Payments";
    private static final String FROM_EMAIL = "noreply@eciPayments.com";

    @Override
    public void onPaymentSuccess(PaymentMethod payment, String customerName,
                                  String customerEmail, String productId) {
        sendConfirmationEmail(customerEmail, customerName, payment);
    }

    @Override
    public void onPaymentFailed(PaymentMethod payment, String customerEmail) {
        sendFailureNotification(payment, customerEmail);
    }

    public void sendConfirmationEmail(String customerEmail, String customerName, PaymentMethod payment) {
        if (customerEmail == null || customerName == null || payment == null) {
            System.out.println("Notification: Cannot send email, missing data.");
            return;
        }

        System.out.println("Notification: Sending confirmation email");
        System.out.println("   To: " + customerEmail);
        System.out.println("   From: " + FROM_EMAIL);
        System.out.println("   Subject: Payment Confirmation - " + payment.getTransactionId());
        System.out.println("   Dear " + customerName + ",");
        System.out.println("   Your payment of $" + payment.getAmount() +
                " has been processed successfully via " + payment.getPaymentMethod());
        System.out.println("   Transaction ID: " + payment.getTransactionId());
        System.out.println("   Thank you for your purchase!");
    }

    public void sendFailureNotification(PaymentMethod payment, String customerEmail) {
        if (payment == null || customerEmail == null) {
            System.out.println("Notification: Cannot send failure notification, missing data.");
            return;
        }

        System.out.println("Notification: Sending failure notification");
        System.out.println("   To: " + customerEmail);
        System.out.println("   Subject: Payment Failed - " + payment.getTransactionId());
        System.out.println("   Your payment could not be processed. Please try again.");
    }

    // Getters
    public String getCompanyName() { return COMPANY_NAME; }
    public String getFromEmail() { return FROM_EMAIL; }
}
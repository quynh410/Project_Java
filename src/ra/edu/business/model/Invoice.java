package ra.edu.business.model;

import ra.edu.utils.IApp;

import java.util.Date;
import java.util.Scanner;

public class Invoice implements IApp {
    private int invoiceId;
    private int customerId;
    private String  customerName;
    private Date createAt;
    private Double totalAmount;
    private String status;

    public Invoice() {
    }

    public Invoice(int invoiceId, int customerId, String customerName, Date createAt, Double totalAmount, String status) {
        this.invoiceId = invoiceId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.createAt = createAt;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", createAt=" + createAt +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public void inputData(Scanner sc) {

    }
}


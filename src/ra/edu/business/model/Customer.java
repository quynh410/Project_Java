package ra.edu.business.model;

import ra.edu.utils.IApp;

import java.util.Scanner;

public class Customer implements IApp {
    private int cusId;
    private String cusName;
    private String cusPhone;
    private String cusEmail;
    private String cusAddress;
    private Gender cusGender;

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public Customer() {
    }

    public Customer(int cusId, String cusName, String cusPhone, String cusEmail, String cusAddress, Gender cusGender) {
        this.cusId = cusId;
        this.cusName = cusName;
        this.cusPhone = cusPhone;
        this.cusEmail = cusEmail;
        this.cusAddress = cusAddress;
        this.cusGender = cusGender;
    }

    public int getCusId() {
        return cusId;
    }

    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public Gender getCusGender() {
        return cusGender;
    }

    public void setCusGender(Gender cusGender) {
        this.cusGender = cusGender;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cusId=" + cusId +
                ", cusName='" + cusName + '\'' +
                ", cusPhone='" + cusPhone + '\'' +
                ", cusEmail='" + cusEmail + '\'' +
                ", cusAddress='" + cusAddress + '\'' +
                ", cusGender=" + cusGender +
                '}';
    }

    @Override
    public void inputData(Scanner sc) {

    }
}
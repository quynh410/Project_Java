package ra.edu.business.model;

import ra.edu.utils.IApp;
import ra.edu.validate.ProductValidator;

import java.util.Scanner;

public class Product implements IApp {
    private int proId;
    private String proName;
    private String proBrand;
    private Double proPrice;
    private int stock;
    private boolean status = true; // Added status field

    public Product() {
    }

    public Product(int proId, String proName, String proBrand, Double proPrice, int stock) {
        this.proId = proId;
        this.proName = proName;
        this.proBrand = proBrand;
        this.proPrice = proPrice;
        this.stock = stock;
        this.status = true;
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProBrand() {
        return proBrand;
    }

    public void setProBrand(String proBrand) {
        this.proBrand = proBrand;
    }

    public Double getProPrice() {
        return proPrice;
    }

    public void setProPrice(Double proPrice) {
        this.proPrice = proPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public byte getStatusAsByte() {
        return (byte) (status ? 1 : 0);
    }

    @Override
    public String toString() {
        return "Product{" +
                "proId=" + proId +
                ", proName='" + proName + '\'' +
                ", proBrand='" + proBrand + '\'' +
                ", proPrice=" + proPrice +
                ", stock=" + stock +
                ", status=" + status +
                '}';
    }

    @Override
    public void inputData(Scanner sc) {
        System.out.print("Tên sản phẩm: ");
        this.proName = ProductValidator.getValidProductName(sc);

        System.out.print("Thương hiệu: ");
        this.proBrand = ProductValidator.getValidBrand(sc);

        System.out.print("Nhập vào giá: ");
        this.proPrice = ProductValidator.getValidPrice(sc);

        System.out.print("Số lượng tồn kho: ");
        this.stock = ProductValidator.getValidStock(sc);
    }
}
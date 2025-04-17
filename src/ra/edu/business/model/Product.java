package ra.edu.business.model;

import ra.edu.utils.IApp;

import java.util.Scanner;

public class Product implements IApp {
    private int proId;
    private String proName;
    private String proBrand;
    private Double proPrice;
    private int stock;

    public Product() {
    }

    public Product(int proId, String proName, String proBrand, Double proPrice, int stock) {
        this.proId = proId;
        this.proName = proName;
        this.proBrand = proBrand;
        this.proPrice = proPrice;
        this.stock = stock;
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

    @Override
    public String toString() {
        return "Product{" +
                "proId=" + proId +
                ", proName='" + proName + '\'' +
                ", proBrand='" + proBrand + '\'' +
                ", proPrice=" + proPrice +
                ", stock=" + stock +
                '}';
    }

    @Override
    public void inputData(Scanner sc) {
        System.out.print("Tên sản phẩm: ");
        this.proName = sc.nextLine();

        System.out.print("Thương hiệu: ");
        this.proBrand = sc.nextLine();

        System.out.print("Nhập vào giá: ");
        try {
            this.proPrice = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Giá không hợp lệ, đặt giá = 0");
            this.proPrice = 0.0;
        }

        System.out.print("Số lượng tồn kho: ");
        try {
            this.stock = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Số lượng không hợp lệ, đặt số lượng = 0");
            this.stock = 0;
        }
    }
}

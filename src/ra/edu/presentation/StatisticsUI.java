package ra.edu.presentation;

import ra.edu.business.model.Invoice;
import ra.edu.business.service.invoice.InvoiceService;
import ra.edu.business.service.invoice.InvoiceServiceImp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class StatisticsUI {
    private final InvoiceService invoiceService = new InvoiceServiceImp();
    private final Scanner scanner = new Scanner(System.in);

    public void menuStatistics() {
        int choice;
        do {
            System.out.println("\u001B[34m========== THỐNG KÊ DOANH THU ==========\u001B[0m");
            System.out.println("|1. Thống kê theo ngày                 |");
            System.out.println("|2. Thống kê theo tháng                |");
            System.out.println("|3. Thống kê theo năm                  |");
            System.out.println("|0. Quay lại                           |");
            System.out.println("=======================================");
            System.out.print("Lựa chọn của bạn: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    statisticsByDay();
                    break;
                case 2:
                    statisticsByMonth();
                    break;
                case 3:
                    statisticsByYear();
                    break;
                case 0:
                    System.out.println("Quay lại menu chính...");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }
        } while (choice != 0);
    }

    private void statisticsByDay() {
        System.out.println("\u001B[34m========= THỐNG KÊ DOANH THU THEO NGÀY =========\u001B[0m");
        System.out.print("Nhập ngày (dd/MM/yyyy): ");
        String dateStr = scanner.nextLine();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            Date startDate = dateFormat.parse(dateStr);

            // Set endDate to the end of the day
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            Date endDate = calendar.getTime();

            List<Invoice> invoices = invoiceService.getInvoicesByDateRange(startDate, endDate);
            displayStatistics(invoices, "ngày " + dateStr);
        } catch (ParseException e) {
            System.err.println("Định dạng ngày không hợp lệ. Vui lòng sử dụng định dạng dd/MM/yyyy");
        }
    }

    private void statisticsByMonth() {
        System.out.println("\u001B[34m========= THỐNG KÊ DOANH THU THEO THÁNG =========\u001B[0m");
        System.out.print("Nhập tháng (MM/yyyy): ");
        String monthStr = scanner.nextLine();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
            dateFormat.setLenient(false);
            Date startDate = dateFormat.parse(monthStr);

            // Set endDate to the end of the month
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            Date endDate = calendar.getTime();

            List<Invoice> invoices = invoiceService.getInvoicesByDateRange(startDate, endDate);
            displayStatistics(invoices, "tháng " + monthStr);
        } catch (ParseException e) {
            System.err.println("Định dạng tháng không hợp lệ. Vui lòng sử dụng định dạng MM/yyyy");
        }
    }

    private void statisticsByYear() {
        System.out.println("\u001B[34m========= THỐNG KÊ DOANH THU THEO NĂM =========\u001B[0m");
        System.out.print("Nhập năm (yyyy): ");
        String yearStr = scanner.nextLine();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
            dateFormat.setLenient(false);
            Date startDate = dateFormat.parse(yearStr);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.set(Calendar.MONTH, Calendar.DECEMBER);
            calendar.set(Calendar.DAY_OF_MONTH, 31);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            Date endDate = calendar.getTime();

            List<Invoice> invoices = invoiceService.getInvoicesByDateRange(startDate, endDate);
            displayStatistics(invoices, "năm " + yearStr);
        } catch (ParseException e) {
            System.err.println("Định dạng năm không hợp lệ. Vui lòng sử dụng định dạng yyyy");
        }
    }

    private void displayStatistics(List<Invoice> invoices, String timeFrame) {
        if (invoices == null || invoices.isEmpty()) {
            System.out.println("Không có hóa đơn nào trong " + timeFrame + ".");
            return;
        }

        double totalRevenue = invoices.stream()
                .filter(invoice -> !"cancelled".equalsIgnoreCase(invoice.getStatus()) &&
                        !"failed".equalsIgnoreCase(invoice.getStatus()))
                .mapToDouble(Invoice::getTotalAmount)
                .sum();

        System.out.println("\u001B[36m========= KẾT QUẢ THỐNG KÊ " + timeFrame.toUpperCase() + " =========\u001B[0m");
        System.out.printf("\u001B[32mTổng số hóa đơn: %d\u001B[0m\n", invoices.size());
        System.out.printf("\u001B[32mTổng doanh thu: %.2f VND\u001B[0m\n", totalRevenue);
        System.out.println("\u001B[36m=============================================\u001B[0m");

        System.out.println("\u001B[36m=============================== DANH SÁCH HÓA ĐƠN ================== ===============\u001B[0m");
        System.out.printf("\u001B[36m| %-5s | %-20s | %-20s | %-12s | %-10s |\u001B[0m%n",
                "ID", "Khách hàng", "Ngày tạo", "Tổng tiền", "Trạng thái");
        System.out.println("\u001B[36m===================================================================================\u001B[0m");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (Invoice invoice : invoices) {
            String dateString = (invoice.getCreateAt() != null) ? dateFormat.format(invoice.getCreateAt()) : "N/A";
            String statusColor;
            switch (invoice.getStatus().toLowerCase()) {
                case "paid":
                    statusColor = "\u001B[32m";
                    break;
                case "pending":
                    statusColor = "\u001B[33m";
                    break;
                case "cancelled":
                case "failed":
                    statusColor = "\u001B[31m";
                    break;
                case "confirmed":
                case "processing":
                    statusColor = "\u001B[36m";
                    break;
                case "delivered":
                    statusColor = "\u001B[34m";
                    break;
                default:
                    statusColor = "\u001B[37m";
                    break;
            }

            System.out.printf("| %-5d | %-20s | %-20s | %-12.2f | %s%-10s\u001B[0m |\n",
                    invoice.getInvoiceId(),
                    invoice.getCustomerName(),
                    dateString,
                    invoice.getTotalAmount(),
                    statusColor,
                    invoice.getStatus());
        }
        System.out.println("\u001B[36m===================================================================================\u001B[0m");
    }
}
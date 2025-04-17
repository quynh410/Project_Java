package ra.edu.business.service.admin;

import ra.edu.business.dao.admin.AdminDAO;
import ra.edu.business.dao.admin.AdminDAOImp;
import ra.edu.business.model.Admin;

public class AdminService {
    private AdminDAO adminDAO;
    private static Admin currentAdmin = null;

    public AdminService() {
        this.adminDAO = new AdminDAOImp();
    }

    public boolean login(String username, String password) {
        Admin admin = adminDAO.login(username, password);

        if (admin != null) {
            if (!admin.isStatus()) {
                System.out.println("Tài khoản hiện không hoạt động");
                return false;
            }
            currentAdmin = admin;
            return true;
        }

        return false;
    }

    public void logout() {
        currentAdmin = null;
        adminDAO.logout();
    }

    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }
}
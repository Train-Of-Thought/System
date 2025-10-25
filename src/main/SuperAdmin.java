package main;

import config.config;
import java.util.*;

public class SuperAdmin {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";

    // viewUsers [SuperAdmin]
    private void viewUsers(config config) {
        String query = "SELECT * FROM tbl_users";
        String[] headers = {
            ANSI_CYAN + "ID" + ANSI_RESET,
            ANSI_CYAN + "First Name" + ANSI_RESET,
            ANSI_CYAN + "Last Name" + ANSI_RESET,
            ANSI_CYAN + "Email" + ANSI_RESET,
            ANSI_CYAN + "Type" + ANSI_RESET,
            ANSI_CYAN + "Status" + ANSI_RESET
        };
        String[] columns = {"u_id", "u_fname", "u_lname", "u_email", "u_type", "u_status"};
        config.viewRecords(query, headers, columns);
    }

    // promoteToAdmin [SuperAdmin]
    private void promoteToAdmin(config config, Scanner scan) {
        viewUsers(config);
        System.out.print("Enter User ID to Promote to Admin: ");
        int id = scan.nextInt();
        String sql = "UPDATE tbl_users SET u_type = ? WHERE u_id = ?";
        config.updateRecord(sql, "Admin", id);
        System.out.println(ANSI_GREEN + "            User promoted to Admin." + ANSI_RESET);
    }

    // demoteToUser [SuperAdmin]
    private void demoteToUser(config config, Scanner scan) {
        viewUsers(config);
        System.out.print("Enter Admin ID to Demote to User: ");
        int id = scan.nextInt();
        String sql = "UPDATE tbl_users SET u_type = ? WHERE u_id = ?";
        config.updateRecord(sql, "User", id);
        System.out.println(ANSI_GREEN + "        Admin demoted to User." + ANSI_RESET);
    }

    // deleteUser [SuperAdmin]
    private void deleteUser(config config, Scanner scan) {
        viewUsers(config);
        System.out.print("Enter User ID to Delete: ");
        int id = scan.nextInt();
        String sql = "DELETE FROM tbl_users WHERE u_id = ?";
        config.updateRecord(sql, id);
        System.out.println(ANSI_GREEN + "            User deleted." + ANSI_RESET);
    }

    // viewLostItems [SuperAdmin]
    private void viewLostItems(config config) {
        String query = "SELECT * FROM tbl_items";
        String[] headers = {
            ANSI_CYAN + "ID" + ANSI_RESET,
            ANSI_CYAN + "Name" + ANSI_RESET,
            ANSI_CYAN + "Description" + ANSI_RESET,
            ANSI_CYAN + "Location" + ANSI_RESET,
            ANSI_CYAN + "Date" + ANSI_RESET,
            ANSI_CYAN + "Status" + ANSI_RESET
        };
        String[] columns = {"li_id", "li_name", "li_description", "li_location", "li_date", "li_status"};
        config.viewRecords(query, headers, columns);
    }

    public void superAdmin() {
        Scanner scan = new Scanner(System.in);
        config config = new config();
        int choice;

        do {
            System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "            ___SUPER ADMIN MENU___" + ANSI_RESET);
            System.out.println("            1. View All Users");
            System.out.println("            2. Promote User to Admin");
            System.out.println("            3. Demote Admin to User");
            System.out.println("            4. Delete Any User");
            System.out.println("            5. View All Lost Items");
            System.out.println("            6. Logout");
            System.out.print("Enter: ");
            choice = scan.nextInt();

            switch (choice) {
                case 1:
                    System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                    viewUsers(config);
                    break;
                case 2:
                    System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                    promoteToAdmin(config, scan);
                    break;
                case 3:
                    System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                    demoteToUser(config, scan);
                    break;
                case 4:
                    System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                    deleteUser(config, scan);
                    break;
                case 5:
                    System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                    viewLostItems(config);
                    break;
                case 6:
                    System.out.println(ANSI_BLUE + "            Logging out..." + ANSI_RESET);
                    break;
                default:
                    System.out.println(ANSI_RED + "            Invalid choice." + ANSI_RESET);
            }

        } while (choice != 6);
    }
}

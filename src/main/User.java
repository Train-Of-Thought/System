package main;

import config.config;
import java.util.*;

public class User {
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    //viewLostItems [Admin/USER]
    public static void viewLostItems(config config) {
        String query = "SELECT * FROM tbl_items WHERE li_status != 'Pending'";
        String[] headers = {"ID", "Item Name", "Description", "Status"};
        String[] columns = {"li_id", "li_name", "li_description", "li_status"};
        config.viewRecords(query, headers, columns);
    }

    //Announcements [Admin/USER]
    public static void announcements(config config) {
        String query = "SELECT * FROM tbl_announcements";
        String[] headers = {"ID", "Title", "Message", "Date"};
        String[] columns = {"a_id", "a_title", "a_message", "a_date"};
        config.viewRecords(query, headers, columns);
    }

    //addLostItem [USER]
    public static void addLostItem(config config, Scanner scan, String u_email) {
        String qry = "SELECT u_id FROM tbl_users WHERE u_email = ?";
        List<Map<String, Object>> result = config.fetchRecords(qry, u_email);

        if (!result.isEmpty()) {
            int userId = Integer.parseInt(result.get(0).get("u_id").toString());
            System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
            System.out.println("User ID: " + userId);
        }
        System.out.print("Enter Item Name: ");
        String name = scan.next();
        System.out.print("Enter Item Description: ");
        scan.nextLine();
        String desc = scan.nextLine();
        System.out.print("Enter Location: ");
        String loc = scan.nextLine();
        System.out.print("Enter Date Lost (YYYY-MM-DD): ");
        String date = scan.next();
        String sql = "INSERT INTO tbl_items (li_name, li_description, li_location, li_date, u_email, li_status) VALUES (?, ?, ?, ?, ?, ?)";
        config.addRecord(sql, name, desc, loc, date, u_email, "Pending");
        System.out.println(ANSI_GREEN + "            Lost item request added and pending approval." + ANSI_RESET);
    }

    //giveFeedback [USER]
    public static void giveFeedback(config config, Scanner scan, String u_email) {
        int choice;
        do {
            System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
            System.out.print("Enter your feedback: ");
            scan.nextLine();
            String message = scan.nextLine();
            String sql = "INSERT INTO tbl_feedbacks (f_user, f_message, f_date) VALUES (?, ?, CURRENT_TIMESTAMP)";
            config.addRecord(sql, u_email, message);
            System.out.println(ANSI_GREEN + "            --Thank you for your feedback!--" + ANSI_RESET);
            System.out.println("            1. Continue");
            System.out.println("            2. Back");
            System.out.print("Enter: ");
            choice = scan.nextInt();
        } while (choice == 1);
    }
    
    // reportIssue [USER]
    public static void reportIssue(config config, Scanner scan, String u_email) {
    int choice;
    do {
        System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
        System.out.print("Describe your issue: ");
        scan.nextLine();
        String message = scan.nextLine();
        String sql = "INSERT INTO tbl_issues (i_user, i_message, i_date) VALUES (?, ?, CURRENT_TIMESTAMP)";
        config.addRecord(sql, u_email, message);
        System.out.println(ANSI_GREEN + "            --Your issue has been submitted.--" + ANSI_RESET);
        System.out.println("            1. Report another issue");
        System.out.println("            2. Back");
        System.out.print("Enter: ");
        choice = scan.nextInt();
    } while (choice == 1);
}

    public void user(String em, String fname, String lname) {
        Scanner scan = new Scanner(System.in);
        config config = new config();
        int userChoice;

        do {
            System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
            System.out.println("......Welcome!"+ANSI_PURPLE+" " + lname + ", "+ fname +" "+ANSI_RESET+ "......");
            System.out.println("......What would you like to do today?......");
            System.out.println(" ");
            System.out.println("            ___USER MENU___");
            System.out.println("            1. Announcements");
            System.out.println("            2. Add Lost Item");
            System.out.println("            3. List of Lost Items");
            System.out.println("            4. Give Feedback");
            System.out.println("            5. Report Issues");
            System.out.println("            6. Logout");
            System.out.print("Enter: ");
            userChoice = scan.nextInt();

            switch (userChoice) {
                case 1:
                    System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                    announcements(config);
                    break;

                case 2:
                    System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                    addLostItem(config, scan, em);
                    break;

                case 3:
                    System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                    viewLostItems(config);
                    break;

                case 4:
                    giveFeedback(config, scan, em);
                    break;

                case 5:
                    reportIssue(config, scan, em);
                    break;

                case 6:
                    System.out.println(ANSI_BLUE + "            Logging out..." + ANSI_RESET);
                    break;

                default:
                    System.out.println(ANSI_RED + "            Invalid Option!" + ANSI_RESET);
            }

        } while (userChoice != 6);
    }
}

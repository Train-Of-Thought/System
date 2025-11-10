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
    public static void viewLostItems(config config, Scanner scan) {
        // Step 1: Display summary list of lost items
        String query = "SELECT li_id, li_name, li_status FROM tbl_items WHERE li_status != 'Pending'";
        String[] headers = {"ID", "Item Name", "Status"};
        String[] columns = {"li_id", "li_name", "li_status"};
        config.viewRecords(query, headers, columns);

        // Step 2: Prompt user for ID to view more details
        System.out.print("\nEnter the ID of the lost item to view details (0 to go back): ");
        int id = scan.nextInt();

        if (id != 0) {
            // Step 3: Fetch and display item details
            String detailQuery = "SELECT * FROM tbl_items WHERE li_id = ?";
            List<Map<String, Object>> result = config.fetchRecords(detailQuery, id);

            if (!result.isEmpty()) {
                Map<String, Object> item = result.get(0);
                System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                System.out.println(ANSI_PURPLE + "Item Name: " + ANSI_RESET + item.get("li_name"));
                System.out.println(ANSI_PURPLE + "Description: " + ANSI_RESET + item.get("li_description"));
                System.out.println(ANSI_PURPLE + "Location: " + ANSI_RESET + item.get("li_location"));
                System.out.println(ANSI_PURPLE + "Date Lost: " + ANSI_RESET + item.get("li_date"));
                System.out.println(ANSI_PURPLE + "Status: " + ANSI_RESET + item.get("li_status"));
                System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Item not found!" + ANSI_RESET);
            }
        }
    }

    //Announcements [Admin/USER]
    public static void announcements(config config, Scanner scan) {
        // Step 1: Display list of announcements (ID, Title, Date)
        String query = "SELECT a_id, a_title, a_date FROM tbl_announcements";
        String[] headers = {"ID", "Title", "Date"};
        String[] columns = {"a_id", "a_title", "a_date"};
        config.viewRecords(query, headers, columns);

        // Step 2: Prompt user to enter ID to view details
        System.out.print("\nEnter the ID of the announcement to view details (0 to go back): ");
        int id = scan.nextInt();

        if (id != 0) {
            // Step 3: Fetch and display announcement details
            String detailQuery = "SELECT * FROM tbl_announcements WHERE a_id = ?";
            List<Map<String, Object>> result = config.fetchRecords(detailQuery, id);

            if (!result.isEmpty()) {
                Map<String, Object> ann = result.get(0);
                System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                System.out.println(ANSI_PURPLE + "Title: " + ANSI_RESET + ann.get("a_title"));
                System.out.println(ANSI_PURPLE + "Message: " + ANSI_RESET + ann.get("a_message"));
                System.out.println(ANSI_PURPLE + "Date: " + ANSI_RESET + ann.get("a_date"));
                System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Announcement not found!" + ANSI_RESET);
            }
        }
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

    // user main menu
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
            System.out.println(" ");
            System.out.println("1. Lost and Found");
            System.out.println("   1. Report Lost Item");
            System.out.println("   2. View Lost Items");
            System.out.println(" ");
            System.out.println("2. Reports");
            System.out.println("   1. Give Feedback");
            System.out.println("   2. Report Issues");
            System.out.println(" ");
            System.out.println("3. Announcement");
            System.out.println("   1. View Announcements");
            System.out.println(" ");
            System.out.println("4. Exit");
            System.out.print("Enter: ");
            userChoice = scan.nextInt();

            switch (userChoice) {
                case 1:
                    System.out.println("1. Report Lost Item");
                    System.out.println("2. View Lost Items");
                    System.out.print("Enter: ");
                    int lostChoice = scan.nextInt();
                    if (lostChoice == 1) addLostItem(config, scan, em);
                    else if (lostChoice == 2) viewLostItems(config, scan);
                    else System.out.println(ANSI_RED + "Invalid Option!" + ANSI_RESET);
                    break;

                case 2:
                    System.out.println("1. Give Feedback");
                    System.out.println("2. Report Issues");
                    System.out.print("Enter: ");
                    int reportChoice = scan.nextInt();
                    if (reportChoice == 1) giveFeedback(config, scan, em);
                    else if (reportChoice == 2) reportIssue(config, scan, em);
                    else System.out.println(ANSI_RED + "Invalid Option!" + ANSI_RESET);
                    break;

                case 3:
                    announcements(config, scan);
                    break;

                case 4:
                    System.out.println(ANSI_BLUE + "            Logging out..." + ANSI_RESET);
                    break;

                default:
                    System.out.println(ANSI_RED + "            Invalid Option!" + ANSI_RESET);
            }

        } while (userChoice != 4);
    }
}

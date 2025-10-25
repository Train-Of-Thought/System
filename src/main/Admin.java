package main;

import config.config;
import java.util.*;

public class Admin {
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";

    //viewFeedbacks [Admin]
    public static void viewFeedbacks(config config) {
        String query = "SELECT * FROM tbl_feedbacks";
        String[] headers = {
            ANSI_CYAN + "ID" + ANSI_RESET,
            ANSI_CYAN + "User" + ANSI_RESET,
            ANSI_CYAN + "Feedback" + ANSI_RESET,
            ANSI_CYAN + "Date" + ANSI_RESET
        };
        String[] columns = {"f_id", "f_user", "f_message", "f_date"};
        config.viewRecords(query, headers, columns);
    }
    
    // viewIssues [Admin]
    public static void viewIssues(config config) {
    String query = "SELECT * FROM tbl_issues";
    String[] headers = {
        ANSI_CYAN + "ID" + ANSI_RESET,
        ANSI_CYAN + "User" + ANSI_RESET,
        ANSI_CYAN + "Issue" + ANSI_RESET,
        ANSI_CYAN + "Date" + ANSI_RESET
    };
    String[] columns = {"i_id", "i_user", "i_message", "i_date"};
    config.viewRecords(query, headers, columns);
}

    //ViewUsers [Admin]
    public static void viewUsers() {
        String Query = "SELECT * FROM tbl_users WHERE u_type != 'SuperAdmin'";
        
        String[] votersHeaders = {
            ANSI_CYAN + "ID" + ANSI_RESET,
            ANSI_CYAN + "Frst Name" + ANSI_RESET,
            ANSI_CYAN + "Last Name" + ANSI_RESET,
            ANSI_CYAN + "Email" + ANSI_RESET,
            ANSI_CYAN + "Status" + ANSI_RESET,
            ANSI_CYAN + "Type" + ANSI_RESET
        };
        String[] votersColumns = {"u_id", "u_fname", "u_lname", "u_email", "u_status", "u_type"};
        config config = new config();
        config.viewRecords(Query, votersHeaders, votersColumns);
    }

    //accountUpdateandTermination [Admin]
    public static void accountUpdateOrTerminateAccount(config config, Scanner scan) {
        int choice;
        do {
            viewUsers();

            System.out.print("Enter User ID to update/terminate: ");
            int userId = scan.nextInt();
            scan.nextLine();
            System.out.println(ANSI_BLUE + "            ___UPDATE // TERMINATION___" + ANSI_RESET);
            System.out.println("            1. Update Account");
            System.out.println("            2. Terminate Account");
            System.out.println("            3. Back");
            System.out.print("Enter: ");
            choice = scan.nextInt();
            scan.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter new first name: ");
                    String newFName = scan.nextLine();
                    System.out.print("Enter new last name: ");
                    String newLName = scan.nextLine();
                    System.out.print("Enter new user type (Admin/User): ");
                    String newType = scan.nextLine();

                    while (!newType.equalsIgnoreCase("Admin") && !newType.equalsIgnoreCase("User")) {
                        System.out.print(ANSI_RED + "          Invalid type. Enter 'Admin' or 'User': " + ANSI_RESET);
                        newType = scan.nextLine();
                    }

                    String sqlUpdate = "UPDATE tbl_users SET u_fname = ?, u_lname = ?, u_type = ? WHERE u_id = ?";
                    config.updateRecord(sqlUpdate, newFName, newLName, newType, userId);
                    System.out.println(ANSI_GREEN + "            User account updated successfully." + ANSI_RESET);
                    break;

                case 2:
                    System.out.print("Are you sure you want to delete this user? (1 = Yes / 2 = No): ");
                    String confirm = scan.nextLine();
                    if (confirm.equals("1")) {
                        String sqlDelete = "DELETE FROM tbl_users WHERE u_id = ?";
                        config.updateRecord(sqlDelete, userId);
                        System.out.println(ANSI_GREEN + "            User account terminated successfully." + ANSI_RESET);
                    } else {
                        System.out.println(ANSI_BLUE + "            Termination cancelled." + ANSI_RESET);
                    }
                    break;

                case 3:
                    System.out.println(ANSI_BLUE + "            Returning to admin menu..." + ANSI_RESET);
                    break;

                default:
                    System.out.println(ANSI_RED + "            Invalid choice." + ANSI_RESET);
            }

            if (choice != 3) {
                System.out.print("Do you want to perform another update/termination? (1 = Yes): ");
                choice = scan.nextInt();
                scan.nextLine();
            }

        } while (choice == 1);
    }

    //viewLostItems [Admin/USER]
    public static void viewLostItems(config config) {
        String query = "SELECT * FROM tbl_items";
        String[] headers = {
            ANSI_CYAN + "ID" + ANSI_RESET,
            ANSI_CYAN + "Item Name" + ANSI_RESET,
            ANSI_CYAN + "Description" + ANSI_RESET,
            ANSI_CYAN + "Status" + ANSI_RESET
        };
        String[] columns = {"li_id", "li_name", "li_description", "li_status"};
        config.viewRecords(query, headers, columns);
    }

    //approveLostItems [Admin]
    public static void approveLostItems(config config, Scanner scan) {
        int choice;
        do {
            viewLostItems(config);
            System.out.print("Enter Lost Item ID to Approve: ");
            int id = scan.nextInt();
            String sql = "UPDATE tbl_items SET li_status = ? WHERE li_id = ?";
            config.updateRecord(sql, "          Approved", id);
            System.out.println("            --Would you like to continue?--");
            System.out.println("            1. Continue");
            System.out.println("            2. Back");
            System.out.print("Enter: ");
            choice = scan.nextInt();
        } while (choice == 1);
    }

    //Announcements [Admin]
    public static void announcements(config config) {
        String query = "SELECT * FROM tbl_announcements";
        String[] headers = {
            ANSI_CYAN + "ID" + ANSI_RESET,
            ANSI_CYAN + "Title" + ANSI_RESET,
            ANSI_CYAN + "Message" + ANSI_RESET,
            ANSI_CYAN + "Date" + ANSI_RESET
        };
        String[] columns = {"a_id", "a_title", "a_message", "a_date"};
        config.viewRecords(query, headers, columns);
    }

    public void admin() {

        Scanner scan = new Scanner(System.in);
        config config = new config();

        int choice;
        int adminChoice;

        do {
            System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
            System.out.println("            ___ADMIN MENU___");
            System.out.println("            1. Approve Account");
            System.out.println("            2. Approve Lost Items Requests");
            System.out.println("            3. Lost Items Records");
            System.out.println("            4. Account Update/Termination");
            System.out.println("            5. View Feedbacks");
            System.out.println("            6. View Reported Issues");
            System.out.println("            7. Post Announcement");
            System.out.println("            8. Logout");
            System.out.print("Enter: ");
            adminChoice = scan.nextInt();

            switch (adminChoice) {
                case 1:
                    do {
                        viewUsers();
                        System.out.print("Enter ID to Approve: ");
                        int ids = scan.nextInt();

                        String sql = "UPDATE tbl_users SET u_status = ? WHERE u_id = ?";
                        config.updateRecord(sql, "          Approved", ids);

                        System.out.println("            --Would you like to continue?--");
                        System.out.println("                    1. Continue");
                        System.out.println("                    2. Back");
                        System.out.print("Enter: ");
                        choice = scan.nextInt();

                    } while (choice == 1);
                    break;

                case 2:
                    System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                    approveLostItems(config, scan);
                    break;

                case 3:
                    System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                    viewLostItems(config);
                    break;

                case 4:
                    System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                    accountUpdateOrTerminateAccount(config, scan);
                    break;

                case 5:
                    System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                    viewFeedbacks(config);
                    break;

                case 6:
                    System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                    viewIssues(config);
                    break;

                case 7:
                    int annChoice;
                    do {
                        System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                        System.out.println("            ___ANNOUNCEMENT MENU___");
                        System.out.println("            1. View Announcements");
                        System.out.println("            2. Post Announcement");
                        System.out.println("            3. Back");
                        System.out.print("Enter: ");
                        annChoice = scan.nextInt();

                        switch (annChoice) {
                            case 1:
                                announcements(config);
                                break;
                            case 2:
                                System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                                System.out.print("Enter Title: ");
                                scan.nextLine();
                                String title = scan.nextLine();
                                System.out.print("Enter Message: ");
                                String message = scan.nextLine();

                                String sqlAnn = "INSERT INTO tbl_announcements (a_title, a_message, a_date) VALUES (?, ?, CURRENT_TIMESTAMP)";
                                config.addRecord(sqlAnn, title, message);
                                System.out.println(ANSI_GREEN + "            Announcement posted!" + ANSI_RESET);
                                break;
                            case 3:
                                System.out.println(ANSI_BLUE + "            Going back..." + ANSI_RESET);
                                break;
                            default:
                                System.out.println(ANSI_RED + "            Invalid Option!" + ANSI_RESET);
                        }
                    } while (annChoice != 3);
                    break;

                case 8:
                    System.out.println(ANSI_BLUE + "            Logging out..." + ANSI_RESET);
                    break;

                default:
                    System.out.println(ANSI_RED + "            Invalid Option!" + ANSI_RESET);
            }

        } while (adminChoice != 8);
    }
}

package main;

import config.config;
import java.util.*;
import static main.User.ANSI_PURPLE;

public class Admin {
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";

   // View all feedbacks (list only ID, User, Date)
public static void viewFeedbacks(config config, boolean showMessage) {
    String query = "SELECT * FROM tbl_feedbacks";
    String[] headers;
    String[] columns;

    if (showMessage) { // detailed view
        headers = new String[]{
            ANSI_CYAN + "ID" + ANSI_RESET,
            ANSI_CYAN + "User" + ANSI_RESET,
            ANSI_CYAN + "Feedback" + ANSI_RESET,
            ANSI_CYAN + "Date" + ANSI_RESET
        };
        columns = new String[]{"f_id", "f_user", "f_message", "f_date"};
    } else { // table view without message
        headers = new String[]{
            ANSI_CYAN + "ID" + ANSI_RESET,
            ANSI_CYAN + "User" + ANSI_RESET,
            ANSI_CYAN + "Date" + ANSI_RESET
        };
        columns = new String[]{"f_id", "f_user", "f_date"};
    }

    config.viewRecords(query, headers, columns);
}

// View details of a single feedback
public static void viewFeedbackDetails(config config, Scanner scan) {
    System.out.print("\nEnter Feedback ID to view details (0 to go back): ");
    int id = scan.nextInt();
    scan.nextLine(); // consume newline

    if (id == 0) return;

    String query = "SELECT * FROM tbl_feedbacks WHERE f_id = ?";
    List<Map<String, Object>> result = config.fetchRecords(query, id);

    if (!result.isEmpty()) {
        Map<String, Object> feedback = result.get(0);
        System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "ID: " + ANSI_RESET + feedback.get("f_id"));
        System.out.println(ANSI_CYAN + "User: " + ANSI_RESET + feedback.get("f_user"));
        System.out.println(ANSI_CYAN + "Feedback: " + ANSI_RESET + feedback.get("f_message"));
        System.out.println(ANSI_CYAN + "Date: " + ANSI_RESET + feedback.get("f_date"));
        System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
    } else {
        System.out.println(ANSI_RED + "Feedback not found!" + ANSI_RESET);
    }
}

    
   // View all issues (list only ID, User, Date)
public static void viewIssues(config config, boolean showMessage) {
    String query = "SELECT * FROM tbl_issues";
    String[] headers;
    String[] columns;

    if (showMessage) { // detailed view
        headers = new String[]{
            ANSI_CYAN + "ID" + ANSI_RESET,
            ANSI_CYAN + "User" + ANSI_RESET,
            ANSI_CYAN + "Issue" + ANSI_RESET,
            ANSI_CYAN + "Date" + ANSI_RESET
        };
        columns = new String[]{"i_id", "i_user", "i_message", "i_date"};
    } else { // table view without message
        headers = new String[]{
            ANSI_CYAN + "ID" + ANSI_RESET,
            ANSI_CYAN + "User" + ANSI_RESET,
            ANSI_CYAN + "Date" + ANSI_RESET
        };
        columns = new String[]{"i_id", "i_user", "i_date"};
    }

    config.viewRecords(query, headers, columns);
}

// View details of a single issue
public static void viewIssueDetails(config config, Scanner scan) {
    System.out.print("\nEnter Issue ID to view details (0 to go back): ");
    int id = scan.nextInt();
    scan.nextLine(); // consume newline

    if (id == 0) return;

    String query = "SELECT * FROM tbl_issues WHERE i_id = ?";
    List<Map<String, Object>> result = config.fetchRecords(query, id);

    if (!result.isEmpty()) {
        Map<String, Object> issue = result.get(0);
        System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "ID: " + ANSI_RESET + issue.get("i_id"));
        System.out.println(ANSI_CYAN + "User: " + ANSI_RESET + issue.get("i_user"));
        System.out.println(ANSI_CYAN + "Issue: " + ANSI_RESET + issue.get("i_message"));
        System.out.println(ANSI_CYAN + "Date: " + ANSI_RESET + issue.get("i_date"));
        System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
    } else {
        System.out.println(ANSI_RED + "Issue not found!" + ANSI_RESET);
    }
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
    
    public static void approveAccounts(config config, Scanner scan) {
    // Step 1: Display list of users pending approval
    String query = "SELECT u_id, u_fname, u_lname, u_email, u_type FROM tbl_users WHERE u_status != 'Approved' AND u_type != 'SuperAdmin'";
    String[] headers = {"ID", "First Name", "Last Name", "Email", "Type"};
    String[] columns = {"u_id", "u_fname", "u_lname", "u_email", "u_type"};
    config.viewRecords(query, headers, columns);

    // Step 2: Prompt admin to enter User ID to view details
    System.out.print("\nEnter the ID of the user to view details (0 to go back): ");
    int id = scan.nextInt();
    scan.nextLine(); // consume newline

    if (id != 0) {
        // Step 3: Fetch and display user details
        String detailQuery = "SELECT * FROM tbl_users WHERE u_id = ?";
        List<Map<String, Object>> result = config.fetchRecords(detailQuery, id);

        if (!result.isEmpty()) {
            Map<String, Object> user = result.get(0);
            System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "ID: " + ANSI_RESET + user.get("u_id"));
            System.out.println(ANSI_CYAN + "First Name: " + ANSI_RESET + user.get("u_fname"));
            System.out.println(ANSI_CYAN + "Last Name: " + ANSI_RESET + user.get("u_lname"));
            System.out.println(ANSI_CYAN + "Email: " + ANSI_RESET + user.get("u_email"));
            System.out.println(ANSI_CYAN + "Type: " + ANSI_RESET + user.get("u_type"));
            System.out.println(ANSI_CYAN + "Status: " + ANSI_RESET + user.get("u_status"));
            System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);

            // Step 4: Ask admin if they want to approve
            System.out.print("Approve this account? (1 = Yes / 2 = No): ");
            String confirm = scan.nextLine();
            if (confirm.equals("1")) {
                String sql = "UPDATE tbl_users SET u_status = ? WHERE u_id = ?";
                config.updateRecord(sql, "          Approved", id);
                System.out.println(ANSI_GREEN + "User account approved!" + ANSI_RESET);
            } else {
                System.out.println(ANSI_BLUE + "Approval cancelled." + ANSI_RESET);
            }
        } else {
            System.out.println(ANSI_RED + "User not found!" + ANSI_RESET);
        }
    }
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
    //viewLostItems [Admin/USER] - optionally show only pending items
//viewLostItems [Admin/USER] - flexible
// View Lost Items (List only ID, Name, Status)
public static void viewLostItems(config config, boolean showDescription) {
    String query = "SELECT * FROM tbl_items";
    String[] headers;
    String[] columns;

    if (showDescription) { // for detailed view
        headers = new String[]{
            ANSI_CYAN + "ID" + ANSI_RESET,
            ANSI_CYAN + "Item Name" + ANSI_RESET,
            ANSI_CYAN + "Description" + ANSI_RESET,
            ANSI_CYAN + "Status" + ANSI_RESET
        };
        columns = new String[]{"li_id", "li_name", "li_description", "li_status"};
    } else { // table view without description
        headers = new String[]{
            ANSI_CYAN + "ID" + ANSI_RESET,
            ANSI_CYAN + "Item Name" + ANSI_RESET,
            ANSI_CYAN + "Status" + ANSI_RESET
        };
        columns = new String[]{"li_id", "li_name", "li_status"};
    }

    config.viewRecords(query, headers, columns);
}


// View details of a single lost item by ID
public static void viewLostItemDetails(config config, Scanner scan) {
    System.out.print("\nEnter Lost Item ID to view details (0 to go back): ");
    int id = scan.nextInt();
    scan.nextLine(); // consume newline

    if (id == 0) return;

    String query = "SELECT * FROM tbl_items WHERE li_id = ?";
    List<Map<String, Object>> result = config.fetchRecords(query, id);

    if (!result.isEmpty()) {
        Map<String, Object> item = result.get(0);
        System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "ID: " + ANSI_RESET + item.get("li_id"));
        System.out.println(ANSI_CYAN + "Item Name: " + ANSI_RESET + item.get("li_name"));
        System.out.println(ANSI_CYAN + "Description: " + ANSI_RESET + item.get("li_description")); // now only here
        System.out.println(ANSI_CYAN + "Status: " + ANSI_RESET + item.get("li_status"));
        System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
    } else {
        System.out.println(ANSI_RED + "Lost item not found!" + ANSI_RESET);
    }
}


    //approveLostItems [Admin]
    //approveLostItems [Admin]
//approveLostItems [Admin]
public static void approveLostItems(config config, Scanner scan) {
    while (true) {
        // Step 1: Display only pending items
        viewLostItems(config, true);

        // Step 2: Prompt admin to enter Item ID
        System.out.print("\nEnter the ID of the lost item to view details (0 to go back): ");
        int id = scan.nextInt();
        scan.nextLine(); // consume newline

        if (id == 0) {
            System.out.println(ANSI_BLUE + "Returning to Lost Items menu..." + ANSI_RESET);
            break;
        }

        // Step 3: Fetch and display item details
        String detailQuery = "SELECT * FROM tbl_items WHERE li_id = ?";
        List<Map<String, Object>> result = config.fetchRecords(detailQuery, id);

        if (!result.isEmpty()) {
            Map<String, Object> item = result.get(0);
            System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "ID: " + ANSI_RESET + item.get("li_id"));
            System.out.println(ANSI_CYAN + "Item Name: " + ANSI_RESET + item.get("li_name"));
            System.out.println(ANSI_CYAN + "Description: " + ANSI_RESET + item.get("li_description"));
            System.out.println(ANSI_CYAN + "Status: " + ANSI_RESET + item.get("li_status"));
            System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);

            // Step 4: Ask admin if they want to approve
            System.out.print("Approve this lost item? (1 = Yes / 2 = No): ");
            String confirm = scan.nextLine();

            if (confirm.equals("1")) {
                String sql = "UPDATE tbl_items SET li_status = ? WHERE li_id = ?";
                config.updateRecord(sql, "          Approved", id);
                System.out.println(ANSI_GREEN + "Lost item approved!" + ANSI_RESET);
            } else {
                System.out.println(ANSI_BLUE + "Approval cancelled." + ANSI_RESET);
            }

        } else {
            System.out.println(ANSI_RED + "Lost item not found!" + ANSI_RESET);
        }
    }
}


    //Announcements [Admin]
    // Announcements [Admin]
// Announcements [Admin]
public static void announcements(config config, Scanner scan) {
    int choice;
    do {
        // Step 1: Display list of announcements (ID, Title, Date)
        String query = "SELECT a_id, a_title, a_date FROM tbl_announcements";
        String[] headers = {"ID", "Title", "Date"};
        String[] columns = {"a_id", "a_title", "a_date"};
        config.viewRecords(query, headers, columns);

        // Step 2: Display submenu
        System.out.println("\n1. View Announcement Details");
        System.out.println("2. Post Announcement");
        System.out.println("3. Update Announcement");
        System.out.println("4. Delete Announcement");
        System.out.println("5. Back");
        System.out.print("Enter choice: ");
        choice = scan.nextInt();
        scan.nextLine();

        switch (choice) {
            case 1: // View details
                System.out.print("Enter the ID of the announcement to view details (0 to go back): ");
                int id = scan.nextInt();
                scan.nextLine();
                if (id != 0) {
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
                break;

            case 2: // Post
                System.out.print("Enter Title: ");
                String title = scan.nextLine();
                System.out.print("Enter Message: ");
                String message = scan.nextLine();
                String sqlPost = "INSERT INTO tbl_announcements (a_title, a_message, a_date) VALUES (?, ?, CURRENT_TIMESTAMP)";
                config.addRecord(sqlPost, title, message);
                System.out.println(ANSI_GREEN + "Announcement posted!" + ANSI_RESET);
                break;

            case 3: // Update
                System.out.print("Enter the ID of the announcement to update: ");
                int updateId = scan.nextInt();
                scan.nextLine();
                System.out.print("Enter new Title: ");
                String newTitle = scan.nextLine();
                System.out.print("Enter new Message: ");
                String newMessage = scan.nextLine();
                String sqlUpdate = "UPDATE tbl_announcements SET a_title = ?, a_message = ? WHERE a_id = ?";
                config.addRecord(sqlUpdate, newTitle, newMessage, updateId);
                System.out.println(ANSI_GREEN + "Announcement updated!" + ANSI_RESET);
                break;

            case 4: // Delete
                System.out.print("Enter the ID of the announcement to delete: ");
                int deleteId = scan.nextInt();
                scan.nextLine();
                String sqlDelete = "DELETE FROM tbl_announcements WHERE a_id = ?";
                config.addRecord(sqlDelete, deleteId);
                System.out.println(ANSI_GREEN + "Announcement deleted!" + ANSI_RESET);
                break;

            case 5: // Back
                System.out.println(ANSI_BLUE + "Returning to main menu..." + ANSI_RESET);
                break;

            default:
                System.out.println(ANSI_RED + "Invalid Option!" + ANSI_RESET);
        }

    } while (choice != 5);
}

    public void admin() {

        Scanner scan = new Scanner(System.in);
        config config = new config();

        int choice;
        int adminChoice;

        do {
        System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
        System.out.println("                           " + ANSI_BLUE + "___ADMIN MENU___" + ANSI_RESET);
        System.out.println("1. Accounts");
        System.out.println("2. Lost Items");
        System.out.println("3. Feedbacks and Issues");
        System.out.println("4. Announcement");
        System.out.println("5. Logout");
        System.out.print("Enter: ");
        adminChoice = scan.nextInt();
        scan.nextLine();

        switch (adminChoice) {
            case 1: // Accounts submenu
                int accountChoice;
                do {
                    System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                    System.out.println("            ___ACCOUNTS MENU___");
                    System.out.println("            1. Approve Account");
                    System.out.println("            2. Account Update/Termination");
                    System.out.println("            3. Back");
                    System.out.print("Enter: ");
                    accountChoice = scan.nextInt();
                    scan.nextLine();

                    switch (accountChoice) {
                        case 1:
                            approveAccounts(config, scan);
                            break;
                        case 2:
                            accountUpdateOrTerminateAccount(config, scan);
                            break;
                        case 3:
                            System.out.println(ANSI_BLUE + "            Returning to main menu..." + ANSI_RESET);
                            break;
                        default:
                            System.out.println(ANSI_RED + "            Invalid Option!" + ANSI_RESET);
                    }
                } while (accountChoice != 3);
                break;

            case 2: // Lost Items submenu
                int lostChoice;
                do {
                    System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                    System.out.println("            ___LOST ITEMS MENU___");
                    System.out.println("            1. Approve Lost Items Requests");
                    System.out.println("            2. View Lost Items Records");
                    System.out.println("            3. Back");
                    System.out.print("Enter: ");
                    lostChoice = scan.nextInt();
                    scan.nextLine();

                    switch (lostChoice) {
                        case 1:
                             approveLostItems(config, scan); // Uses new approval flow
                            break;
                        case 2: // View Lost Items Records
                            viewLostItems(config, false); // show all items without description
                            viewLostItemDetails(config, scan); // optional detailed view
                            break;

                        case 3:
                            System.out.println(ANSI_BLUE + "            Returning to main menu..." + ANSI_RESET);
                            break;
                        default:
                            System.out.println(ANSI_RED + "            Invalid Option!" + ANSI_RESET);
                    }
                } while (lostChoice != 3);
                break;

            case 3: // Feedbacks and Issues submenu
                int fbChoice;
                do {
                    System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                    System.out.println("            ___FEEDBACK/ISSUES MENU___");
                    System.out.println("            1. View Feedbacks");
                    System.out.println("            2. View Reported Issues");
                    System.out.println("            3. Back");
                    System.out.print("Enter: ");
                    fbChoice = scan.nextInt();
                    scan.nextLine();

                    switch (fbChoice) {
                       case 1:
                            viewFeedbacks(config, false); // show list without message
                            viewFeedbackDetails(config, scan); // optional detailed view
                            break;

                        case 2:
                            viewIssues(config, false); // show list without message
                            viewIssueDetails(config, scan); // optional detailed view
                            break;
                        case 3:
                            System.out.println(ANSI_BLUE + "            Returning to main menu..." + ANSI_RESET);
                            break;
                        default:
                            System.out.println(ANSI_RED + "            Invalid Option!" + ANSI_RESET);
                    }
                } while (fbChoice != 3);
                break;

            case 4: // Announcement submenu
                announcements(config, scan);
            break;


            case 5:
                System.out.println(ANSI_BLUE + "            Logging out..." + ANSI_RESET);
                break;

            default:
                System.out.println(ANSI_RED + "            Invalid Option!" + ANSI_RESET);
        }

    } while (adminChoice != 5);
    }
}

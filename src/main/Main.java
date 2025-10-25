package main;

import config.config;
import java.util.*;

public class Main {
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_WHITE = "\\u001B[41m";
    
    public static void asd(){
        //WAHAHAHAHAHAHA
        
        

    }

    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
        config config = new config();
        
        int ch;
        int choice;
        
        do {
            System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
            System.out.println("            ___MAIN MENU___");
            System.out.println("            1. Login");
            System.out.println("            2. Register");
            System.out.println("            3. Exit");
            System.out.print("Enter: ");
            choice = scan.nextInt();
            
            switch (choice) {
                case 1:
                    System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                    System.out.println("            ___LOGIN___");
                    System.out.print("Enter email: ");
                    String em = scan.next();
                    System.out.print("Enter Password: ");
                    String password = scan.next();
                    
                    while (true) {
                        String hashedPass = config.hashPassword(password);
                        String sql = "SELECT * FROM tbl_users WHERE u_email = ? AND u_password = ?";
                        var result = config.fetchRecords(sql, em, hashedPass);
                        
                        if (result.isEmpty()) {
                            System.out.println(ANSI_RED + "            INVALID CREDENTIALS" + ANSI_RESET);
                            break;
                        } else {
                            java.util.Map<String, Object> user = result.get(0);
                            String stat = user.get("u_status").toString();
                            String type = user.get("u_type").toString();
                            String firstName = user.get("u_fname").toString();
                            String lastName = user.get("u_lname").toString();
                            if (stat.equals("Pending")) {
                                System.out.println(ANSI_RED + "            Account is Pending, Contact the Admin!" + ANSI_RESET);
                                break;
                            } else {
                                System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                                System.out.println(ANSI_GREEN + "            LOGIN SUCCESS!" + ANSI_RESET);
                                if (type.equals("Admin")) {
                                    Admin admin = new Admin();
                                    admin.admin();
                                } else if (type.equals("User")) {
                                    User userObj = new User();
                                    userObj.user(em, firstName, lastName);
                                } else if (type.equals("SuperAdmin")){
                                    SuperAdmin SupAd = new SuperAdmin();
                                    SupAd.superAdmin();
                                }
                                break;
                            }
                        }
                    }
                    break;
                
                case 2:
                    System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
                    System.out.println("            ___REGISTER___");
                    System.out.println("Enter first name: ");
                    String f_name = scan.next();
                    System.out.println("Enter last name: ");
                    String l_name = scan.next();
                    System.out.println("Enter email: ");
                    String email = scan.next();
                    System.out.println("Enter password: ");
                    String pass = scan.next();
                    
                    // Hash the password before saving
                    String hashedPass = config.hashPassword(pass);
                    
                    // Check for existing email
                    while (true) {
                        String qry = "SELECT * FROM tbl_users WHERE u_email = ?";
                        java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(qry, email);

                        if (result.isEmpty()) {
                            break;
                        } else {
                            System.out.print(ANSI_RED + "          Email already exists, Enter other Email: " + ANSI_RESET);
                            email = scan.next();
                        }
                    }
                    System.out.println("            --Select User Type--");
                    System.out.println("            1. Admin");
                    System.out.println("            2. User");
                    System.out.print("Enter: ");
                    int type = scan.nextInt();
                    while (type > 2 || type < 1) {
                        System.out.print(ANSI_RED + "          Invalid, choose between 1 & 2 only: " + ANSI_RESET);
                        type = scan.nextInt();
                    }
                    String tp = (type == 1) ? "Admin" : "User";
                    
                    String sql = "INSERT INTO tbl_users (u_fname, u_lname, u_email, u_password, u_status, u_type) VALUES (?, ?, ?, ?, ?, ?)";
                    config.addRecord(sql, f_name, l_name, email, hashedPass, "Pending", tp);
                    
                    System.out.println(ANSI_GREEN + "          Registration submitted for approval." + ANSI_RESET);
                    
                    break;
                
                case 3:
                    System.out.println(ANSI_BLUE + "            Exiting..." + ANSI_RESET);
                    break;
            }
            System.out.println(ANSI_BLUE + "----------------------------------------------------------" + ANSI_RESET);
            System.out.println("            --Do you want to continue?--");
            System.out.println("            1. Yes");
            System.out.println("            2. No");
            System.out.println("            Enter: ");
            ch = scan.nextInt();
            
        } while (ch == 1);
        
        System.out.println(ANSI_BLUE + "            Program ended..." + ANSI_RESET);
    }
}

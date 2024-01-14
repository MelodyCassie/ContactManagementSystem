import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class ContactBook {
    public static void contactMain() throws IOException {
        String[] credentials = {"Login", "Sign-Up"};
        boolean loop = false;

        do {
            Object userInput = JOptionPane.showInputDialog(
                    null,
                    "Reach out to friends and family.",
                    "Contact Book",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    credentials,
                    credentials[0]);

            if (userInput == null) {
                JOptionPane.showMessageDialog(null, "Logging out...");
                return;
            }

            String userChoice = userInput.toString();

            switch (userChoice) {
                case "Login":
                    loop = loginPage();
                    break;
                case "Sign-Up":
                    loop = signUpPage();
                    break;
                default:
                    loop = true;
            }
        } while (loop);
    }

    public static void successfulLogin(String filename) throws IOException {
        String[] infoDetails = {"Contact Name: ", "Phone number: ", "Address: "};
        String[] list = {"Add ", "Read ", "Find ", "Delete Contact / Update Data"};
        String choice;
        String data = "*************[Phone Book]*************";
        char continueOrExit = 'y';

        do {
            Object[] options = {"Add", "Read", "Find", "Delete Contact / Update Data"};
            int option = JOptionPane.showOptionDialog(null,
                    "Select an option",
                    "Contact Book",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (option == -1) {
                JOptionPane.showMessageDialog(null, "Logging out...");
                return;
            }
            choice = String.valueOf(option + 1);

            try {
                if (!(Integer.parseInt(choice) > 0 & Integer.parseInt(choice) <= 4)) {
                    JOptionPane.showMessageDialog(null, "Please select an appropriate option");
                    continue;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Please select integer option");
                continue;
            }

            switch (choice) {
                case "1": {
                    data += add(infoDetails);
                    saveData(data, filename);
                }
                break;
                case "2":
                    readData(filename);
                    break;
                case "3":
                    findEntity(filename);
                    break;
                case "4":
                    updateContact_contact(filename, infoDetails);
                    break;
            }

            int optionResult = JOptionPane.showOptionDialog(null,
                    "Press OK to continue or Cancel to exit",
                    "Contact Book",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    new Object[]{"OK", "Cancel"},
                    "OK");

            if (optionResult == 1) {
                continueOrExit = 'x';
            } else {
                continueOrExit = 'y';
            }

        } while (continueOrExit == 'y');
    }

    public static String add(String[] info) {
        String savedData = "\n";
        char choice;

        do {
            StringBuilder data = new StringBuilder("\nEnter contact details...");
            for (String s : info) {
                String input = JOptionPane.showInputDialog(null, s);
                if (input == null) {
                    JOptionPane.showMessageDialog(null, "Operation cancelled.");
                    return "";
                }
                data.append("\n").append(s).append(input);
            }
            savedData += data + "\n---------------\n";
            choice = JOptionPane.showInputDialog(null, "Press (y) to save another contact or (n) to exit").toLowerCase().charAt(0);
        } while (choice == 'y');
        return savedData;
    }

    public static void saveData(String data, String filename) throws IOException {
        File contactBookTextFile = new File(filename);
        if (!contactBookTextFile.exists() && !contactBookTextFile.createNewFile()) {
            JOptionPane.showMessageDialog(null, "Error creating file");
            return;
        }
        try (FileWriter fileWriter = new FileWriter(contactBookTextFile, true)) {
            fileWriter.write(data);
        }
    }

    public static void readData(String filename) throws IOException {
        File myFile = new File(filename);

        try (Scanner scanner = new Scanner(myFile)) {
            try {
                scanner.nextLine();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Contact book exists but it is empty");
                return;
            }
            StringBuilder output = new StringBuilder();
            while (scanner.hasNextLine()) output.append(scanner.nextLine()).append("\n");
            JOptionPane.showMessageDialog(null, output.toString());
        }
    }

    public static void findEntity(String filename) throws IOException {
        File myFile = new File(filename);

        try (Scanner scanner = new Scanner(myFile)) {
            try {
                scanner.nextLine();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Contact book exists but it is empty");
                return;
            }

            String name = JOptionPane.showInputDialog(null, "Enter name to find:");
            if (name == null) {
                JOptionPane.showMessageDialog(null, "Operation cancelled.");
                return;
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("Contact Name:") && line.toLowerCase().endsWith(name.toLowerCase())) {
                    JOptionPane.showMessageDialog(null, "Contact found: \n" + line);
                    try {
                        JOptionPane.showMessageDialog(null, scanner.nextLine());
                        JOptionPane.showMessageDialog(null, scanner.nextLine());
                        JOptionPane.showMessageDialog(null, scanner.nextLine());
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "End of phone book reached");
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Contact not found.");
        }
    }

    public static boolean loginPage() throws IOException {
        JOptionPane.showMessageDialog(null, "\n\t\t**** [Welcome to Login Page] ****\n");
        String username = JOptionPane.showInputDialog(null, "Enter your username:");
        if (username == null) {
            JOptionPane.showMessageDialog(null, "Operation cancelled.");
            return true;
        }

        String filename = "./" + username + ".txt";
        File userFile = new File(filename);
        File registrationVerification = new File("./Registered_Area.txt");

        if (!registrationVerification.exists()) {
            JOptionPane.showMessageDialog(null, "Not registered.\nTry other username or sign-up");
            return true;
        } else if (!userFile.exists()) {
            JOptionPane.showMessageDialog(null, "Login Failed\nNo Such Id found");
            return true;
        } else {
            try (Scanner userScan = new Scanner(registrationVerification)) {
                String passcode = JOptionPane.showInputDialog(null, "Enter password:");
                if (passcode == null) {
                    JOptionPane.showMessageDialog(null, "Operation cancelled.");
                    return true;
                }

                while (userScan.hasNextLine()) {
                    String nextLine = userScan.nextLine().toLowerCase(Locale.ROOT);
                    if (nextLine.endsWith(username.toLowerCase(Locale.ROOT))) {
                        String s = userScan.nextLine();
                        if (passcode.equals(s.substring(s.indexOf(" ") + 1))) {
                            JOptionPane.showMessageDialog(null, "Password matched.... logging in...\nNow you can perform the following functions");
                            successfulLogin(filename);
                            return false;
                        }
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Wrong Password");
        return true;
    }

    public static boolean signUpPage() throws IOException {
        JOptionPane.showMessageDialog(null, "\n\t\t**** [Welcome to Sign-Up Page] ****\n");
        boolean OK = true;

        do {
            String username = JOptionPane.showInputDialog(null, "Enter username:");
            if (username == null) {
                JOptionPane.showMessageDialog(null, "Operation cancelled.");
                return true;
            }

            File f = new File("./" + username + ".txt");
            if (f.exists()) {
                JOptionPane.showMessageDialog(null, "User already exists. Try a new username.");
            } else {
                String password = JOptionPane.showInputDialog(null, "Create Password:");
                if (password == null) {
                    JOptionPane.showMessageDialog(null, "Operation cancelled.");
                    return true;
                }

                f.createNewFile();
                File registered = new File("./Registered_Area.txt");
                if (!registered.exists()) {
                    registered.createNewFile();
                }

                try (FileWriter fileWriter = new FileWriter("./Registered_Area.txt", true)) {
                    fileWriter.write("\nUsername: " + username + "\n");
                    fileWriter.write("Password: " + password + "\n");
                }

                JOptionPane.showMessageDialog(null, "User registered successfully.\nNow you can Log-in to your contact book.");
                OK = false;
            }
        } while (OK);
        return true;
    }

    public static void updateContact_contact(String filename, String[] info) throws IOException {
        char choice;
        boolean found = false;
        JOptionPane.showMessageDialog(null, "\nEnter contact name to update...");
        String contactName = JOptionPane.showInputDialog(null, "Enter contact name to update:");
        if (contactName == null) {
            JOptionPane.showMessageDialog(null, "Operation cancelled.");
            return;
        }

        try (Scanner scan = new Scanner(new File(filename))) {
            while (scan.hasNextLine()) {
                if (scan.nextLine().toLowerCase(Locale.ROOT).endsWith(contactName.toLowerCase(Locale.ROOT))) {
                    JOptionPane.showMessageDialog(null, "Contact found");
                    found = true;
                    break;
                }
            }
        }

        if (found) {
            String[] deleteUpdate = {"Delete", "Rename or Update"};
            boolean correct = true;

            do {
                Object[] options = {"Delete", "Rename or Update"};
                int option = JOptionPane.showOptionDialog(null,
                        "Select an option",
                        "Contact Book",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (option == -1) {
                    JOptionPane.showMessageDialog(null, "Operation cancelled.");
                    return;
                }

                choice = (char) (option + '1');

                switch (choice) {
                    case '1':
                        delete(filename, contactName, info);
                        correct = false;
                        break;
                    case '2':
                        updateExistingData(filename, contactName, info);
                        correct = false;
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Wrong selection:");
                        continue;
                }
            } while (correct);
        } else {
            JOptionPane.showMessageDialog(null, "Contact not found.\nTry correct Contact name");
        }
    }

    public static void delete(String filename, String contactName, String[] info) throws IOException {
        File oldFile = new File(filename);
        Scanner scan = new Scanner(oldFile);
        StringBuilder allData = new StringBuilder();

        while (scan.hasNextLine()) {
            String n = scan.nextLine();
            if (n.toLowerCase(Locale.ROOT).endsWith(contactName.toLowerCase(Locale.ROOT))) {
                scan.nextLine();
                scan.nextLine();
            } else {
                allData.append(n).append("\n");
            }
        }
        scan.close();
        if (!oldFile.delete()) {
            JOptionPane.showMessageDialog(null, "Error deleting contact");
            return;
        }

        File newFile = new File(filename);

        try (FileWriter fileWriter = new FileWriter(newFile)) {
            fileWriter.write(allData.toString());
        }

        JOptionPane.showMessageDialog(null, "Contact Deleted Successfully");
    }

    public static void updateExistingData(String filename, String contactName, String[] info) throws IOException {
        File oldFile = new File(filename);
        Scanner scan = new Scanner(oldFile);
        Scanner input = new Scanner(System.in);
        StringBuilder allData = new StringBuilder();

        while (scan.hasNextLine()) {
            String n = scan.nextLine();
            if (n.toLowerCase(Locale.ROOT).endsWith(contactName.toLowerCase(Locale.ROOT))) {
                scan.nextLine();
                scan.nextLine();
                JOptionPane.showMessageDialog(null, "Enter data to be updated:");

                for (String s : info) {
                    input.useDelimiter("\\r");
                    String data = JOptionPane.showInputDialog(null, s);
                    allData.append(s).append(data).append("\n");
                    input.reset();
                }
            } else {
                allData.append(n).append("\n");
            }
        }
        scan.close();

        if (!oldFile.delete()) {
            JOptionPane.showMessageDialog(null, "Error updating contact details");
            return;
        }

        try (FileWriter fileWriter = new FileWriter(new File(filename))) {
            fileWriter.write(allData.toString());
        }
        JOptionPane.showMessageDialog(null, "Contact details updated successfully");
    }
}

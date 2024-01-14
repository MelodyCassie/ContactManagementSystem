import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) throws IOException {
        String[] cellFunctions = {"Phonebook", "Game"};
        int choice;
        int againYesNo = 0;

        do {
            String currentTime = getCurrentTime();
            String input = (String) JOptionPane.showInputDialog(
                    null,
                    "Hello, welcome to your iPhone",
                    "5G | " + currentTime, JOptionPane.PLAIN_MESSAGE,
                    null,
                    cellFunctions,
                    cellFunctions[0]);

            if (input == null) {
                System.out.println("Powering down...");
                break;
            }

            choice = Arrays.asList(cellFunctions).indexOf(input) + 1;

            switch (choice) {
                case 1:
                    ContactBook.contactMain();
                    break;
                case 2:
                    Game.gaming();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Wrong Selection!\nSelect appropriate option");
                    continue;
            }
            againYesNo = JOptionPane.showConfirmDialog(null, "Switch off your phone?", "Confirmation", JOptionPane.YES_NO_OPTION);
        } while (againYesNo == JOptionPane.NO_OPTION);
        JOptionPane.showMessageDialog(null, "Powering down...");
    }

    private static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(new Date());
    }
}

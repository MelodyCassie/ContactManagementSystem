import java.io.IOException;
import java.util.Arrays;
import javax.swing.JOptionPane;

public class Game {
    public static void gaming() throws IOException {
        String[] listOfGames = {"Hangman", "Country Capital"};
        int choiceOfGame = JOptionPane.showOptionDialog(null,
                "Play and challenge friends while playing online.",
                "Game Center",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                listOfGames,
                listOfGames[0]);

        switch (choiceOfGame) {
            case 0:
                hangManGame();
                break;
            case 1:
                countryCapital();
                break;
        }
    }

    public static void countryCapital() throws IOException {
        String[][] list = {
                {"Nigeria", "Abuja"}, {"Portugal", "Lisbon"}, {"Canada", "Ottawa"},
                {"New Zealand", "Wellington"}, {"Japan", "Tokyo"}};
        shuffle(list);
        int win = 0;
        int a = 0;
        String[][] output = new String[5][3];
        while (a != 5) {
            for (String[] strings : list) {
                String answer = JOptionPane.showInputDialog(null, "What is the capital of " + strings[0] + "?");
                if (answer == null) {
                    JOptionPane.showMessageDialog(null, "Operation cancelled.");
                    return;
                }
                if (answer.equalsIgnoreCase(strings[1])) {
                    JOptionPane.showMessageDialog(null, "Correct!");
                    win++;
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong! Correct answer is: " + strings[1]);
                }
                output[a] = new String[]{strings[0], answer, strings[1]};
                a++;
            }
        }

        JOptionPane.showMessageDialog(null, "The correct count is: " + win + " out of 5");
        StringBuilder summary = new StringBuilder("The summary is: \n");
        summary.append(String.format("%-20s%-20s%-20s\n", "States", "Answers", "Capitals"));
        summary.append("------------------------------------------------------\n");

        for (String[] strings : output) {
            summary.append(String.format("%-20s%-20s%-20s\n", strings[0], strings[1], strings[2]));
        }
        JOptionPane.showMessageDialog(null, summary.toString());
    }

    public static void shuffle(String[][] list) {
        for (int i = 0; i < list.length; i++) {
            int rd = (int) (Math.random() * list.length);
            String[] temp = list[i];
            list[i] = list[rd];
            list[rd] = temp;
        }
    }

    public static void hangManGame() {
        char y = 'y';
        while (y == 'y') {
            char[] list = randomWord();
            playGame(list);
            int optionResult = JOptionPane.showOptionDialog(null,
                    "Do you want to play again?",
                    "Hang Man Game",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    new Object[]{"Yes", "No"},
                    "Yes");

            if (optionResult == 1) {
                y = 'x';
            } else {
                y = 'y';
            }
        }
    }

    public static void playGame(char[] list) {
        String word = new String(list);
        char[] asterisks = new char[list.length];
        Arrays.fill(asterisks, '*');
        int count = 0;
        int chance = 5;

        while (count < list.length && chance > 0) {
            String guess = JOptionPane.showInputDialog(null,
                    "Enter guess:",
                    "Hangman Game",
                    JOptionPane.PLAIN_MESSAGE);

            if (guess == null) {
                JOptionPane.showMessageDialog(null, "Operation cancelled.");
                return;
            }

            char letter = guess.charAt(0);
            boolean x = false;
            int index = 0;

            for (int i = 0; i < list.length; i++) {
                if (letter == list[i]) {
                    x = true;
                    index = i;
                }
            }

            if (x) {
                asterisks[index] = list[index];
                list[index] = '*';
                count++;
            } else {
                JOptionPane.showMessageDialog(null, letter + " is not part\nChances left: " + chance--);
            }

            if (count == list.length || chance == 0) {
                JOptionPane.showMessageDialog(null, "The correct word is: " + word + "\nYou missed: " + (5 - chance));
            }
        }
    }

    public static char[] randomWord() {
        char[] list1 = {'g', 'h', 'a', 'n', 'a'};
        char[] list2 = {'a', 'm', 'e', 'r', 'i', 'c', 'a'};
        char[] list3 = {'g', 'e', 'r', 'm', 'a', 'n', 'y'};
        char[] list4 = {'c', 'h', 'i', 'n', 'a'};
        int random = (int) (1 + (Math.random() * 4));

        switch (random) {
            case 1:
                return list1;
            case 2:
                return list2;
            case 3:
                return list3;
            case 4:
                return list4;
        }
        return list1;
    }
}

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            if (Objects.equals(input, "exit 0"))
                break;
            String[] command = input.split("\s+");

            if (command.length == 0)
                continue;
            if (Objects.equals(command[0], "echo")) {
                for (int i = 1; i < command.length; i++) {
                    System.out.print(command[i]);
                    if (i != command.length - 1)
                        System.out.print(" ");
                }
                System.out.println("");
            }
            else
                System.out.println(input + ": command not found");
        }
    }
}

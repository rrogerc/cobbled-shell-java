import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Scanner;
import java.util.Arrays;

public class Main {
    static boolean handle_path_program(String[] command, String input, String[] path_directories) throws Exception {
        for (String dir : path_directories) {
            Path path = Path.of(dir + "/" + command[0]);

            if (Files.isExecutable(path)) {
                String full_command = command[0] + " " + input.substring(command[0].length());
                String[] full_path = full_command.split("\s+");

                Process p = Runtime.getRuntime().exec(full_path);
                p.getInputStream().transferTo(System.out);
                return true;
            }
        }

        return false;
    }

    static void handle_type_command(String[] command, String[] path_directories) {
        if (command.length == 1)
            return;

        String[] builtins = {"echo", "exit", "type"};
        Arrays.sort(builtins);

        if (Arrays.binarySearch(builtins, command[1]) >= 0) {
            System.out.println(command[1] + " is a shell builtin");
            return;
        }

        for (String dir : path_directories) {
            Path path = Path.of(dir + "/" + command[1]);
            if (Files.isExecutable(path)) {
                System.out.println(command[1] + " is " + path.toString());
                return;
            }
        }

        System.out.println(command[1] + ": not found");
    }

    static void handle_echo_command(String[] command) {
        for (int i = 1; i < command.length; i++) {
            System.out.print(command[i]);
            if (i != command.length - 1)
                System.out.print(" ");
        }
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        String[] path_directories = System.getenv("PATH").split(":");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            if (Objects.equals(input, "exit 0"))
                break;
            String[] command = input.split("\s+");

            if (command.length == 0)
                continue;

            if (Objects.equals(command[0], "echo"))
                handle_echo_command(command);
            else if (Objects.equals(command[0], "type"))
                handle_type_command(command, path_directories);
            else if (handle_path_program(command, input, path_directories))
                continue;
            else
                System.out.println(input + ": command not found");
        }
    }
}

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Actividad10 {

    private static final Pattern LENGTH_PATTERN = Pattern.compile(".{8,}");
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile(".*[!@#$%^&*(),.?\":{}|<>].*");
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile(".*[A-Z].*[A-Z].*");
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile(".*[a-z].*[a-z].*[a-z].*");
    private static final Pattern NUMBER_PATTERN = Pattern.compile(".*\\d.*");


    public static boolean validatePassword(String password) {
        boolean lengthValid = LENGTH_PATTERN.matcher(password).matches();
        boolean specialCharValid = SPECIAL_CHAR_PATTERN.matcher(password).matches();
        boolean uppercaseValid = UPPERCASE_PATTERN.matcher(password).matches();
        boolean lowercaseValid = LOWERCASE_PATTERN.matcher(password).matches();
        boolean numberValid = NUMBER_PATTERN.matcher(password).matches();

        if (!lengthValid) {
            System.out.println("Contraseña: " + password + " -> Inválida (Debe tener al menos 8 caracteres)");
        } else if (!specialCharValid) {
            System.out.println("Contraseña: " + password + " -> Inválida (Debe contener al menos un carácter especial)");
        } else if (!uppercaseValid) {
            System.out.println("Contraseña: " + password + " -> Inválida (Debe contener al menos dos letras mayúsculas)");
        } else if (!lowercaseValid) {
            System.out.println("Contraseña: " + password + " -> Inválida (Debe contener al menos tres letras minúsculas)");
        } else if (!numberValid) {
            System.out.println("Contraseña: " + password + " -> Inválida (Debe contener al menos un número)");

        } return lengthValid && specialCharValid && uppercaseValid && lowercaseValid && numberValid;

    }

    public static void showSavedPasswords(String logFile) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(logFile));
            System.out.println("Lista de contraseñas guardadas:");
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de registro: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        String logFile = "Archivo-contraseñas.txt";


        try {
            Path path = Paths.get(logFile);
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo de registro: " + e.getMessage());
            return;
        }
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(logFile)))) {
            System.out.println("Ingrese contraseñas para validar. Escriba 'salir' para terminar:");

            while (true) {
                System.out.print("Contraseña: ");
                String password = scanner.nextLine();
                if (password.equalsIgnoreCase("salir")) {
                    break;
                }

                executorService.submit(() -> {
                    boolean isValid = validatePassword(password);
                    String result = isValid ? "Válida" : "Inválida";
                    System.out.println("Contraseña: " + password + " -> " + result);

                    synchronized (writer) {
                        writer.println("Contraseña: " + password + " -> " + result);
                    }
                });
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de registro: " + e.getMessage());
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {

        }
        System.out.println("Validación de contraseñas completada. Los resultados se han guardado en " + logFile);
        scanner.close();

        showSavedPasswords(logFile);
    }
}
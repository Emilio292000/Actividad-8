import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Actividad9 {


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
        }

        return lengthValid && specialCharValid && uppercaseValid && lowercaseValid && numberValid;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        System.out.println("Ingrese contraseñas para validar. Escriba 'salir' para terminar:");

        while (true) {
            System.out.print("Contraseña: ");
            String password = scanner.nextLine();

            if (password.equalsIgnoreCase("salir")) {
                break;
            }

            executorService.submit(() -> {
                boolean isValid = validatePassword(password);
                if (isValid) {
                    System.out.println("Contraseña: " + password + " -> Válida");
                } else {
                    System.out.println("Contraseña: " + password + " -> Inválida");
                }
            });
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {

        }

        System.out.println("Validación de contraseñas completada.");
        scanner.close();
    }
}

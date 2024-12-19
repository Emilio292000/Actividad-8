import java.util.Scanner;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Double> resultados = new ArrayList<>();
        boolean continuar = true;

        while (continuar) {
            System.out.println("Ingrese una operación matemática (ejemplo: 10 + 5) o 'salir' para terminar:");
            System.out.println("(Ingrese las variables con los espacios y la operacion correspondiente)");
            String entrada = scanner.nextLine();

            if (entrada.equalsIgnoreCase("salir")) {
                continuar = false;
            } else {
                try {

                    String[] partes = entrada.split(" ");
                    double variable1 = Double.parseDouble(partes[0]);
                    String variable = partes[1];
                    double variable2 = Double.parseDouble(partes[2]);


                    double resultado = 0;


                    Operacion operacion = null;
                    switch (variable) {
                        case "+":
                            operacion = new Suma(variable1, variable2);
                            break;
                        case "-":
                            operacion = new Resta(variable1, variable2);
                            break;
                        case "*":
                            operacion = new Multiplicacion(variable1, variable2);
                            break;
                        case "/":
                            operacion = new Division(variable1, variable2);
                            break;
                        case "^":
                            operacion = new Potencia(variable1, (int) variable2);
                            break;
                        default:
                            throw new IllegalArgumentException("Datos invalidos.");
                    }

                    // Calcular el resultado
                    resultado = operacion.calcular(variable1, variable2);

                    // Almacenar el resultado
                    resultados.add(resultado);
                    System.out.println("Resultado: " + resultado);

                } catch (NumberFormatException e) {
                    System.out.println("Error: Por favor, ingrese números válidos.");
                } catch (IllegalArgumentException | ArithmeticException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }

        System.out.println("Resultados almacenados:");
        for (Double res : resultados) {
            System.out.println(res);
        }

        scanner.close();
    }
}

interface Operacion {
    double calcular(double a, double b);
}


abstract class OperacionBase implements Operacion {
    protected double a, b;

    public OperacionBase(double a, double b) {
        this.a = a;
        this.b = b;
    }


    @Override
    public abstract double calcular(double a, double b);
}


class Suma extends OperacionBase {
    public Suma(double a, double b) {
        super(a, b);
    }

    @Override
    public double calcular(double a, double b) {
        return a + b;
    }
}


class Resta extends OperacionBase {
    public Resta(double a, double b) {
        super(a, b);
    }

    @Override
    public double calcular(double a, double b) {
        return a - b;
    }
}


class Multiplicacion extends OperacionBase {
    public Multiplicacion(double a, double b) {
        super(a, b);
    }

    @Override
    public double calcular(double a, double b) {
        return a * b;
    }
}


class Division extends OperacionBase {
    public Division(double a, double b) {
        super(a, b);
    }

    @Override
    public double calcular(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("No se puede dividir entre cero.");
        }
        return a / b;
    }
}


class Potencia implements Operacion {
    private double base;
    private int exponente;

    public Potencia(double base, int exponente) {
        this.base = base;
        this.exponente = exponente;
    }

    @Override
    public double calcular(double a, double b) {
        return potencia(base, exponente);
    }


    private double potencia(double base, int exponente) {
        if (exponente == 0) {
            return 1;
        } else if (exponente < 0) {
            return 1 / potencia(base, -exponente);
        } else {
            return base * potencia(base, exponente - 1);
        }
    }
}


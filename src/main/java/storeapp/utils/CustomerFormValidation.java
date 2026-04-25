package storeapp.utils;

import storeapp.domain.Customer;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerFormValidation {

    static Scanner sc = new Scanner(System.in);

    // Valida que el id sea un número entero y que funcione correctame
    public static void validateId(Customer customer) {
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println("Ingrese el id del cliente");
                int id = sc.nextInt();
                sc.nextLine();
                validInput = true;
                customer.setId(id);
            } catch (Exception e) {
                System.out.println("Error al ingresar el id del cliente, por favor ingrese un numero entero");
                validInput = false;
                sc.nextLine();
            }
        }
    }

    // Valida que el valor ingresado sea un número entero
    public static int validateInt(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                int value = sc.nextInt();
                sc.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Error: el valor debe ser un número , intente de nuevo");
                sc.nextLine();
            }
        }
    }

    // Valida que el valor ingresado sea un número decimal
    public static double validateDouble(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                double value = sc.nextDouble();
                sc.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Error: el valor debe ser un número decimal, intente de nuevo");
                sc.nextLine();
            }
        }
    }

    // Valida que el valor ingresado sea true o false
    public static boolean validateBoolean(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                boolean value = sc.nextBoolean();
                sc.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Error: el valor debe ser true o false, intente de nuevo");
                sc.nextLine();
            }
        }
    }

    // Valida que el texto ingresado no esté vacío
    public static String validateString(String prompt) {
        while (true) {
            System.out.println(prompt);
            String value = sc.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Error: el campo no puede estar vacío, intente de nuevo");
        }
    }

    // Valida que el número entero sea positivo (mayor a cero)
    public static int validatePositiveInt(String prompt) {
        while (true) {
            int value = validateInt(prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("Error: el valor debe ser mayor a cero, intente de nuevo");
        }
    }

    // Valida que el número decimal sea positivo (mayor a cero)
    public static double validatePositiveDouble(String prompt) {
        while (true) {
            double value = validateDouble(prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("Error: el valor debe ser mayor a cero, intente de nuevo");
        }
    }

}

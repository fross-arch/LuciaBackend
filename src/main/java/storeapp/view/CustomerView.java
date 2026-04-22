package storeapp.view;

import storeapp.domain.Customer;
import storeapp.services.CustumerService;
import storeapp.utils.CustomerFormValidation;
import storeapp.domain.enums.CustomerTypeEnum;
import storeapp.domain.enums.CustomerState;
import java.util.List;

public class CustomerView {

    private final CustumerService customerService;

    public CustomerView(CustumerService customerService) {
        this.customerService = customerService;
    }

    public void createCustomer() {
        customerService.createCustomer();
    }

    public void getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No hay clientes registrados");
            return;
        }
        for (Customer c : customers) {
            System.out.println("Id: " + c.getId() +
                    " | Nombre: " + c.getName() +
                    " | Apellido: " + c.getLastName() +
                    " | Email: " + c.getEmail() +
                    " | Estado: " + (c.isStatus() ? "Activo" : "Inactivo") +
                    " | Cupo: " + c.getQuote() +
                    " | Tipo: " + c.getCustomerType());
        }
    }

    public void getCustomerById() {
        int id = CustomerFormValidation.validateInt("Ingrese el id del cliente a buscar");
        Customer c = customerService.getCustomerById(id);
        if (c != null) {
            System.out.println("Id: " + c.getId() +
                    " | Nombre: " + c.getName() +
                    " | Email: " + c.getEmail());
        } else {
            System.out.println("Cliente no encontrado");
        }
    }

    public void updateCustomer() {
        // Buscar cliente existente
        Customer existing = null;
        while (existing == null) {
            int id = CustomerFormValidation.validateInt("Ingrese el id del cliente a modificar");
            existing = customerService.getCustomerById(id);
            if (existing == null) {
                System.out.println("Cliente no encontrado, intente con otro id");
            }
        }

        System.out.println("Cliente encontrado: " + existing.getName());

        // Menú de campos a modificar
        boolean editing = true;
        while (editing) {
            int option = CustomerFormValidation.validateInt(
                    "¿Qué desea modificar?\n" +
                            "  1. Nombre\n" +
                            "  2. Apellido\n" +
                            "  3. Email\n" +
                            "  4. Password\n" +
                            "  5. Estado\n" +
                            "  6. Cupo\n" +
                            "  7. Tipo de cliente\n" +
                            "  8. Guardar y salir");

            switch (option) {
                case 1:
                    existing.setName(CustomerFormValidation.validateString("Nuevo nombre"));
                    break;
                case 2:
                    existing.setLastName(CustomerFormValidation.validateString("Nuevo apellido"));
                    break;
                case 3:
                    existing.setEmail(CustomerFormValidation.validateString("Nuevo email"));
                    break;
                case 4:
                    existing.setPassword(CustomerFormValidation.validateString("Nuevo password"));
                    break;
                case 5:
                    existing.setStatus(selectCustomerState());
                    break;
                case 6:
                    existing.setQuote(CustomerFormValidation.validatePositiveDouble("Nuevo cupo"));
                    break;
                case 7:
                    existing.setCustomerType(selectCustomerType());
                    break;
                case 8:
                    editing = false;
                    break;
                default:
                    System.out.println("Opción no válida, seleccione entre 1 y 8");
            }
        }

        customerService.updateCustomer(existing);
        System.out.println("Cliente actualizado correctamente");
    }

    public void deleteCustomer() {
        int id = CustomerFormValidation.validateInt("Ingrese el id del cliente a eliminar");
        customerService.deleteCustomer(id);
        System.out.println("Cliente eliminado correctamente");
    }

    // Menú para seleccionar estado
    private boolean selectCustomerState() {
        while (true) {
            int option = CustomerFormValidation.validateInt(
                    "Seleccione el estado:\n  1. Activo\n  2. Inactivo");
            switch (option) {
                case 1: return CustomerState.ACTIVE.getDescription();
                case 2: return CustomerState.INACTIVE.getDescription();
                default: System.out.println("Opción no válida, seleccione 1 o 2");
            }
        }
    }

    // Menú para seleccionar tipo de cliente
    private String selectCustomerType() {
        while (true) {
            int option = CustomerFormValidation.validateInt(
                    "Seleccione el tipo de cliente:\n  1. Cliente Nuevo\n  2. Cliente Antiguo\n  3. Cliente en mora");
            switch (option) {
                case 1: return CustomerTypeEnum.NEW_CUSTOMER.getDescription();
                case 2: return CustomerTypeEnum.OLD_CUSTOMER.getDescription();
                case 3: return CustomerTypeEnum.BLOCKED.getDescription();
                default: System.out.println("Opción no válida, seleccione 1, 2 o 3");
            }
        }
    }

}



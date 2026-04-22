package storeapp.services;

import storeapp.domain.Customer;
import storeapp.repository.CustomerRepository;
import storeapp.utils.CustomerFormValidation;
import storeapp.domain.enums.CustomerTypeEnum;
import storeapp.domain.enums.CustomerState;

import java.util.List;

public class CustumerServiceImpl implements CustumerService {

    // Ahora vamos a comunicar las clases, para eso creamos una instancia
    // de la capa inmediatamente anterior (repository)
    private final CustomerRepository customerRepository;

    public CustumerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer() {

        Customer customer = new Customer();

        // Validar que el id sea un número entero y que no exista ya
        int id;
        while (true) {
            id = CustomerFormValidation.validateInt("Ingrese el id del cliente");
            if (customerRepository.existsById(id)) {
                System.out.println("Error: ya existe un cliente con ese id, intente con otro");
            } else {
                break;
            }
        }
        customer.setId(id);

        customer.setName(CustomerFormValidation.validateString("Ingrese el nombre del cliente"));
        customer.setLastName(CustomerFormValidation.validateString("Ingrese el apellido del cliente"));
        customer.setEmail(CustomerFormValidation.validateString("Ingrese el email del cliente"));
        customer.setPassword(CustomerFormValidation.validateString("Ingrese el password del cliente"));
        customer.setStatus(CustomerFormValidation.validateBoolean("Estado del cliente (true = activo / false = inactivo)"));
        customer.setQuote(CustomerFormValidation.validatePositiveDouble("Ingrese el cupo del cliente"));
        customer.setCustomerType(CustomerFormValidation.validateString("Ingrese el tipo de cliente"));

        System.out.println("Cliente creado de forma exitosa");
        return customerRepository.saveCustomer(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAllCustomers();
    }

    @Override
    public Customer getCustomerById(int id) {
        return customerRepository.findCustomerById(id);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return null;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(int id) {
        customerRepository.deleteCustomer(id);
    }

    @Override
    public boolean existsById(int id) {
        return customerRepository.existsById(id);
    }

    // Menú para seleccionar el estado del cliente
    private boolean selectCustomerState() {
        while (true) {
            int option = CustomerFormValidation.validateInt(
                    "Seleccione el estado del cliente:\n  1. Activo\n  2. Inactivo");
            switch (option) {
                case 1: return CustomerState.ACTIVE.getDescription();
                case 2: return CustomerState.INACTIVE.getDescription();
                default: System.out.println("Opción no válida, seleccione 1 o 2");
            }
        }
    }

    // Menú para seleccionar el tipo de cliente
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




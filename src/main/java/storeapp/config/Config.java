package storeapp.config;

import storeapp.domain.Admin;
import storeapp.repository.CategoryRepository;
import storeapp.repository.CustomerRepository;
import storeapp.repository.ProductRepository;
import storeapp.services.*;
import storeapp.userinterface.MenuApp;
import storeapp.view.AdminView;
import storeapp.view.CategoryView;
import storeapp.view.CustomerView;
import storeapp.view.ProductView;

public class Config {

    public static MenuApp createMenuApp() {

        // Patrón Simple Factory: centraliza la creación de objetos.
        // Si se cambia una implementación, solo se modifica aquí.

        // Clientes
        Admin admin = new Admin();
        CustomerRepository customerRepository = new CustomerRepository();
        CustumerService customerService = new CustumerServiceImpl(customerRepository);
        CustomerView customerView = new CustomerView(customerService);
        AdminServiceImpl adminService = new AdminServiceImpl(admin);
        AdminView adminView = new AdminView(adminService, admin);

        // Categorías
        CategoryRepository categoryRepository = new CategoryRepository();
        CategoryView categoryView = new CategoryView(categoryRepository);

        // Productos
        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductServiceImpl(productRepository);
        ProductView productView = new ProductView(productService, categoryRepository);

        return new MenuApp(customerView, adminView, productView, categoryView);
    }

}

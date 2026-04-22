package storeapp.view;

import storeapp.domain.Category;
import storeapp.domain.Product;
import storeapp.repository.CategoryRepository;
import storeapp.services.ProductService;
import storeapp.utils.CustomerFormValidation;

import java.util.List;

public class ProductView {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    public ProductView(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    public void createProduct() {
        Product newProduct = new Product();

        // Validar id único
        int id;
        while (true) {
            id = CustomerFormValidation.validateInt("Ingrese el id del producto");
            if (productService.existsById(id)) {
                System.out.println("Error: ya existe un producto con ese id, intente con otro");
            } else {
                break;
            }
        }
        newProduct.setIdProduct(id);

        newProduct.setDescription(CustomerFormValidation.validateString("Ingrese el nombre del producto"));
        newProduct.setPrice(CustomerFormValidation.validatePositiveDouble("Ingrese el precio del producto"));
        newProduct.setStock(CustomerFormValidation.validatePositiveInt("Ingrese el stock del producto"));
        newProduct.setState(true);
        newProduct.setCategory(selectCategory());

        productService.saveProduct(newProduct);
        System.out.println("Producto creado exitosamente");
    }

    public void getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No hay productos registrados");
            return;
        }
        for (Product p : products) {
            System.out.println("Id: " + p.getIdProduct() +
                    " | Nombre: " + p.getDescription() +
                    " | Precio: " + p.getPrice() +
                    " | Stock: " + p.getStock() +
                    " | Categoría: " + p.getCategory().getDescription() +
                    " | Estado: " + (p.isState() ? "Disponible" : "No disponible"));
        }
    }

    public void getProductById() {
        Product existing = null;
        while (existing == null) {
            int id = CustomerFormValidation.validateInt("Ingrese el id del producto a buscar");
            existing = productService.getProductById(id);
            if (existing == null) {
                System.out.println("Producto no encontrado, intente con otro id");
            }
        }
        System.out.println("Id: " + existing.getIdProduct() +
                " | Nombre: " + existing.getDescription() +
                " | Precio: " + existing.getPrice() +
                " | Stock: " + existing.getStock() +
                " | Categoría: " + existing.getCategory().getDescription());
    }

    public void updateProduct() {
        // Buscar producto existente
        Product existing = null;
        while (existing == null) {
            int id = CustomerFormValidation.validateInt("Ingrese el id del producto a modificar");
            existing = productService.getProductById(id);
            if (existing == null) {
                System.out.println("Producto no encontrado, intente con otro id");
            }
        }

        System.out.println("Producto encontrado: " + existing.getDescription());

        existing.setDescription(CustomerFormValidation.validateString("Ingrese el nuevo nombre"));
        existing.setPrice(CustomerFormValidation.validatePositiveDouble("Ingrese el nuevo precio"));
        existing.setStock(CustomerFormValidation.validatePositiveInt("Ingrese el nuevo stock"));
        existing.setState(CustomerFormValidation.validateBoolean("Nuevo estado (true = disponible / false = no disponible)"));

        boolean changeCategory = CustomerFormValidation.validateBoolean("¿Desea cambiar la categoría? (true = sí / false = no)");
        if (changeCategory) {
            existing.setCategory(selectCategory());
        }

        productService.updateProduct(existing);
        System.out.println("Producto actualizado correctamente");
    }

    public void deleteProduct() {
        int id = CustomerFormValidation.validateInt("Ingrese el id del producto a eliminar");
        productService.deleteProduct(id);
        System.out.println("Producto eliminado correctamente");
    }

    // Método privado para seleccionar categoría mostrando las disponibles
    private Category selectCategory() {
        List<Category> categories = categoryRepository.findAllCatagories();
        System.out.println("Categorías disponibles:");
        for (Category c : categories) {
            System.out.println("  Id: " + c.getIdCategory() + " | " + c.getDescription());
        }

        Category selected = null;
        while (selected == null) {
            int id = CustomerFormValidation.validateInt("Ingrese el id de la categoría");
            selected = categoryRepository.findCategoryById(id);
            if (selected == null) {
                System.out.println("Categoría no encontrada, intente de nuevo");
            }
        }
        return selected;
    }

}

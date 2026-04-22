package storeapp.view;

import storeapp.domain.Category;
import storeapp.repository.CategoryRepository;
import storeapp.utils.CustomerFormValidation;

import java.util.List;

public class CategoryView {

    private final CategoryRepository categoryRepository;

    public CategoryView(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void createCategory() {
        Category newCategory = new Category();

        int id;
        while (true) {
            id = CustomerFormValidation.validateInt("Ingrese el id de la categoría");
            if (categoryRepository.existsById(id)) {
                System.out.println("Error: ya existe una categoría con ese id, intente con otro");
            } else {
                break;
            }
        }
        newCategory.setIdCategory(id);

        newCategory.setDescription(CustomerFormValidation.validateString("Ingrese el nombre de la categoría"));
        newCategory.setState(true);

        categoryRepository.saveCategory(newCategory);
        System.out.println("Categoría creada exitosamente");
    }

    public void getAllCategories() {
        List<Category> categories = categoryRepository.findAllCatagories();
        if (categories.isEmpty()) {
            System.out.println("No hay categorías registradas");
            return;
        }
        for (Category c : categories) {
            System.out.println("Id: " + c.getIdCategory() +
                    " | Nombre: " + c.getDescription() +
                    " | Estado: " + (c.isState() ? "Activa" : "Inactiva"));
        }
    }

    public void updateCategory() {
        Category existing = null;
        while (existing == null) {
            int id = CustomerFormValidation.validateInt("Ingrese el id de la categoría a modificar");
            existing = categoryRepository.findCategoryById(id);
            if (existing == null) {
                System.out.println("Categoría no encontrada, intente con otro id");
            }
        }

        System.out.println("Categoría encontrada: " + existing.getDescription());

        boolean editing = true;
        while (editing) {
            int option = CustomerFormValidation.validateInt(
                    "¿Qué desea modificar?\n" +
                            "  1. Nombre\n" +
                            "  2. Estado\n" +
                            "  3. Guardar y salir");

            switch (option) {
                case 1:
                    existing.setDescription(CustomerFormValidation.validateString("Nuevo nombre"));
                    break;
                case 2:
                    existing.setState(selectCategoryState());
                    break;
                case 3:
                    editing = false;
                    break;
                default:
                    System.out.println("Opción no válida, seleccione entre 1 y 3");
            }
        }

        categoryRepository.updateCategory(existing);
        System.out.println("Categoría actualizada correctamente");
    }

    private boolean selectCategoryState() {
        while (true) {
            int option = CustomerFormValidation.validateInt(
                    "Seleccione el estado:\n  1. Activa\n  2. Inactiva");
            switch (option) {
                case 1: return true;
                case 2: return false;
                default: System.out.println("Opción no válida, seleccione 1 o 2");
            }
        }
    }

}

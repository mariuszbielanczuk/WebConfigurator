package pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.customer.Customer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.product.ProductService.PRICINGFACTORSURFACE;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String showProductlist(Model model) {
        List<Product> listProducts = productService.listAll();
        model.addAttribute("listProducts", listProducts);
        return "products";

    }

    @GetMapping("/products/new")
    public String showNewForm(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("pageTitle", "Add New Product");
        model.addAttribute("action", "new");
        model.addAttribute("pricingFactorSurface", PRICINGFACTORSURFACE);
        return "product_form";
    }

    @GetMapping("/products/generate")
    public String generateCustomers(RedirectAttributes redirectAttributes) {
        productService.generate();
        redirectAttributes.addFlashAttribute("message", "Random Product has been generated.");
        return "redirect:/products";
    }

    @PostMapping("/products/save")
    public String saveProduct(Product product, Model model, RedirectAttributes redirectAttributes){
        try {
            productService.save(product);
            redirectAttributes.addFlashAttribute("message", "The product has been saved.");
            return "redirect:/products";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("message", "Product name exists.");
            model.addAttribute("pageTitle", "Add New Product");
            model.addAttribute("action", "new");
            model.addAttribute("pricingFactorSurface", PRICINGFACTORSURFACE);
            return "product_form";
        }
    }


    @GetMapping("/products/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Product product = productService.get(id);
            model.addAttribute("product", product);
            model.addAttribute("pageTitle", "Edit Product (ID: " + id + ")");
            model.addAttribute("pricingFactorSurface", PRICINGFACTORSURFACE);
            return "product_form";
        } catch (ProductNotFoudException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/products";
        }
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            productService.delete(id);
            redirectAttributes.addFlashAttribute("message", "The Product ID " + id + " has been deleted.");
        } catch (ProductNotFoudException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("message", "Cannot delete product. Product is assigned to at least one order.");
        }
        return "redirect:/products";
    }

}

package pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.product.Product;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public String showCustomerlist(Model model) {
        List<Customer> listCustomers = customerService.listAll();
        model.addAttribute("listCustomers", listCustomers);
        return "customers";

    }

    @GetMapping("/customers/new")
    public String showNewForm(Model model){
        model.addAttribute("customer", new Customer());
        model.addAttribute("pageTitle", "Add New Customer");
        return "customer_form";
    }

    @GetMapping("/customers/generate")
    public String generateCustomers(RedirectAttributes redirectAttributes) {
        customerService.generate();
        redirectAttributes.addFlashAttribute("message", "Random Customer has been generated.");
        return "redirect:/customers";
    }

    @PostMapping("/customers/save")
    public String saveCustomer(Customer customer, Model model, RedirectAttributes redirectAttributes){
        try {
            customerService.save(customer);
            redirectAttributes.addFlashAttribute("message", "The customer has been saved.");
            return "redirect:/customers";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("message", "Email is used by another customer. Please use a different Email.");
            model.addAttribute("pageTitle", "Add New Customer");
            model.addAttribute("action", "new");
            return "customer_form";
        }
    }

    @GetMapping("/customers/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Customer customer = customerService.get(id);
            model.addAttribute("customer", customer);
            model.addAttribute("pageTitle", "Edit Customer (ID: " + id + ")");
            return "customer_form";
        } catch (CustomerNotFoudException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/customers";
        }
    }

    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            customerService.delete(id);
            redirectAttributes.addFlashAttribute("message", "The Customer ID: " + id + " has been deleted.");
        } catch (CustomerNotFoudException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("message", "Cannot delete customer. Customer is assigned to at least one order.");
        }
        return "redirect:/customers";
    }
}

package pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.customer.Customer;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.customer.CustomerNotFoudException;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.customer.CustomerService;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.order.OrderNotFoudException;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.order.OrderService;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.product.Product;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.product.ProductNotFoudException;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.product.ProductService;

import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;


    @PostMapping("/items/save")
    public String saveItem(Item item, RedirectAttributes redirectAttributes) throws OrderNotFoudException, ProductNotFoudException {
        itemService.save(item);
        redirectAttributes.addFlashAttribute("message", "The item has been saved.");
        return "redirect:/orders/edit/"+item.getOrder().getId();
    }

    @GetMapping("/items/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Item item = null;
        try {
            item = itemService.get(id);
            model.addAttribute("item", item);
            model.addAttribute("pageTitle", "Edit Item (ID: " + id + ")");
            List<Product> products = productService.listAll();
            model.addAttribute("products", products);
            return "item_form";
        } catch (ItemNotFoudException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/orders/edit/" + item.getOrder().getId();
        }
    }


    @GetMapping("/items/delete/{id}")
    public String deleteItem(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Integer orderId = null;
        try {
            orderId = itemService.get(id).getOrder().getId();
            itemService.delete(id);

            redirectAttributes.addFlashAttribute("message", "Item has been deleted.");
        } catch (ItemNotFoudException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/orders/edit/" + orderId;
    }
}

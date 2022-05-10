package pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.customer.Customer;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.customer.CustomerService;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.item.Item;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.item.ItemService;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.product.Product;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.product.ProductService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;


    @GetMapping("/orders")
    public String showOrderlist(Model model) {
        List<Order> listOrders = orderService.listAll();
        model.addAttribute("listOrders", listOrders);
        return "orders";
    }

    @GetMapping("/orders/")
    public String showOrderWithId(@RequestParam(value="id", required = false) Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            List<Order> listOrders = Collections.singletonList(orderService.get(id));
            model.addAttribute("listOrders", listOrders);
            return "orders";
        } catch (OrderNotFoudException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/orders";
        }
    }

    @GetMapping("/customers/{id}/orders")
    public String showCustomerOrderlist(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        List<Order> listOrders = orderService.listAllByCustomerId(id);
        model.addAttribute("listOrders", listOrders);
        model.addAttribute("message", "Displaying all orders of Customer ID: " + id);
        return "orders";
    }

    @GetMapping("/orders/generate")
    public String generateOrders(RedirectAttributes redirectAttributes) {
        orderService.generate();
        redirectAttributes.addFlashAttribute("message", "Random Order has been generated.");
        return "redirect:/orders";
    }

    @GetMapping("/orders/new")
    public String showNewOrderForm(Model model){
        Order order = new Order();
        order.setDate(LocalDate.now());
        order.setStatus(OrderStatus.NEW);
        model.addAttribute("order", order);
        model.addAttribute("pageTitle", "Create New Order");
        List<Customer> customers = customerService.listAll();
        model.addAttribute("customers", customers);
        return "order_new_form";
    }

    @GetMapping("/orders/edit/{id}")
    public String showEditOrderForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Order order = orderService.get(id);
            model.addAttribute("order", order);
            model.addAttribute("pageTitle", "Edit Order (ID: " + id + ")");
//            model.addAttribute("action", "edit");
            List<Customer> customers = customerService.listAll();
            model.addAttribute("customers", customers);
            List<Item> items = itemService.listAllByOrderId(id);
            model.addAttribute("items", items);
            return "order_edit_form";
        } catch (OrderNotFoudException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/orders";
        }
    }

    @GetMapping("/orders/{id}/items/new")
    public String showNewItemForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Item item = new Item();
            item.setOrder(orderService.get(id));
            model.addAttribute("item", item);
            Order order = orderService.get(id);
            model.addAttribute("order", order);
            model.addAttribute("pageTitle", "Add new item to order (ID: " + id + ")");
            List<Product> products = productService.listAll();
            model.addAttribute("products", products);
            model.addAttribute("action", "new");
            return "item_form";
        } catch (OrderNotFoudException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/orders/edit/{id}";
        }
    }



    @PostMapping("/orders/save")
    public String saveOrder(Order order, RedirectAttributes redirectAttributes){
        order.setItems(itemService.listAllByOrderId(order.getId()));
//        order.setCustomer(order.getCustomer());
        orderService.save(order);
        redirectAttributes.addFlashAttribute("message", "The order has been saved. Order ID: "+ order.getId());
        return "redirect:/orders";
    }

    @PostMapping("/orders/save_new")
    public String saveNewOrder(Order order, RedirectAttributes redirectAttributes){
//        order.setItems(itemService.listAllByOrderId(order.getId()));
//        order.setCustomer(order.getCustomer());
        orderService.save(order);
        redirectAttributes.addFlashAttribute("message", "The order has been saved. Order ID: "+ order.getId());
        return "redirect:/orders/edit/"+order.getId();
    }

    @GetMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            orderService.delete(id);
            redirectAttributes.addFlashAttribute("message", "The Order ID " + id + " has been deleted.");
        } catch (OrderNotFoudException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/orders";
    }
}

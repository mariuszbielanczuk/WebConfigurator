package pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.customer.Customer;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.customer.CustomerService;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.item.Item;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.item.ItemRepository;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.item.ItemService;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.product.Product;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.product.ProductNotFoudException;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.product.ProductService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    public List<Order> listAll() {
        return (List<Order>) orderRepository.findAll();
    }

    public List<Order> listAllByCustomerId(Integer id){
        return (List<Order>) orderRepository.findAllOrderByCustomerId(id);
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public Order get(Integer id) throws OrderNotFoudException {
        Optional<Order> result = orderRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new OrderNotFoudException("No order with ID " + id + " found.");
    }

    public void delete(Integer id) throws OrderNotFoudException {
        Long count = orderRepository.countById(id);
        if (count == null || count == 0) {
            throw new OrderNotFoudException("No order with ID " + id + " found.");
        }
        orderRepository.deleteById(id);
    }

    public void generate() {

        List<Item> items = new ArrayList<>();
        Random r = new Random();
        Customer selectedCustomer = customerService.getRandom();

        Order order = Order.builder()
                .date(LocalDate.now())
                .status(OrderStatus.NEW)
                .customer(selectedCustomer)
                .build();

        orderRepository.save(order);

        for (int i = 0; i < r.nextInt(6) + 1; i++) {
            //create an item with random product from Products table

            Product selectedProduct = productService.getRandom();
            Item item = Item.builder()
                    .quantity(r.nextInt(11) + 1)
                    .order(order)
                    .product(selectedProduct)
                    .build();
            items.add(item);

        }
        order.setItems(items);
        itemRepository.saveAll(items);
        orderRepository.save(order);
    }

}

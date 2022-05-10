package pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.customer.Customer;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.order.Order;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.product.Product;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.product.ProductNotFoudException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> listAll(){
        return (List<Item>) itemRepository.findAll();
    }

    public List<Item> listAllByOrderId(Integer id){
        return (List<Item>) itemRepository.findAllItemByOrderId(id);
    }

    public void save(Item item) {
        itemRepository.save(item);
    }

//    public void deleteByOrderId(Integer orderId) throws ItemNotFoudException {
//        Long count = itemRepository.countById(orderId);
//        if (count == null || count == 0) {
//            throw new ItemNotFoudException("No items with Order ID " + orderId +" found.");
//        }
//        itemRepository.deleteByOrderId(orderId);
//    }


    public Item get(Integer id) throws ItemNotFoudException {
        Optional<Item> result = itemRepository.findById(id);
        if (result.isPresent()){
            return result.get();
        }
        throw new ItemNotFoudException("No item with ID " + id +" found.");
    }

    public void delete(Integer id) throws ItemNotFoudException {
        Long count = itemRepository.countById(id);
        if (count == null || count == 0) {
            throw new ItemNotFoudException("No item with ID " + id +" found.");
        }
        itemRepository.deleteById(id);
    }
}

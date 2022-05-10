package pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.order;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.customer.Customer;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.item.Item;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    Long countById(Integer id);

    @Query("select o from Order o where o.customer.id = ?1")
    List<Order> findAllOrderByCustomerId(Integer customerId);

}

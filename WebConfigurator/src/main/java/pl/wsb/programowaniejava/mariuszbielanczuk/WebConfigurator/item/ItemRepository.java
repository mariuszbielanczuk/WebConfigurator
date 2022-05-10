package pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.item;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.order.Order;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Integer> {
    Long countById(Integer id);

    @Query("select i from Item i where i.order.id = ?1")
    List<Item> findAllItemByOrderId(Integer orderId);



}

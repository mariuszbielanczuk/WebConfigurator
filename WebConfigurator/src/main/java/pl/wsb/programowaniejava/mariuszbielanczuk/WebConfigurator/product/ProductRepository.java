package pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.product;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    Long countById(Integer id);

    @Query("FROM Product ORDER BY Rand()")
    List<Product> getRandomProduct();

}

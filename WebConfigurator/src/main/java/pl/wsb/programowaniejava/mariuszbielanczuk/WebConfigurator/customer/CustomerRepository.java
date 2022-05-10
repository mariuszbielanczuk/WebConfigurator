package pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.customer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Long countById(Integer id);

    @Query("FROM Customer ORDER BY Rand()")
    List<Customer> getRandomCustomer();




}

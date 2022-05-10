package pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.customer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    public List<Customer> listAll() {
        return (List<Customer>) customerRepository.findAll();
    }

    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer get(Integer id) throws CustomerNotFoudException {
        Optional<Customer> result = customerRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new CustomerNotFoudException("No customer with ID " + id + " found.");
    }

    public Customer getRandom() {
        List<Customer> result = customerRepository.getRandomCustomer();
        return result.get(0);
    }

    public void delete(Integer id) throws CustomerNotFoudException {
        Long count = customerRepository.countById(id);
        if (count == null || count == 0) {
            throw new CustomerNotFoudException("No customer with ID " + id + " found.");
        }
        customerRepository.deleteById(id);
    }


    public void generate() {

        List<String> firstNames = List.of(
                "Anna", "Piotr",
                "Maria", "Krzysztof",
                "Katarzyna", "Andrzej",
                "Małgorzata", "Jan",
                "Agnieszka", "Stanisław",
                "Barbara", "Tomasz",
                "Krystyna", "Paweł",
                "Ewa", "Marcin",
                "Elżbieta", "Michał",
                "Zofia", "Marek",
                "Teresa", "Grzegorz",
                "Magdalena", "Józef",
                "Joanna", "Łukasz",
                "Janina", "Adam",
                "Monika", "Zbigniew",
                "Danuta", "Jerzy",
                "Jadwiga", "Tadeusz",
                "Aleksandra", "Mateusz",
                "Halina", "Dariusz",
                "Irena", "Mariusz",
                "Beata", "Wojciech",
                "Marta", "Ryszard",
                "Dorota", "Jakub",
                "Helena", "Henryk",
                "Karolina", "Robert",
                "Grażyna", "Rafał",
                "Jolanta", "Kazimierz",
                "Iwona", "Jacek",
                "Marianna", "Maciej",
                "Natalia", "Kamil");

        List<String> lastNames = List.of("Kowal", "Nowak", "Wójcik", "Woźniak", "Mazur", "Krawczyk", "Kaczmarek");
        Random r = new Random();

        Customer customer = Customer.builder()
                .firstName(firstNames.get(r.nextInt(firstNames.size())))
                .lastName(lastNames.get(r.nextInt(lastNames.size())))
                .build();
        customer.setEmail(StringUtils.stripAccents(customer.getFirstName().toLowerCase() + "." + customer.getLastName().toLowerCase() + "@gmail.com"));
        customerRepository.save(customer);
    }
}

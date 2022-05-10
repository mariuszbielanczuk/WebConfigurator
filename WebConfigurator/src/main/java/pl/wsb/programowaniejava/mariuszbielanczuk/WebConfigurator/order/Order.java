package pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.order;

import lombok.*;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.customer.Customer;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.item.Item;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@ToString
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;

    @Column(name = "status")
    private OrderStatus status;

}

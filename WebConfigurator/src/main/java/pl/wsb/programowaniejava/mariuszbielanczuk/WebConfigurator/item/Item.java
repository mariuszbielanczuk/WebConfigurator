package pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.item;


import lombok.*;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.order.Order;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.product.Product;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@ToString
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column (name = "quantity")
    private int quantity;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;


}

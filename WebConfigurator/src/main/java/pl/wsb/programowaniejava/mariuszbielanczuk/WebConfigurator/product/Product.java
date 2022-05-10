package pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.product;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@ToString
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "type")
    private ProductType type;

    @Column(length = 40, nullable = false, unique = true, name = "name")
    private String name;

    @Column(length = 4, nullable = false, name = "width")
    private int width;

    @Column(length = 4, nullable = false, name = "height")
    private int height;

    @Column(name = "price", nullable = false)
    private BigDecimal price;


}

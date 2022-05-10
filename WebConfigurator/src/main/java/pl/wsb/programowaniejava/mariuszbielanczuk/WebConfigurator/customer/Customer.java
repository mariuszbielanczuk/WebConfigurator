package pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.customer;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@ToString
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 25, nullable = false, name = "first_name")
    private String firstName;

    @Column(length = 25, nullable = false, name = "last_name")
    private String lastName;

    @Column(length = 40, nullable = false, unique = true)
    private String email;

}

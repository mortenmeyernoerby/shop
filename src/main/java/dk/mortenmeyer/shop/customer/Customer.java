package dk.mortenmeyer.shop.customer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import dk.mortenmeyer.shop.order.PurchaseOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @OneToMany
    @JoinColumn(name = "fk_customer")
    private List<PurchaseOrder> orders = new ArrayList<>();

    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void addOrder(PurchaseOrder order) {
        orders.add(order);
    }
}

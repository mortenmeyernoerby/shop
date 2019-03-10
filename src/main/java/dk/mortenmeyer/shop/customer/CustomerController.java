package dk.mortenmeyer.shop.customer;

import dk.mortenmeyer.shop.customer.representations.CustomerOrderRepresentation;
import dk.mortenmeyer.shop.exceptions.CustomerNotFoundException;
import dk.mortenmeyer.shop.exceptions.PurchaseOrderNotFoundException;
import dk.mortenmeyer.shop.order.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerOrderMappingService customerOrderMappingService;

    @GetMapping("/customers")
    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/customers/{customerId}")
    public Customer getCustomer(@PathVariable Long customerId) {
        return customerRepository
            .findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    @GetMapping("/customers/{customerId}/orders")
    public List<PurchaseOrder> getCustomerOrders(@PathVariable Long customerId) {
        return customerRepository
            .findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException(customerId))
            .getOrders();
    }

    @GetMapping("/customers/{customerId}/orders/{orderId}")
    public CustomerOrderRepresentation getCustomerOrder(@PathVariable Long customerId, @PathVariable Long orderId) {
        Customer customer = customerRepository
            .findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException(customerId));

        PurchaseOrder order = customer.getOrders().stream()
                .filter(o -> o.getId() == orderId)
                .findFirst()
                .orElseThrow(() -> new PurchaseOrderNotFoundException(orderId));

        return customerOrderMappingService.map(customer, order);
    }
}

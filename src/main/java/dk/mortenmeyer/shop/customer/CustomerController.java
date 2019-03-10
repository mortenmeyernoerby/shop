package dk.mortenmeyer.shop.customer;

import dk.mortenmeyer.shop.customer.representations.CustomerHal;
import dk.mortenmeyer.shop.customer.representations.CustomerOrderRepresentation;
import dk.mortenmeyer.shop.exceptions.CustomerNotFoundException;
import dk.mortenmeyer.shop.exceptions.PurchaseOrderNotFoundException;
import dk.mortenmeyer.shop.order.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerOrderMappingService customerOrderMappingService;

    @GetMapping("/customers")
    public Resources<CustomerHal> getAllCustomers() {
        return toCustomerHal(customerRepository.findAll());
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

    private Resources<CustomerHal> toCustomerHal(Iterable<Customer> customers) {
        List<CustomerHal> customersHal = StreamSupport.stream(customers.spliterator(), false)
                .map(customer -> CustomerHal.builder()
                        .customerId(customer.getId())
                        .name(customer.getName())
                        .address(customer.getAddress())
                        .build())
                .collect(Collectors.toList());

        customersHal.forEach(customerHal -> {
            Link link = linkTo(methodOn(CustomerController.class).getCustomer(customerHal.getCustomerId())).withSelfRel();
            customerHal.add(link);
        });

        Link selfLink = linkTo(methodOn(CustomerController.class).getAllCustomers()).withSelfRel();
        return new Resources<>(customersHal, selfLink);
    }
}

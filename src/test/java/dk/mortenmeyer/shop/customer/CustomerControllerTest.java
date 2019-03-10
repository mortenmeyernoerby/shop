package dk.mortenmeyer.shop.customer;

import dk.mortenmeyer.shop.customer.representations.CustomerOrderRepresentation;
import dk.mortenmeyer.shop.exceptions.CustomerNotFoundException;
import dk.mortenmeyer.shop.exceptions.PurchaseOrderNotFoundException;
import dk.mortenmeyer.shop.order.PurchaseOrder;
import org.hamcrest.core.Is;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerOrderMappingService customerOrderMappingService;

    @InjectMocks
    private CustomerController customerController;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void shouldGetCustomerOrder() {
        Customer customer = mock(Customer.class);
        PurchaseOrder order = mock(PurchaseOrder.class);
        when(order.getId()).thenReturn(1L);
        when(customer.getOrders()).thenReturn(Collections.singletonList(order));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerOrderRepresentation customerOrderRepresentation = mock(CustomerOrderRepresentation.class);
        when(customerOrderMappingService.map(customer, order)).thenReturn(customerOrderRepresentation);

        CustomerOrderRepresentation customerOrder = customerController.getCustomerOrder(1L, 1L);

        assertThat(customerOrder, Is.is(customerOrderRepresentation));
    }

    @Test
    public void shouldThrowCustomerNotFoundException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        exceptionRule.expect(CustomerNotFoundException.class);
        exceptionRule.expectMessage("Customer with id:[1] not found");

        customerController.getCustomerOrder(1L, 1L);
    }

    @Test
    public void shouldThrowOrderNotFoundException() {
        Customer customer = mock(Customer.class);
        when(customer.getOrders()).thenReturn(Collections.emptyList());
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        exceptionRule.expect(PurchaseOrderNotFoundException.class);
        exceptionRule.expectMessage("Order with id:[1] not found");

        customerController.getCustomerOrder(1L, 1L);
    }
}
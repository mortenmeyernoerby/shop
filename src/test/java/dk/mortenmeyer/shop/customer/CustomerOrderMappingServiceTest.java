package dk.mortenmeyer.shop.customer;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import dk.mortenmeyer.shop.item.Item;
import dk.mortenmeyer.shop.order.PurchaseOrder;
import dk.mortenmeyer.shop.order.PurchaseOrderRepository;
import dk.mortenmeyer.shop.customer.representations.CustomerOrderRepresentation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CustomerOrderMappingServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PurchaseOrderRepository purchaseOrderRepository;

    @InjectMocks
    private CustomerOrderMappingService customerOrderMappingService;

    @Test
    public void shouldReturnCustomerOrderRepresentation() {
        Customer customer = mock(Customer.class);
        when(customer.getId()).thenReturn(1L);

        Item item = mock(Item.class);
        when(item.getId()).thenReturn(1L);
        when(item.getName()).thenReturn("MyItem");
        when(item.getPrice()).thenReturn(200d);

        PurchaseOrder order = mock(PurchaseOrder.class);
        when(order.getId()).thenReturn(2L);
        when(order.getDiscount()).thenReturn(50d);
        when(order.getTotalPrice()).thenReturn(150d);
        when(order.getItems()).thenReturn(Collections.singletonList(item));

        CustomerOrderRepresentation result = customerOrderMappingService.map(customer, order);

        assertThat(result.getCustomerId(), is(1L));
        assertThat(result.getOrderId(), is(2L));
        assertThat(result.getDiscount(), is(50d));
        assertThat(result.getTotalPrice(), is(150d));
        assertThat(result.getItems(), hasSize(1));
        assertThat(result.getItems().get(0).getId(), is(1L));
        assertThat(result.getItems().get(0).getName(), is("MyItem"));
        assertThat(result.getItems().get(0).getPrice(), is(200d));
    }

}
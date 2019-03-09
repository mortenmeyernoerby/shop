package dk.mortenmeyer.shop.order;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import dk.mortenmeyer.shop.item.Item;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class PurchaseOrderTest {

    private PurchaseOrder order;

    @Before
    public void setup() {
        order = new PurchaseOrder();

        Item itemA = Mockito.mock(Item.class);
        Item itemB = Mockito.mock(Item.class);
        when(itemA.getId()).thenReturn(0L);
        when(itemA.getPrice()).thenReturn(200d);
        when(itemB.getId()).thenReturn(1L);
        when(itemB.getPrice()).thenReturn(300d);

        order.addItem(itemA);
        order.addItem(itemB);
    }

    @Test
    public void shouldReturnItems() {
        List<Item> items = order.getItems();

        assertThat(items, hasSize(2));
        assertThat(items.get(0).getId(), is(0L));
        assertThat(items.get(1).getId(), is(1L));
    }

    @Test
    public void shouldGetTotalPriceWhenNoDiscount() {
        double totalPriceNoDiscount = order.getTotalPrice();

        assertThat(totalPriceNoDiscount, is(500d));
    }

    @Test
    public void shouldGetTotalPriceWhenWithDiscount() {
        order.setDiscount(150);

        double totalPriceWithDiscount = order.getTotalPrice();

        assertThat(totalPriceWithDiscount, is(350d));
    }
}
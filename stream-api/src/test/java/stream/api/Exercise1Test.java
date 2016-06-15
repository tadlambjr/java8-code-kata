package stream.api;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import common.test.tool.annotation.Necessity;
import common.test.tool.dataset.ClassicOnlineStore;
import common.test.tool.entity.Customer;
import common.test.tool.util.AssertUtil;
import org.junit.Test;

import java.util.List;
import java.util.function.*;
import java.util.stream.*;

public class Exercise1Test extends ClassicOnlineStore {

    @Test
    @Necessity(true)
    public void findRichCustomers() {
        List<Customer> customerList = this.mall.getCustomerList();

        /**
         * Create a {@link Stream} from customerList only including customer who has more budget than 10000.
         * Use lambda expression for Predicate and {@link Stream#filter} for filtering.
         */
        Predicate<Customer> richCustomerCondition = customer -> customer.getBudget() > 10000;
        Stream<Customer> richCustomerStream = customerList.parallelStream().filter(richCustomerCondition);

        assertTrue("Solution for Predicate should be lambda expression", AssertUtil.isLambda(richCustomerCondition));
        List<Customer> richCustomer = richCustomerStream.collect(Collectors.toList());
        assertThat(richCustomer, hasSize(2));
        assertThat(richCustomer, contains(customerList.get(3), customerList.get(7)));
    }

    @Test
    @Necessity(true)
    public void howOldAreTheCustomers() {
        List<Customer> customerList = this.mall.getCustomerList();

        /**
         * Create a {@link Stream} from customerList with age values.
         * Use method reference(best) or lambda expression(okay) for creating {@link Function} which will
         * convert {@link Customer} to {@link Integer}, and then apply it by using {@link Stream#map}.
         */
        Function<Customer, Integer> getAgeFunction = Customer::getAge;
        Stream<Integer> ageStream = customerList.parallelStream().map(getAgeFunction);

        assertTrue(AssertUtil.isLambda(getAgeFunction));
        List<Integer> richCustomer = ageStream.collect(Collectors.toList());
        assertThat(richCustomer, hasSize(10));
        assertThat(richCustomer, contains(22, 27, 28, 38, 26, 22, 32, 35, 21, 36));
    }
}

package collection.interfaces;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import common.test.tool.annotation.Necessity;
import org.junit.Test;

import java.util.*;
import java.util.function.BiFunction;

public class Exercise2Test {

    private final Map<String, Integer> map = new HashMap<String, Integer>() {{
        put("Joe", 22);
        put("Steven", 27);
        put("Patrick", 28);
        put("Chris", 26);
    }};

    @Test
    @Necessity(true)
    public void getDefaultValue() {
        Map<String, Integer> map = new HashMap<>(this.map);

        /**
         * Try to get from key "Alice" using {@link Map#getOrDefault}. If the key doesn't exist, use 30 as default.
         */
        Integer defaultVal = map.getOrDefault("Alice", 3);

        assertThat(defaultVal, is(30));
    }

    @Test
    @Necessity(true)
    public void putIfNotExisting() {
        Map<String, Integer> map = new HashMap<>(this.map);

        /**
         * Try to put 2 entry with key as "Alice" value as 32, key as "Joe" and value as 32 using {@link Map#putIfAbsent}.
         */
        map.putIfAbsent("Alice", 32);
        map.putIfAbsent("Joe", 32);

        assertThat(map.get("Alice"), is(32));
        assertThat(map.get("Joe"), is(22));
    }

    @Test
    @Necessity(true)
    public void mergeValues() {
        Map<String, Integer> map = new HashMap<>(this.map);

        /**
         * Merge 2 entry to {@link map} with key as "Alice" value as 32, key as "Joe" and value as 32 using {@link Map#merge}.
         * If the value already exist for the key, remap with sum value.
         */
        BiFunction<Object, Object, Integer> remappingFunction = (first, second) -> (Integer) first + (Integer) second;
        map.merge("Alice", 32, remappingFunction);
        map.merge("Joe", 32, remappingFunction);

        assertThat(map.get("Alice"), is(32));
        assertThat(map.get("Joe"), is(54));
    }

    @Test
    @Necessity(true)
    public void ignoringAbsentKeys() {
        Map<String, Integer> map = new HashMap<>(this.map);

        /**
         * Try to increment the value for keys "Joe", "Steven" and "Alice".
         */
        BiFunction<Object, Object, Integer> remappingFunction = (first, second) -> (Integer) second + 1;
        map.computeIfPresent("Joe", remappingFunction);
        map.computeIfPresent("Steven", remappingFunction);
        map.computeIfPresent("Alice", remappingFunction);

        assertThat(map.get("Joe"), is(23));
        assertThat(map.get("Steven"), is(28));
        assertThat(map, not(hasKey("Alice")));
    }
}

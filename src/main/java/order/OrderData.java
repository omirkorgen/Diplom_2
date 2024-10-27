package order;

import java.util.List;

public class OrderData {
    public static final List<String> INGREDIENT = List.of(
            "61c0c5a71d1f82001bdaaa6d",
            "61c0c5a71d1f82001bdaaa6f",
            "61c0c5a71d1f82001bdaaa72"
    );

    public static final List<String> INGREDIENT_WITH_WRONG_HASH = List.of(
            "61c0c5a71d1f82001bdaa45454",
            "61c0c5a71d1f82001bdaaa6f",
            "61c0c5a71d1f82001bdaaa72"
    );
}

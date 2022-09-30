import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {
    public static final List<String> countriesList = new ArrayList<String>(List.of("UK", "Germany"));
    public static final List<String> productType = new ArrayList<String>(List.of("Mask", "Gloves"));
    public static final Integer transportCost = 400;
    public static final Integer discount = 20;
    public static final Map<String, Integer> quantity_and_price =new HashMap<>();

    static {
        quantity_and_price.put("uk_mask_quantity", 100);
        quantity_and_price.put("uk_gloves_quantity", 100);
        quantity_and_price.put("uk_mask_price", 65);
        quantity_and_price.put("uk_gloves_price", 100);

        quantity_and_price.put("germany_mask_quantity", 100);
        quantity_and_price.put("germany_gloves_quantity", 50);
        quantity_and_price.put("germany_mask_price", 100);
        quantity_and_price.put("germany_gloves_price", 150);
    }
}

import java.sql.ClientInfoStatus;
import java.util.*;

public class ZonoTest {

    public static void main(String[] args) {
        OrderDetails od = new OrderDetails();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter order input:");
        String orderString = sc.nextLine();
        String[] orderArr = orderString.split(":");
        Integer masks_total_price = null;
        Integer gloves_total_price = null;
        LinkedList<Integer> list = new LinkedList();
        boolean sameOrderAndPassCountry = false;

        od = orderDetailsExtractor(orderArr);

        if(od.getPassportCountry().equals(od.getOrderCountry())){
            sameOrderAndPassCountry = true;
            if(od.getPassportCountry().equals(Constants.countriesList.get(0))){
                Integer mask_quantity = Constants.quantity_and_price.get("uk_mask_quantity");
                Integer mask_price = Constants.quantity_and_price.get("uk_mask_price");
                Integer gloves_quantity = Constants.quantity_and_price.get("uk_gloves_quantity");
                Integer gloves_price = Constants.quantity_and_price.get("uk_gloves_price");
                list = getUkMaskTotalPrice(od, mask_quantity, mask_price, sameOrderAndPassCountry);
                masks_total_price = list.get(0);
                list.addAll(getUkGlovesTotalPrice(od, gloves_quantity, gloves_price, sameOrderAndPassCountry));
                gloves_total_price = list.get(3);


            } else {
                Integer mask_quantity = Constants.quantity_and_price.get("germany_mask_quantity");
                Integer mask_price = Constants.quantity_and_price.get("germany_mask_price");
                Integer gloves_quantity = Constants.quantity_and_price.get("germany_gloves_quantity");
                Integer gloves_price = Constants.quantity_and_price.get("germany_gloves_price");
                list = getGermanyMaskTotalPrice(od, mask_quantity, mask_price, sameOrderAndPassCountry);
                masks_total_price = list.get(0);
                list.addAll(getGermanyGlovesTotalPrice(od, gloves_quantity, gloves_price, sameOrderAndPassCountry));
                gloves_total_price = list.get(3);
            }
        } else {
            if(od.getPassportCountry().equals(Constants.countriesList.get(0))){
                Integer mask_quantity = Constants.quantity_and_price.get("uk_mask_quantity");
                Integer mask_price = Constants.quantity_and_price.get("uk_mask_price");
                Integer gloves_quantity = Constants.quantity_and_price.get("uk_gloves_quantity");
                Integer gloves_price = Constants.quantity_and_price.get("uk_gloves_price");
                list = getUkMaskTotalPrice(od, mask_quantity, mask_price, sameOrderAndPassCountry);
                masks_total_price = list.get(0);
                list.addAll(getUkGlovesTotalPrice(od, gloves_quantity, gloves_price, sameOrderAndPassCountry));
                gloves_total_price = list.get(3);
            } else {
                Integer mask_quantity = Constants.quantity_and_price.get("germany_mask_quantity");
                Integer mask_price = Constants.quantity_and_price.get("germany_mask_price");
                Integer gloves_quantity = Constants.quantity_and_price.get("germany_gloves_quantity");
                Integer gloves_price = Constants.quantity_and_price.get("germany_gloves_price");
                list = getGermanyMaskTotalPrice(od, mask_quantity, mask_price, sameOrderAndPassCountry);
                masks_total_price = list.get(0);
                list.addAll(getGermanyGlovesTotalPrice(od, gloves_quantity, gloves_price, sameOrderAndPassCountry));
                gloves_total_price = list.get(3);
            }
        }

        System.out.println(masks_total_price + gloves_total_price + ":" + list.get(1) + ":" + list.get(2) + ";" +list.get(4)+ ":" + list.get(5));

        System.out.println(od.getGlovesOrderQuantity()+"+"+od.getMasksOrderQuantity()+"+"+od.getOrderCountry()+"+"+od.getPassportCountry());
    }

    private static LinkedList<Integer> getGermanyGlovesTotalPrice(OrderDetails od, Integer gloves_quantity, Integer gloves_price, boolean flag) {
        LinkedList<Integer> list = new LinkedList<>();
        Integer gloves_total_price;
        if(od.getGlovesOrderQuantity() > gloves_quantity){
            list = flag
                    ? getGermanyGlovesTotalPriceWithoutDiscount(od, gloves_quantity, gloves_price)
                    : getGermanyGlovesTotalPriceWithDiscount(od, gloves_quantity, gloves_price);
            return list;
        } else {
            gloves_total_price = od.getGlovesOrderQuantity() * gloves_price;
            list.add(gloves_total_price);
            list.add(Constants.quantity_and_price.get("uk_gloves_quantity"));
            list.add(Constants.quantity_and_price.get("germany_mask_quantity") - od.getGlovesOrderQuantity());
            return list;
        }
    }

    private static LinkedList<Integer> getGermanyGlovesTotalPriceWithDiscount(OrderDetails od, Integer gloves_quantity, Integer gloves_price) {
        LinkedList<Integer> list = new LinkedList<>();
        Integer gloves_total_price;
        Integer extra = od.getGlovesOrderQuantity() - gloves_quantity;
        Integer batches = extra/10;
        Integer transportCost = batches * Constants.transportCost * (100 - Constants.discount) / 100;
        Integer extra_price = extra * Constants.quantity_and_price.get("uk_gloves_price") + transportCost;
        gloves_total_price = gloves_quantity + gloves_price + extra_price;
        list.add(gloves_total_price);
        list.add(Constants.quantity_and_price.get("uk_gloves_quantity") - extra);
        list.add(0);
        return list;
    }

    private static LinkedList<Integer> getGermanyGlovesTotalPriceWithoutDiscount(OrderDetails od, Integer gloves_quantity, Integer gloves_price) {
        LinkedList<Integer> list = new LinkedList<>();
        Integer gloves_total_price;
        Integer extra = od.getGlovesOrderQuantity() - gloves_quantity;
        Integer batches = extra/10;
        Integer transportCost = batches * Constants.transportCost;
        Integer extra_price = extra * Constants.quantity_and_price.get("uk_gloves_price") + transportCost;
        gloves_total_price = gloves_quantity + gloves_price + extra_price;
        list.add(gloves_total_price);
        list.add(Constants.quantity_and_price.get("uk_gloves_quantity") - extra);
        list.add(0);
        return list;
    }

    private static LinkedList<Integer> getUkGlovesTotalPrice(OrderDetails od, Integer gloves_quantity, Integer gloves_price, boolean flag) {
        Integer gloves_total_price;
        LinkedList<Integer> list = new LinkedList();
        if(od.getGlovesOrderQuantity() > gloves_quantity){
            list = flag
                    ? getGlovesTotalPriceWithoutDiscount(od, gloves_quantity, gloves_price)
                    : getGlovesTotalPriceWithDiscount(od, gloves_quantity, gloves_price);
            return list;
        } else {
            gloves_total_price = od.getGlovesOrderQuantity() * gloves_price;
            list.add(gloves_total_price);
            list.add(Constants.quantity_and_price.get("uk_gloves_quantity") - od.getGlovesOrderQuantity());
            list.add(Constants.quantity_and_price.get("germany_gloves_quantity"));
            return list;
        }
    }

    private static LinkedList<Integer> getGlovesTotalPriceWithDiscount(OrderDetails od, Integer gloves_quantity, Integer gloves_price) {
        LinkedList<Integer> list = new LinkedList<>();
        Integer gloves_total_price;
        Integer extra = od.getGlovesOrderQuantity() - gloves_quantity;
        Integer batches = extra/10;
        Integer transportCost = batches * Constants.transportCost * (100 - Constants.discount) / 100;
        Integer extraPrice = extra * Constants.quantity_and_price.get("germany_gloves_price") + transportCost;
        gloves_total_price = gloves_quantity * gloves_price + extraPrice;
        list.add(gloves_total_price);
        list.add(0);
        list.add(Constants.quantity_and_price.get("germany_gloves_quantity") - extra);
        return list;
    }

    private static LinkedList<Integer> getGlovesTotalPriceWithoutDiscount(OrderDetails od, Integer gloves_quantity, Integer gloves_price) {
        LinkedList<Integer> list = new LinkedList<>();
        Integer gloves_total_price;
        Integer extra = od.getGlovesOrderQuantity() - gloves_quantity;
        Integer batches = extra/10;
        Integer transportCost = batches * Constants.transportCost;
        Integer extra_price = extra * Constants.quantity_and_price.get("germany_gloves_price") + transportCost;
        gloves_total_price = gloves_quantity * gloves_price + extra_price;
        list.add(gloves_total_price);
        list.add(0);
        list.add(Constants.quantity_and_price.get("germany_gloves_quantity") - extra);
        return list;
    }

    private static LinkedList<Integer> getGermanyMaskTotalPrice(OrderDetails od, Integer mask_quantity, Integer mask_price, boolean flag) {
        LinkedList<Integer> list = new LinkedList<>();
        Integer masks_total_price;
        if(od.getMasksOrderQuantity() > mask_quantity){
            list = flag
                    ? getGermanyMasksTotalPriceWithoutDiscount(od, mask_quantity, mask_price)
                    : getGermanyMasksTotalPriceWithDiscount(od, mask_quantity, mask_price);
            return list;
        } else {
            //correct
            masks_total_price = od.getMasksOrderQuantity() * mask_price;
            list.add(masks_total_price);
            list.add(Constants.quantity_and_price.get("uk_mask_quantity"));
            list.add(Constants.quantity_and_price.get("germany_mask_quantity") - od.getMasksOrderQuantity());
            return list;
        }
    }

    private static LinkedList<Integer> getGermanyMasksTotalPriceWithDiscount(OrderDetails od, Integer mask_quantity, Integer mask_price) {
        LinkedList<Integer> list = new LinkedList<>();
        Integer masks_total_price;
        Integer extra = od.getMasksOrderQuantity() - mask_quantity;
        Integer batches = extra/10;
        Integer transportCost = batches * Constants.transportCost * (100 - Constants.discount) / 100;
        Integer extra_price = extra * Constants.quantity_and_price.get("uk_mask_price") + transportCost;
        masks_total_price = mask_quantity * mask_price + extra_price;
        list.add(masks_total_price);
        list.add(Constants.quantity_and_price.get("uk_mask_quantity"));
        list.add(0);
        return list;
    }

    private static LinkedList<Integer> getGermanyMasksTotalPriceWithoutDiscount(OrderDetails od, Integer mask_quantity, Integer mask_price) {
        LinkedList<Integer> list = new LinkedList<>();
        Integer masks_total_price;
        Integer extra = od.getMasksOrderQuantity() - mask_quantity;
        Integer batches = extra/10;
        Integer transportCost = batches * Constants.transportCost;
        Integer extra_price = extra * Constants.quantity_and_price.get("uk_mask_price") + transportCost;
        masks_total_price = mask_quantity * mask_price + extra_price;
        list.add(masks_total_price);
        list.add(Constants.quantity_and_price.get("uk_mask_quantity") - extra);
        list.add(0);
        return list;
    }

    private static LinkedList<Integer> getUkMaskTotalPrice(OrderDetails od, Integer mask_quantity, Integer mask_price, boolean flag) {
        Integer masks_total_price;
        LinkedList<Integer> list = new LinkedList();
        if(od.getMasksOrderQuantity() > mask_quantity){
            list = flag
                    ? getMasksTotalPriceWithoutDiscount(od, mask_quantity, mask_price) 
                    : getMasksTotalPriceWithDiscount(od, mask_quantity, mask_price);
            return list;
        } else {
            masks_total_price = od.getMasksOrderQuantity() * mask_price;
            list.add(masks_total_price);
            list.add(Constants.quantity_and_price.get("uk_mask_quantity") - od.getMasksOrderQuantity());
            list.add(Constants.quantity_and_price.get("germany_mask_quantity"));

            return list;
        }
    }

    private static LinkedList<Integer> getMasksTotalPriceWithDiscount(OrderDetails od, Integer mask_quantity, Integer mask_price) {
        Integer masks_total_price;
        LinkedList list = new LinkedList();
        Integer extra = od.getMasksOrderQuantity() - mask_quantity;
        Integer batches = extra/10;
        Integer transportCost = batches * Constants.transportCost * (100 - Constants.discount) / 100;
        Integer extra_price = extra * Constants.quantity_and_price.get("germany_mask_price") + transportCost;
        masks_total_price = mask_quantity * mask_price + extra_price;
        list.add(masks_total_price);
        list.add(0);
        list.add(Constants.quantity_and_price.get("germany_mask_quantity"));
        return list;
    }

    private static LinkedList<Integer> getMasksTotalPriceWithoutDiscount(OrderDetails od, Integer mask_quantity, Integer mask_price) {
        LinkedList<Integer> list = new LinkedList<>();
        Integer masks_total_price;
        Integer extra = od.getMasksOrderQuantity() - mask_quantity;
        Integer batches = extra/10;
        Integer transportCost = batches * Constants.transportCost;
        Integer extra_price =  extra * Constants.quantity_and_price.get("germany_mask_price") + transportCost;
        masks_total_price = mask_quantity * mask_price + extra_price;
        list.add(masks_total_price);
        list.add(0);
        list.add(Constants.quantity_and_price.get("germany_mask_quantity") - extra);
        return list;
    }

    private static OrderDetails orderDetailsExtractor(String[] orderArr) {
        OrderDetails od = new OrderDetails();

        for(int i = 0; i < orderArr.length; i++){
            if(orderArr[i].equals(Constants.countriesList.get(0)) || orderArr[i].equals(Constants.countriesList.get(1))){
                od.setOrderCountry(orderArr[i]);
            } else if(orderArr[i].matches("B[0-9]{3}[a-zA-z]{2}[a-zA-Z0-9]{7}")){
                od.setPassportCountry(Constants.countriesList.get(0));
            } else if(orderArr[i].matches("A[a-zA-z]{2}[a-zA-Z0-9]{9}")){
                od.setPassportCountry(Constants.countriesList.get(1));
            } else if(orderArr[i].equals(Constants.productType.get(0))){
                od.setMasksOrderQuantity(Integer.valueOf(orderArr[i + 1]));
            } else if(orderArr[i].equals(Constants.productType.get(1))){
                od.setGlovesOrderQuantity(Integer.valueOf(orderArr[i + 1]));
            }
        }
        return od;
    }
}

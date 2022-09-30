public class OrderDetails {
    private String orderCountry = new String();
    private String passportCountry = new String();
    private Integer masksOrderQuantity = null;
    private Integer glovesOrderQuantity = null;

    public String getOrderCountry() {
        return orderCountry;
    }

    public void setOrderCountry(String orderCountry) {
        this.orderCountry = orderCountry;
    }

    public String getPassportCountry() {
        return passportCountry;
    }

    public void setPassportCountry(String passportCountry) {
        this.passportCountry = passportCountry;
    }

    public Integer getMasksOrderQuantity() {
        return masksOrderQuantity;
    }

    public void setMasksOrderQuantity(Integer masksOrderQuantity) {
        this.masksOrderQuantity = masksOrderQuantity;
    }

    public Integer getGlovesOrderQuantity() {
        return glovesOrderQuantity;
    }

    public void setGlovesOrderQuantity(Integer glovesOrderQuantity) {
        this.glovesOrderQuantity = glovesOrderQuantity;
    }


}

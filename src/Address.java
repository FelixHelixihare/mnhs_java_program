public class Address {
    public String street;
    public String barangay_code;
    public String barangay;
    public String city;
    public String province;
    public String region;

    public Address(String code, String region, String province, String city, String barangay, String street) {
        this.barangay_code = code;
        this.region = region;
        this.province = province;
        this.city = city;
        this.barangay = barangay;
        this.street = street;
    }
}

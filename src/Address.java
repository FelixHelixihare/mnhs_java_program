public class Address {
    public String street;
    public String barangay_code;
    public String barangay;
    public String city;
    public String province;
    public String region;
    public String zipcode;

    public Address(String code, String region, String province, String city, String barangay, String street, String zipcode) {
        this.barangay_code = code;
        this.region = region;
        this.province = province;
        this.city = city;
        this.barangay = barangay;
        this.street = street;
        this.zipcode = zipcode;
    }

    public Address(Address studentCurrentAddress) {
        this.barangay_code = studentCurrentAddress.barangay_code;
        this.region = studentCurrentAddress.region;
        this.province = studentCurrentAddress.province;
        this.city = studentCurrentAddress.city;
        this.barangay = studentCurrentAddress.barangay;
        this.street = studentCurrentAddress.street;
    }

    public String toString() {
        return province + ", " + city + ", " + barangay +
                (street.isEmpty() ? ", " + street : "");
    }
}

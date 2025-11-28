public class Name {
    public String first_name;
    public String last_name;
    public String middle_name;
    public String extension_name;

    public Name(String first_name, String last_name, String middle_name, String extension_name) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.middle_name = middle_name;
        this.extension_name = extension_name;
    }

    public String get_middle_initial() {return middle_name.charAt(0) + ".";}

    public String get_lfm_name() {
        return String.format("%s, %s %s", last_name, first_name, get_middle_initial()) + (extension_name.isEmpty() ? "" : " " + extension_name);
    }
    public String get_fml_name() {
        return String.format("%s %s %s", first_name, get_middle_initial(), last_name  + (extension_name.isEmpty() ? "" : " " + extension_name));
    }
    public String get_full_name() {
        return String.format("%s %s %s", first_name, middle_name, last_name) + (extension_name.isEmpty() ? "" : " " + extension_name);
    }
}

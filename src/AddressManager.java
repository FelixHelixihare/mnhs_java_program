import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddressManager {
    private List<Map<String, Object>> regionList = new ArrayList<>();
    private List<Map<String, Object>> provinceList = new ArrayList<>();
    private List<Map<String, Object>> cityList = new ArrayList<>();
    private List<Map<String, Object>> barangayList = new ArrayList<>();
    /*
    private Map<String, Map<String, Object>> regionMap = new HashMap<>();
    private Map<String, Map<String, Object>> provinceMap = new HashMap<>();
    private Map<String, Map<String, Object>> cityMap = new HashMap<>();
    private Map<String, Map<String, Object>> barangayMap = new HashMap<>();
    */
    public AddressManager() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            regionList = objectMapper.readValue(new File("data/region.json"), objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
            provinceList = objectMapper.readValue(new File("data/province.json"), objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
            cityList = objectMapper.readValue(new File("data/city.json"), objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
            barangayList = objectMapper.readValue(new File("data/barangay.json"), objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        /*
        for (Map<String, Object> i : regionList)    {regionMap.put((String) i.get("region_code"), i);}
        for (Map<String, Object> i : provinceList)  {provinceMap.put((String) i.get("province_code"), i);}
        for (Map<String, Object> i : cityList)      {cityMap.put((String) i.get("city_code"), i);}
        for (Map<String, Object> i : barangayList)  {barangayMap.put((String) i.get("barangay code"), i);}
         */
    }

    public List<Map<String, Object>> get_provinces_in_region(String region_code) {
        List<Map<String, Object>> provincesInRegion = new ArrayList<>();

        for (Map<String, Object> i : provinceList) {
            if (region_code.equals(i.get("region_code"))) {
                provincesInRegion.add(i);
            }
        }

        return provincesInRegion;
    }

    public List<Map<String, Object>> get_cities_in_province(String province_code) {
        List<Map<String, Object>> citiesInProvince = new ArrayList<>();

        for (Map<String, Object> i : cityList) {
            if (province_code.equals(i.get("province_code"))) citiesInProvince.add(i);
        }

        return citiesInProvince;
    }

    public List<Map<String, Object>> get_barangays_in_city(String city_code) {
        List<Map<String, Object>> barangaysInCity = new ArrayList<>();

        for (Map<String, Object> i : barangayList) {
            if (city_code.equals(i.get("city_code"))) barangaysInCity.add(i);
        }

        return barangaysInCity;
    }

    public Map<String, Object> get_region(int index) throws IndexOutOfBoundsException {return regionList.get(index);}

    public void show_region_choices() {
        for (int i = 0; i < regionList.size(); i++) {
            System.out.printf("(%s) %s%n", i, regionList.get(i).get("region_name"));
        }
    }
    public void show_province_choices(String region_code) {
        List<Map<String, Object>> provincesInRegion = get_provinces_in_region(region_code);
        for (int i = 0; i < provincesInRegion.size(); i++) {
            System.out.printf("(%s) %s%n", i, provinceList.get(i).get("province_name"));
        }
    }
    public void show_city_choices(String province_code) {
        List<Map<String, Object>> citiesInProvince = get_cities_in_province(province_code);
        for (int i = 0; i < citiesInProvince.size(); i++) {
            System.out.printf("(%s) %s%n", i, cityList.get(i).get("city_name"));
        }
    }
    public void show_barangay_choices(String city_code) {
        List<Map<String, Object>> barangaysInCity = get_barangays_in_city(city_code);
        for (int i = 0; i < barangaysInCity.size(); i++) {
            System.out.printf("(%s) %s%n", i, regionList.get(i).get("barangay_name"));
        }
    }

    public static void test() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Map<String, Object>> list = objectMapper.readValue(new File("data/region.json"), objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
            System.out.println(list.getFirst().toString());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}

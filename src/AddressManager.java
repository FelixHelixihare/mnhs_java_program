import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressManager {
    private List<Map<String, Object>> regionList = new ArrayList<>();
    private List<Map<String, Object>> provinceList = new ArrayList<>();
    private List<Map<String, Object>> cityList = new ArrayList<>();
    private List<Map<String, Object>> barangayList = new ArrayList<>();

    private Map<String, List<Map<String, Object>>> provincesInRegions = new HashMap<>();
    private Map<String, List<Map<String, Object>>> citiesInProvinces = new HashMap<>();
    private Map<String, List<Map<String, Object>>> barangaysInCities = new HashMap<>();

    public AddressManager() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            regionList =    objectMapper.readValue(new File("data/region.json"), objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
            provinceList =  objectMapper.readValue(new File("data/province.json"), objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
            cityList =      objectMapper.readValue(new File("data/city.json"), objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
            barangayList =  objectMapper.readValue(new File("data/barangay.json"), objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        for (Map<String, Object> i : regionList) {provincesInRegions.put((String) i.get("region_code"), new ArrayList<>());}
        for (Map<String, Object> j : provinceList) {
            provincesInRegions.get((String) j.get("region_code")).add(j);
            citiesInProvinces.put((String) j.get("province_code"), new ArrayList<>());
        }
        for (Map<String, Object> k : cityList) {
            citiesInProvinces.get((String) k.get("province_code")).add(k);
            barangaysInCities.put((String) k.get("city_code"), new ArrayList<>());
        }
        for (Map<String, Object> l : barangayList) {barangaysInCities.get((String) l.get("city_code")).add(l);}
    }

    public void show_region_choices() {
        for (int i = 0; i < regionList.size(); i++) {
            System.out.printf("(%s) %s%n", i, regionList.get(i).get("region_name"));
        }
    }
    public void show_province_choices(String region_code) {
        List<Map<String, Object>> provincesInRegion = provincesInRegions.get(region_code);
        for (int i = 0; i < provincesInRegion.size(); i++) {
            System.out.printf("(%s) %s%n", i, provincesInRegion.get(i).get("province_name"));
        }
    }
    public void show_city_choices(String province_code) {
        List<Map<String, Object>> citiesInProvince = citiesInProvinces.get(province_code);
        for (int i = 0; i < citiesInProvince.size(); i++) {
            System.out.printf("(%s) %s%n", i, citiesInProvince.get(i).get("city_name"));
        }
    }
    public void show_barangay_choices(String city_code) {
        List<Map<String, Object>> barangaysInCity = barangaysInCities.get(city_code);
        for (int i = 0; i < barangaysInCity.size(); i++) {
            System.out.printf("(%s) %s%n", i, barangaysInCity.get(i).get("brgy_name"));
        }
    }

    public Map<String, Object> getRegion    (int index) {return regionList.get(index);}
    public Map<String, Object> getProvince  (String region_code, int index)     {return provincesInRegions.get(region_code).get(index);}
    public Map<String, Object> getCity      (String province_code, int index)   {return citiesInProvinces.get(province_code).get(index);}
    public Map<String, Object> getBarangay  (String city_code, int index)       {return barangaysInCities.get(city_code).get(index);}
}

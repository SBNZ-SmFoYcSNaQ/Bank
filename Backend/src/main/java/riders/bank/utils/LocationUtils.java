package riders.bank.utils;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import riders.bank.App;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Objects;

public class LocationUtils {
    private static final String dbLocation = "databases/GeoLite2-City.mmdb";

    public static boolean isSameCountry(String ipOne, String ipTwo) throws IOException, GeoIp2Exception {
        InputStream inputStream = App.class.getClassLoader().getResourceAsStream(dbLocation);
        DatabaseReader dbReader = new DatabaseReader.Builder(inputStream).build();
        CityResponse cityResponseOne = dbReader.city(InetAddress.getByName(ipOne));
        CityResponse cityResponseTwo = dbReader.city(InetAddress.getByName(ipTwo));
        return cityResponseOne.getCountry().getName().equals(cityResponseTwo.getCountry().getName());
    }

    public static String getCityAndCountryName(String location) throws IOException, GeoIp2Exception {
        InputStream inputStream = App.class.getClassLoader().getResourceAsStream(dbLocation);
        DatabaseReader dbReader = new DatabaseReader.Builder(inputStream).build();
        CityResponse cityResponse = dbReader.city(InetAddress.getByName(location));
        return cityResponse.getCity().getName() + ", "+cityResponse.getCountry().getName();
    }
}

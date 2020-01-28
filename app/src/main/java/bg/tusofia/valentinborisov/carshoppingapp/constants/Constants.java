package bg.tusofia.valentinborisov.carshoppingapp.constants;

import bg.tusofia.valentinborisov.carshoppingapp.ui.settings.SettingsFragment;

public class Constants {
    //Foursquare API
    public static final String CLIENT_ID = "3KHWXDGEJ2B41BHWP4RPBWSIYAVDZJSUCFFCKZDDH0PEPZ4Y";
    public static final String CLIENT_SECRET = "FCU2ITKBQQPVW5WJ5GO545TMXHNCPKSYYOYEDHAN0FBVT0LC";
    public static final String VERSION = "20190908";
    public static final String FOURSQUARE_API_URL = "https://api.foursquare.com/v2/venues/search";

    //Backend
    public static final String BASE_URL = "http://192.168.100.35:8087/";

    //Settings
    public static final String RANGE_SETTING = "range";
    public static final String INTERVAL_SETTING = "interval";
    public static final CharSequence[] RANGE_LABELS = new CharSequence[] {"500 M", "1KM", "2 KM"};
    public static final CharSequence[] RANGE_VALUES = new CharSequence[] {"500", "1000", "2000"};

    public static final CharSequence[] INTERVAL_LABELS = new CharSequence[] {"1 min", "5 min", "10 min"};
    public static final CharSequence[] INTERVAL_VALUES = new CharSequence[] {"60000", "300000", "600000"};


    //Google Maps
    public static final String GOOGLE_MAPS_URL = "http://maps.google.com/maps?daddr=";
    public static final String GOOGLE_MAPS_URL_LATITUDE = "lat";
    public static final String GOOGLE_MAPS_URL_LONGITUDE = "lng";

    //Shared Preferences
    public static final String SHARED_PREFERENCES_NAME = SettingsFragment.class.getName() + ".SETTINGS";

    private Constants(){
        //removes default constructor
    }
}

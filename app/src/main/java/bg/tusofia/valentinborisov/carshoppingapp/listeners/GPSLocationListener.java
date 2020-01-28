package bg.tusofia.valentinborisov.carshoppingapp.listeners;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import android.util.Log;

import java.util.List;

import bg.tusofia.valentinborisov.carshoppingapp.constants.Constants;
import bg.tusofia.valentinborisov.carshoppingapp.retrofit.clients.CarShoppingClient;
import bg.tusofia.valentinborisov.carshoppingapp.realm.utils.RealmUtils;
import bg.tusofia.valentinborisov.carshoppingapp.retrofit.callbacks.FoursquareAPICallback;
import bg.tusofia.valentinborisov.carshoppingapp.ui.settings.SettingsFragment;
import lombok.AllArgsConstructor;
import okhttp3.ResponseBody;
import retrofit2.Call;

@AllArgsConstructor
public class GPSLocationListener implements LocationListener {
    private Context mContext;
    private CarShoppingClient mClient;
    private NotificationManager mNotificationManager;


        @Override
        public void onLocationChanged(final Location location) {


            final StringBuilder builderLoc = new StringBuilder();
            builderLoc.append(location.getLatitude());
            builderLoc.append(",");
            builderLoc.append(location.getLongitude());

            SharedPreferences prefs = mContext.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

            String rangeString = prefs.getString(Constants.RANGE_SETTING, null);
            Long range = rangeString != null ? Long.valueOf(rangeString) : 1000L;

            List<String> keywords = RealmUtils.getKeywordsFromWishlist();
            for(String words : keywords) {
                Log.d("GPSListener","Calling " + words);
                Call<ResponseBody> resp = mClient.getNearbyPlaces(Constants.FOURSQUARE_API_URL, builderLoc.toString(), range, words, Constants.CLIENT_ID, Constants.CLIENT_SECRET, Constants.VERSION);
                FoursquareAPICallback callback = new FoursquareAPICallback(mNotificationManager, mContext);
                resp.enqueue(callback);

                if(callback.getIsCallMade()){
                    Log.d("GPSListener","Breaking on " + words);
                    break;
                }
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            //do nothing - deprecated
        }

        @Override
        public void onProviderEnabled(String s) {
            //do nothing
        }

        @Override
        public void onProviderDisabled(String s) {
            //TODO prompt to enable
        }
    }


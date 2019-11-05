package bg.tusofia.valentinborisov.dipl.listeners;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import bg.tusofia.valentinborisov.dipl.MainActivity;
import bg.tusofia.valentinborisov.dipl.R;
import bg.tusofia.valentinborisov.dipl.clients.CarShoppingClient;
import lombok.AllArgsConstructor;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AllArgsConstructor
public class GPSLocationListener implements LocationListener {
    private Context mContext;
    private CarShoppingClient mClient;
    private NotificationManager mNotificationManager;
    private static final String CLIENT_ID = "3KHWXDGEJ2B41BHWP4RPBWSIYAVDZJSUCFFCKZDDH0PEPZ4Y";
    private static final String CLIENT_SECRET = "FCU2ITKBQQPVW5WJ5GO545TMXHNCPKSYYOYEDHAN0FBVT0LC";
    private static final String VERSION = "20190908";

        @Override
        public void onLocationChanged(final Location location) {
            final StringBuilder builderLoc = new StringBuilder();
            builderLoc.append(location.getLatitude());
            builderLoc.append(",");
            builderLoc.append(location.getLongitude());
            Toast.makeText(mContext, builderLoc.toString(), Toast.LENGTH_LONG).show();

            Call<ResponseBody> resp = mClient.getNearbyPlaces("https://api.foursquare.com/v2/venues/search", builderLoc.toString(), 2000L, "dinner", CLIENT_ID, CLIENT_SECRET, VERSION);
            resp.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    JSONObject json;
                    try {
                        json = new JSONObject(response.body().string());

                        JSONArray placesJson = json.getJSONObject("response").getJSONArray("venues");

                        JSONObject loc = placesJson.getJSONObject(0).getJSONObject("location");

                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse("http://maps.google.com/maps?daddr=" + loc.getString("lat") + "," + loc.getString("lng")));


                        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, "channel_id")
                                .setSmallIcon(R.drawable.ic_warn)
                                .setContentTitle("In-car Shopping")
                                .setContentText("An item on your wishlist is nearby. Tap to open navigation.")
                                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(PendingIntent.getActivity(mContext, 0, intent, 0));

                        mNotificationManager.notify(1,notificationBuilder.build());

                    } catch (IOException e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            //do nothing
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


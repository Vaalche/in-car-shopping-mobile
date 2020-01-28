package bg.tusofia.valentinborisov.carshoppingapp.retrofit.callbacks;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import bg.tusofia.valentinborisov.carshoppingapp.constants.Constants;
import bg.tusofia.valentinborisov.carshoppingapp.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoursquareAPICallback implements Callback<ResponseBody> {

    private NotificationManager mNotificationManager;
    private Context mContext;
    private boolean isCallMade;

    public FoursquareAPICallback(NotificationManager notificationManager, Context context){
        mContext = context;
        mNotificationManager = notificationManager;
        isCallMade = false;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        JSONObject json;
        try {
            json = new JSONObject(response.body().string());

            JSONArray placesJson = json.getJSONObject("response").getJSONArray("venues");

            if (placesJson.length() == 0) {
                return;
            }

            JSONObject loc = placesJson.getJSONObject(0).getJSONObject("location");

            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse(Constants.GOOGLE_MAPS_URL + loc.getString(Constants.GOOGLE_MAPS_URL_LATITUDE) + "," + loc.getString(Constants.GOOGLE_MAPS_URL_LONGITUDE)));


            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, "channel_id")
                    .setSmallIcon(R.drawable.ic_warn)
                    .setContentTitle("In-car Shopping")
                    .setContentText("An item on your wishlist is nearby. Tap to open navigation.")
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(PendingIntent.getActivity(mContext, 0, intent, 0));

            mNotificationManager.notify(1, notificationBuilder.build());

            isCallMade = true;


        } catch (IOException e) {
            Log.e("APICallback", e.getMessage());
        } catch (JSONException e) {
            Log.e("APICallback", e.getMessage());
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e("APICallback", t.getMessage());
    }

    public boolean getIsCallMade(){
        return this.isCallMade;
    }
}

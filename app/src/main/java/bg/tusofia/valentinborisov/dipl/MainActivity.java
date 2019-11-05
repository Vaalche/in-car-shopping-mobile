package bg.tusofia.valentinborisov.dipl;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import bg.tusofia.valentinborisov.dipl.adapters.ProductsListAdapter;
import bg.tusofia.valentinborisov.dipl.clients.CarShoppingClient;
import bg.tusofia.valentinborisov.dipl.listeners.GPSLocationListener;
import bg.tusofia.valentinborisov.dipl.responses.ProductsResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    private LocationManager mLocationManager;
    private CarShoppingClient mClient;
    private NotificationManager mNotificationManager;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.productsListView);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Get permission
        }
        else{
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000,
                    1, new GPSLocationListener(mContext, mClient, mNotificationManager));
        }

        if(!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            new AlertDialog.Builder(mContext)
                    .setMessage("GPS not enabled.")
                    .setPositiveButton("Open location settings",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("Cancel",null)
                    .show();
        }

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl("http://192.168.100.7:8087/")
                .addConverterFactory(GsonConverterFactory.create());

        final Retrofit retrofit = retrofitBuilder.build();
        mClient = retrofit.create(CarShoppingClient.class);

        Call<List<ProductsResponse>> call = mClient.getProductsByBrandName("Adidas");

        call.enqueue(new Callback<List<ProductsResponse>>() {
            @Override
            public void onResponse(Call<List<ProductsResponse>> call, Response<List<ProductsResponse>> response) {
                List<ProductsResponse> products = response.body();
                listView.setAdapter(new ProductsListAdapter(MainActivity.this, products));

            }

            @Override
            public void onFailure(Call<List<ProductsResponse>> call, Throwable t){
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}

package bg.tusofia.valentinborisov.carshoppingapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import bg.tusofia.valentinborisov.carshoppingapp.constants.Constants;
import bg.tusofia.valentinborisov.carshoppingapp.retrofit.clients.CarShoppingClient;
import bg.tusofia.valentinborisov.carshoppingapp.listeners.GPSLocationListener;
import bg.tusofia.valentinborisov.carshoppingapp.ui.settings.SettingsFragment;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private LocationManager mLocationManager;
    private NotificationManager mNotificationManager;
    private Context mContext;
    private CarShoppingClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_wishlist, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        mContext = this;


        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        final Retrofit retrofit = retrofitBuilder.build();

        mClient = retrofit.create(CarShoppingClient.class);

        mLocationManager = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);

        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Get permission
        }
        else{
            String intervalString = mContext.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).getString(Constants.INTERVAL_SETTING, null);
            Long interval;
            if(intervalString != null){
                interval = Long.valueOf(intervalString);
            }
            else{
                interval = 60000L;
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, interval,
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
    }

}

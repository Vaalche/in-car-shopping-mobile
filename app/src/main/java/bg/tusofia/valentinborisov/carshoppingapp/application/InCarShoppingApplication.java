package bg.tusofia.valentinborisov.carshoppingapp.application;

import android.app.Application;

import io.realm.Realm;

public class InCarShoppingApplication extends Application {

    @Override
    public void onCreate() {
        Realm.init(getApplicationContext());
        super.onCreate();
    }
}

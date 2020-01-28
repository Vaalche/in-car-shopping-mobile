package bg.tusofia.valentinborisov.carshoppingapp.ui.home;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import bg.tusofia.valentinborisov.carshoppingapp.realm.entities.Wishlist;
import bg.tusofia.valentinborisov.carshoppingapp.retrofit.responses.Brand;
import bg.tusofia.valentinborisov.carshoppingapp.retrofit.responses.ProductsResponse;
import io.realm.Realm;

public class HomeViewModel extends ViewModel {
    Realm realm;

    public HomeViewModel() {

        this.realm = Realm.getDefaultInstance();
    }

    public void addItemToWishList(ProductsResponse item){
        this.realm.beginTransaction();
        this.realm.copyToRealmOrUpdate(new Wishlist(UUID.randomUUID().toString(), item.getName(), item.getBrand().getName(), item.getKeywords(), item.getEncodedImg()));
        this.realm.commitTransaction();
    }

    public List<String> getBrandNames(List<Brand> brandsList){
        List<String> brandNames = new ArrayList<>();
        for(Brand brand : brandsList){
            brandNames.add(brand.getName());
        }
        return brandNames;
    }
}
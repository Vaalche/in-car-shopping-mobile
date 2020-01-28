package bg.tusofia.valentinborisov.carshoppingapp.realm.utils;

import java.util.ArrayList;
import java.util.List;

import bg.tusofia.valentinborisov.carshoppingapp.realm.entities.Wishlist;
import io.realm.Realm;

public class RealmUtils {

    public static List<String> getKeywordsFromWishlist(){
        List<String> keywordsList = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        List<Wishlist> wishlist = realm.where(Wishlist.class).findAll();

        for (Wishlist item : wishlist){
            keywordsList.add(item.getKeywords());
        }

        return keywordsList;
    }
}

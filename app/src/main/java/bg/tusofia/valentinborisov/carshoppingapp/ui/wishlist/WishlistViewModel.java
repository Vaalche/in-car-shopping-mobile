package bg.tusofia.valentinborisov.carshoppingapp.ui.wishlist;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;

import bg.tusofia.valentinborisov.carshoppingapp.realm.entities.Wishlist;
import io.realm.Realm;
import io.realm.RealmResults;

public class WishlistViewModel extends ViewModel {

    private Realm realm;
    private Fragment parent;

    public WishlistViewModel() {
        this.realm = Realm.getDefaultInstance();

    }

    public void setParentFragment(Fragment parent){
        this.parent = parent;
    }

    public void removeItemFromWishlist(final String id){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Wishlist> result = realm.where(Wishlist.class).equalTo("id",id).findAll();
                result.deleteAllFromRealm();
            }
        });
    }

    public void refreshFragment(){
        FragmentTransaction ft = parent.getFragmentManager().beginTransaction();
        ft.detach(parent).attach(parent).commit();
    }

}
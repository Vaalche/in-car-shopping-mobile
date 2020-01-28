package bg.tusofia.valentinborisov.carshoppingapp.ui.wishlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import bg.tusofia.valentinborisov.carshoppingapp.R;
import bg.tusofia.valentinborisov.carshoppingapp.adapters.WishlistItemAdapter;
import bg.tusofia.valentinborisov.carshoppingapp.realm.entities.Wishlist;
import io.realm.Realm;

public class WishlistFragment extends Fragment {

    private WishlistViewModel wishlistViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        wishlistViewModel =
                ViewModelProviders.of(this).get(WishlistViewModel.class);
        wishlistViewModel.setParentFragment(this);
        View root = inflater.inflate(R.layout.fragment_wishlist, container, false);
        final ListView wishListView = root.findViewById(R.id.wishListView);

        Realm realm = Realm.getDefaultInstance();

        List<Wishlist> items = realm.where(Wishlist.class).findAll();
        if(items != null) {
            wishListView.setAdapter(new WishlistItemAdapter(getActivity(), items, wishlistViewModel));
        }
        return root;
    }
}
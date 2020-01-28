package bg.tusofia.valentinborisov.carshoppingapp.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import java.util.List;

import bg.tusofia.valentinborisov.carshoppingapp.R;
import bg.tusofia.valentinborisov.carshoppingapp.realm.entities.Wishlist;
import bg.tusofia.valentinborisov.carshoppingapp.ui.wishlist.WishlistViewModel;

public class WishlistItemAdapter extends ArrayAdapter<Wishlist>{

        private Activity context;
        private List<Wishlist> products;
        private Button removeButton;
        private ViewModel wishlistViewModel;

        public WishlistItemAdapter(Activity context, List<Wishlist> products, ViewModel wishlistViewModel){
            super(context, R.layout.wishlist_item_layout, products);
            this.context = context;
            this.products = products;
            this.wishlistViewModel = wishlistViewModel;
        }

        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.wishlist_item_layout, null, true);
        byte[] img = Base64.decode(products.get(position).getEncodedImg(),Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(img,0, img.length);
        bmp = Bitmap.createScaledBitmap(bmp, 200, 200, false);

        TextView txtTitle = rowView.findViewById(R.id.textViewItem);
        TextView txtBrand = rowView.findViewById(R.id.textViewBrand);
        removeButton = rowView.findViewById(R.id.removeButton);
        ImageView imageView = rowView.findViewById(R.id.imageView);
        txtTitle.setText(products.get(position).getName());
        txtBrand.setText(products.get(position).getBrandName());
        imageView.setImageBitmap(bmp);


        removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        ((WishlistViewModel) wishlistViewModel).removeItemFromWishlist(products.get(position).getId());
                        ((WishlistViewModel) wishlistViewModel).refreshFragment();
                }
        });

        return rowView;
    }

}

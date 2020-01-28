package bg.tusofia.valentinborisov.carshoppingapp.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bg.tusofia.valentinborisov.carshoppingapp.R;
import bg.tusofia.valentinborisov.carshoppingapp.retrofit.responses.ProductsResponse;

public class ProductsListAdapter extends ArrayAdapter<ProductsResponse> {

    private Activity context;
    private List<ProductsResponse> products;

    public ProductsListAdapter(Activity context, List<ProductsResponse> products){
        super(context, R.layout.product_list_layout, products);
        this.context = context;
        this.products = products;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.product_list_layout, null, true);

        byte[] img = Base64.decode(products.get(position).getEncodedImg(),Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(img,0, img.length);
        bmp = Bitmap.createScaledBitmap(bmp, 200, 200, false);

        TextView txtTitle = rowView.findViewById(R.id.textViewItem);
        TextView txtBrand = rowView.findViewById(R.id.textViewBrand);
        ImageView imageView = rowView.findViewById(R.id.imageView);
        txtTitle.setText(products.get(position).getName());
        txtBrand.setText(products.get(position).getBrand().getName());
        imageView.setImageBitmap(bmp);

        return rowView;
    }
}

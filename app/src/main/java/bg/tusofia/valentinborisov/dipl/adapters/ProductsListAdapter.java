package bg.tusofia.valentinborisov.dipl.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bg.tusofia.valentinborisov.dipl.R;
import bg.tusofia.valentinborisov.dipl.responses.ProductsResponse;

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

        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
        txtTitle.setText(products.get(position).getName());
        imageView.setImageBitmap(bmp);

        return rowView;
    }
}

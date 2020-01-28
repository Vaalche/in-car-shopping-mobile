package bg.tusofia.valentinborisov.carshoppingapp.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import bg.tusofia.valentinborisov.carshoppingapp.constants.Constants;
import bg.tusofia.valentinborisov.carshoppingapp.R;
import bg.tusofia.valentinborisov.carshoppingapp.adapters.ProductsListAdapter;
import bg.tusofia.valentinborisov.carshoppingapp.retrofit.clients.CarShoppingClient;
import bg.tusofia.valentinborisov.carshoppingapp.retrofit.responses.Brand;
import bg.tusofia.valentinborisov.carshoppingapp.retrofit.responses.ProductsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;


    private ListView listView;
    private AutoCompleteTextView autoComplete;
    private Button searchButton;
    private Context mContext;
    private CarShoppingClient mClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        listView = root.findViewById(R.id.productsListView);
        searchButton = root.findViewById(R.id.search_button);
        autoComplete = root.findViewById(R.id.brand_filter);
        mContext = root.getContext();


        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        final Retrofit retrofit = retrofitBuilder.build();
        mClient = retrofit.create(CarShoppingClient.class);


        Call<List<Brand>> brandsCall = mClient.getBrands();
        brandsCall.enqueue(new Callback<List<Brand>>() {
            @Override
            public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {

                autoComplete.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, homeViewModel.getBrandNames(response.body())));
            }

            @Override
            public void onFailure(Call<List<Brand>> call, Throwable t) {
                Log.e("Home", t.getMessage());
            }
        });






        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Call<List<ProductsResponse>> call = mClient.getProductsByBrandName(autoComplete.getText().toString());

                call.enqueue(new Callback<List<ProductsResponse>>() {
                    @Override
                    public void onResponse(Call<List<ProductsResponse>> call, Response<List<ProductsResponse>> response) {
                        List<ProductsResponse> products = response.body();
                        listView.setAdapter(new ProductsListAdapter(getActivity(), products));

                    }

                    @Override
                    public void onFailure(Call<List<ProductsResponse>> call, Throwable t){
                        Log.e("Home", t.getMessage());
                    }
                });
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final ProductsResponse item = (ProductsResponse) adapterView.getItemAtPosition(i);
                new AlertDialog.Builder(mContext)
                        .setMessage("Do you wish to add this item to your wishlist?")
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                homeViewModel.addItemToWishList(item);
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .show();
            }
        });

        return root;
    }
}
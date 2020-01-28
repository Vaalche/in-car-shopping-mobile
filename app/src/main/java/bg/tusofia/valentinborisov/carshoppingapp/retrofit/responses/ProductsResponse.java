package bg.tusofia.valentinborisov.carshoppingapp.retrofit.responses;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Valche on 4.9.2019 г..
 */

@Getter
@Setter
public class ProductsResponse {

    private String name;
    private Brand brand;
    private String keywords;
    private String encodedImg;
}

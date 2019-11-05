package bg.tusofia.valentinborisov.dipl.realm.entities;

import bg.tusofia.valentinborisov.dipl.responses.Brand;
import io.realm.RealmObject;

public class Wishlist extends RealmObject {

    private String name;
    private String brandName;
    private String keywords;
    private String encodedImg;
}

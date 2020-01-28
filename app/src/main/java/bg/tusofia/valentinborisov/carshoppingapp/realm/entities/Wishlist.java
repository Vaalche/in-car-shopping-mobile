package bg.tusofia.valentinborisov.carshoppingapp.realm.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class Wishlist extends RealmObject {

    @PrimaryKey
    @Required
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String brandName;
    @NonNull
    private String keywords;
    @NonNull
    private String encodedImg;

    public Wishlist(){
        //default constructor
    }


}

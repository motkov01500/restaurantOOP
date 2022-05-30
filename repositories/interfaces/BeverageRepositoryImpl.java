package restaurant.repositories.interfaces;


import restaurant.entities.drinks.interfaces.Beverages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class BeverageRepositoryImpl implements BeverageRepository<Beverages> {
    Collection<Beverages> beveragesCollection;

    public BeverageRepositoryImpl(){
    beveragesCollection = new ArrayList<>();
    }

    @Override
    public Beverages beverageByName(String drinkName, String drinkBrand) {
        for (Beverages beverage:beveragesCollection) {
            if(beverage.getName().equals(drinkName) && beverage.getBrand().equals(drinkBrand)){
                return beverage;
            }
        }
    return null;
    }

    @Override
    public Collection<Beverages> getAllEntities() {
        return Collections.unmodifiableCollection(this.beveragesCollection);
    }

    @Override
    public void add(Beverages entity) {
        beveragesCollection.add(entity);
    }
}

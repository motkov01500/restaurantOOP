package restaurant.repositories.interfaces;

import restaurant.entities.healthyFoods.interfaces.HealthyFood;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HealthFoodRepositoryImpl implements HealthFoodRepository<HealthyFood>{
    private Collection<HealthyFood> healthyFoods;
    public HealthFoodRepositoryImpl(){
        healthyFoods = new ArrayList<>();
    }
    @Override
    public HealthyFood foodByName(String name) {
        for (HealthyFood food:healthyFoods) {
            if(food.getName().equals(name)){
                return food;
            }
        }
        return null;
    }

    @Override
    public Collection<HealthyFood> getAllEntities() {
        return Collections.unmodifiableCollection(this.healthyFoods);
    }

    @Override
    public void add(HealthyFood entity) {
        healthyFoods.add(entity);
    }
}

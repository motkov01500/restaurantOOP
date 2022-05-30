package restaurant.entities.healthyFoods.interfaces;

import restaurant.common.ExceptionMessages;

import static restaurant.common.ExceptionMessages.*;

public abstract class Food implements HealthyFood{
    private String name;
    private double portion;
    private double price;

    public Food(String name, double portion, double price){
    this.setName(name);
    this.setPortion(portion);
    this.setPrice(price);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getPortion() {
        return this.portion;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(INVALID_NAME);
        }
        this.name = name;
    }

    public void setPortion(double portion) {
        if(portion <= 0){
            throw new IllegalArgumentException(INVALID_PORTION);
        }
        this.portion = portion;
    }

    public void setPrice(double price) {
        if(price <= 0){
            throw new IllegalArgumentException(INVALID_PRICE);
        }
        this.price = price;
    }

}

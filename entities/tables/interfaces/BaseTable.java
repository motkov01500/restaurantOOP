package restaurant.entities.tables.interfaces;

import restaurant.common.ExceptionMessages;
import restaurant.entities.drinks.interfaces.Beverages;
import restaurant.entities.healthyFoods.interfaces.HealthyFood;

import java.util.ArrayList;
import java.util.Collection;

import static restaurant.common.ExceptionMessages.*;

public abstract class BaseTable implements Table{
    private Collection<HealthyFood> healthyFood;
    private Collection<Beverages> beverages;
    private int number;
    private int size;
    private int numberOfPeople; //counter of people who want a table
    private double pricePerPerson;
    private boolean isReservedTable;
    private double allPeople; //calculates the price for all people
    private double sum = 0;

    public BaseTable(int number, int size, double pricePerPerson) {
        this.number = number;
        this.setSize(size);
        this.pricePerPerson = pricePerPerson;
        this.healthyFood = new ArrayList<>();
        this.beverages = new ArrayList<>();

    }

    public void setSize(int size) {
        if(size < 0){
            throw new IllegalArgumentException(INVALID_TABLE_SIZE);
        }
        this.size = size;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        if(numberOfPeople <= 0){
            throw new IllegalArgumentException(INVALID_NUMBER_OF_PEOPLE);
        }

        this.numberOfPeople = numberOfPeople;
    }
    @Override
    public int getTableNumber() {
        return this.number;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public int numberOfPeople() {
        return this.numberOfPeople;
    }

    @Override
    public double pricePerPerson() {
        return this.pricePerPerson;
    }

    @Override
    public boolean isReservedTable() {
        return this.isReservedTable;
    }

    @Override
    public double allPeople() {
        return this.allPeople;
    }

    @Override
    public void reserve(int numberOfPeople) {
        this.setNumberOfPeople(numberOfPeople);
        sum += numberOfPeople * this.pricePerPerson; //TODO
        isReservedTable = true;
    }

    @Override
    public void orderHealthy(HealthyFood food) {
        sum += food.getPrice();
        this.healthyFood.add(food);
    }

    @Override
    public void orderBeverages(Beverages beverages) {
        sum += beverages.getPrice();
        this.beverages.add(beverages);
    }

    @Override
    public double bill() {
        return this.sum;
    }

    @Override
    public void clear() {
        beverages.clear();
        sum = 0;
        healthyFood.clear();
        this.isReservedTable = false;
        numberOfPeople = 0;
    }

    @Override
    public String tableInformation() {
        return String.format("Table - %d%nSize - %d%nType - %s%nAll price - %.2f", this.getTableNumber(), this.getSize(), this.getClass().getSimpleName(), this.pricePerPerson);
    }
}

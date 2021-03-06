package restaurant.core;

import restaurant.common.ExceptionMessages;
import restaurant.common.OutputMessages;
import restaurant.core.interfaces.Controller;
import restaurant.entities.drinks.interfaces.Fresh;
import restaurant.entities.drinks.interfaces.Smoothie;
import restaurant.entities.healthyFoods.interfaces.Food;
import restaurant.entities.healthyFoods.interfaces.HealthyFood;
import restaurant.entities.drinks.interfaces.Beverages;
import restaurant.entities.healthyFoods.interfaces.Salad;
import restaurant.entities.healthyFoods.interfaces.VeganBiscuits;
import restaurant.entities.tables.interfaces.InGarden;
import restaurant.entities.tables.interfaces.Indoors;
import restaurant.entities.tables.interfaces.Table;
import restaurant.repositories.interfaces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static restaurant.common.ExceptionMessages.*;
import static restaurant.common.OutputMessages.*;

public class ControllerImpl implements Controller {
    private BeverageRepository<Beverages> beverageRepository;
    private HealthFoodRepository<HealthyFood> healthFoodRepository;
    private TableRepository<Table> tableRepository;


    public ControllerImpl(HealthFoodRepository<HealthyFood> healthFoodRepository, BeverageRepository<Beverages> beverageRepository, TableRepository<Table> tableRepository) {
        this.healthFoodRepository = healthFoodRepository;
        this.beverageRepository = beverageRepository;
        this.tableRepository = tableRepository;
    }

    @Override
    public String addHealthyFood(String type, double price, String name) {
        HealthyFood food = null;
        switch (type){
            case "Salad":
                food = new Salad(name, price);
                break;
            case "VeganBiscuits":
                food = new VeganBiscuits(name, price);
                break;

        }
        HealthyFood currentFood = healthFoodRepository.foodByName(name);
        if(currentFood == null) {
            healthFoodRepository.add(food);
            return String.format(FOOD_ADDED, name);
        } else {
            throw new IllegalArgumentException(String.format(FOOD_EXIST, name));
        }
    }

    @Override
    public String addBeverage(String type, int counter, String brand, String name){
        Beverages beverage = null;
        switch (type){
            case "Fresh":
                beverage = new Fresh(name, counter, brand);
                break;
            case "Smoothie":
                beverage = new Smoothie(name, counter, brand);
                break;

        }
        Beverages currentBeverage = beverageRepository.beverageByName(name, brand);
        if(currentBeverage == null) {
            beverageRepository.add(beverage);
            return String.format(BEVERAGE_ADDED, beverage.getClass().getSimpleName(), brand);
        } else {
            throw new IllegalArgumentException(String.format(BEVERAGE_EXIST, name));
        }
    }

    @Override
    public String addTable(String type, int tableNumber, int capacity) {
        Table table = null;
        switch (type){
            case "Indoors":
                table = new Indoors(tableNumber, capacity);
                break;
            case "InGarden":
                table = new InGarden(tableNumber, capacity);
                break;

        }
        Table currentTable = tableRepository.byNumber(tableNumber);
        if(currentTable == null) {
            tableRepository.add(table);
            return String.format(TABLE_ADDED, tableNumber);
        } else {
            throw new IllegalArgumentException(String.format(TABLE_IS_ALREADY_ADDED, tableNumber));
        }
    }

    @Override
    public String reserve(int numberOfPeople) {
        for (Table currentTable:tableRepository.getAllEntities()) {
            if(currentTable.getSize() >= numberOfPeople && (!currentTable.isReservedTable())){
                currentTable.reserve(numberOfPeople);
                return String.format(TABLE_RESERVED, currentTable.getTableNumber(), numberOfPeople);
            }
        }
        return String.format(RESERVATION_NOT_POSSIBLE, numberOfPeople);
    }

    @Override
    public String orderHealthyFood(int tableNumber, String healthyFoodName) {
        Table currentTable = tableRepository.byNumber(tableNumber);
        if(currentTable == null){
            return String.format(WRONG_TABLE_NUMBER, tableNumber);
        } else {
            HealthyFood currentFood = healthFoodRepository.foodByName(healthyFoodName);
            if(currentFood == null){
                return String.format(NONE_EXISTENT_FOOD, healthyFoodName);
            } else {
                currentTable.orderHealthy(currentFood);
                return String.format(FOOD_ORDER_SUCCESSFUL, healthyFoodName, tableNumber);
            }
        }
    }

    @Override
    public String orderBeverage(int tableNumber, String name, String brand) {
        Table currentTable = tableRepository.byNumber(tableNumber);
        if(currentTable == null){
            return String.format(WRONG_TABLE_NUMBER, tableNumber);
        } else {
            Beverages currentBeverage = beverageRepository.beverageByName(name, brand);
            if(currentBeverage == null){
                return String.format(NON_EXISTENT_DRINK, name, brand);
            } else {
                currentTable.orderBeverages(currentBeverage);
                return String.format(BEVERAGE_ORDER_SUCCESSFUL, name, tableNumber);
            }
        }
    }
    private double totalMoney = 0;

    @Override
    public String closedBill(int tableNumber) {
        Table currentTable = tableRepository.byNumber(tableNumber);
        double bill = currentTable.bill();
        totalMoney += bill;
        currentTable.clear();
        return String.format(BILL, tableNumber, bill);
    }


    @Override
    public String totalMoney() {
        return String.format(TOTAL_MONEY, totalMoney);
    }
}

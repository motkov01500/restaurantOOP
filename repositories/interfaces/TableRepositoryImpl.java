package restaurant.repositories.interfaces;

import restaurant.entities.tables.interfaces.Table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class TableRepositoryImpl implements TableRepository<Table>{
    private Collection<Table> tables;

    public TableRepositoryImpl(){
        tables = new ArrayList<>();
    }
    @Override
    public Table byNumber(int number) {
        for (Table table:this.tables) {
            if(table.getTableNumber() == number){
                return table;
            }
        }
        return null;
    }

    @Override
    public Collection<Table> getAllEntities() {
        return Collections.unmodifiableCollection(this.tables);
    }

    @Override
    public void add(Table entity) {
        tables.add(entity);
    }
}

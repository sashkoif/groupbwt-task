package com.tasks.groupbwttask.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    private final List<Field> fields;
    private final List<Field> rocks;
    
    public World(int sizeX, int sizeY) {
        fields = new ArrayList<>(sizeX*sizeY);
		for (int x=1;x<=sizeX;x++){
            for (int y=1;y<=sizeY;y++){
                fields.add(new Field(x,y));
            }
        }

        rocks = new ArrayList<>();
        for (int i=0;i<100;i++){
            rocks.add(new Field(new Random().ints(1,sizeX).findFirst().getAsInt(),
                                new Random().ints(1,sizeY).findFirst().getAsInt()));
        }
    }
    
    public List<Field> getFields() {
		return fields;
    }

    public List<Field> getRocks() {
		return rocks;
    }
    
    public boolean isSpaceAvailable(List<Field> movedBody) {
        boolean result = true;
        for (Field spaceField:movedBody){
            if (!isFieldAvailable(spaceField)){
                return false;
            }
        }
        return result;
    }
    
    private boolean isFieldAvailable(Field spaceField) {
		boolean isSpaceAvailable = fields.stream().anyMatch(field -> field.equals(spaceField));
        boolean isRockOnField = rocks.stream().anyMatch(field -> field.equals(spaceField));
        return isSpaceAvailable && !isRockOnField;
	}
}

package com.tasks.groupbwttask.models;

import java.util.ArrayList;
import java.util.List;

import com.tasks.groupbwttask.enumerations.Direction;

public class RunningObject {
    private List<Field> body;

    public RunningObject(Field initField, int sizeX, int sizeY) {
        int initX = initField.getX();
        int initY = initField.getY();
        body = new ArrayList<>();
		for (int x=0;x<sizeX;x++){
            for (int y=0;y<sizeY;y++){
                body.add(new Field(initX+x,initY+y));
            }
        }
    }

	public List<Field> getBody() {
		return body;
	}

	public List<Field> makeMove(Direction direction) {
        List<Field> newBody = new ArrayList<>();
		for (Field field:body){
            Field newField = null;
            switch (direction) {
                case RIGHT: newField = new Field(field.getX()+1,field.getY());break;                            
                case LEFT: newField = new Field(field.getX()-1,field.getY());break;
                case DOWN: newField = new Field(field.getX(),field.getY()+1);break;
                case UP: newField = new Field(field.getX(),field.getY()-1);break;
            }
            newBody.add(newField);
        }
        this.body = newBody;
        return newBody;
	}    
}

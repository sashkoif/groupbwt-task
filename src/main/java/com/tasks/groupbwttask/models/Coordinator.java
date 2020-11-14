package com.tasks.groupbwttask.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tasks.groupbwttask.enumerations.Direction;
public class Coordinator {
    private World world;
    private RunningObject runningObject;
    private Field targetField;
    private List<List<Field>> path;
    
    public Coordinator(World world, RunningObject runningObject, Field targetField) {
		this.world = world;
        this.runningObject = runningObject;
        this.targetField = targetField;
	}
    
    public List<List<Field>> findPath(){
        path = new ArrayList<>();
        while (true) {
            if (isPathCompleate()){
                break;
            } else {
                List<Field> step = getStep();
                path.add(step);
            }            
        }

        return path;
    }

    private List<Field> getStep(){
        Direction direction = findDirection();
        return runningObject.makeMove(direction);
    }

	private Direction findDirection() {
        List<DirectionPath> movePriority = new ArrayList<>(4);
        int targetX = targetField.getX();
        int targetY = targetField.getY();
        Field currentField = runningObject.getBody().get(0);
        int lengthX = targetX-currentField.getX();
        int lengthY = targetY-currentField.getY();
        if (lengthX>0){
            movePriority.add(new DirectionPath(Direction.RIGHT,Math.abs(lengthX)));
            movePriority.add(new DirectionPath(Direction.LEFT,0));
        } else {
            movePriority.add(new DirectionPath(Direction.LEFT,Math.abs(lengthX)));
            movePriority.add(new DirectionPath(Direction.RIGHT,0));
        }
        if (lengthY>0){
            movePriority.add(new DirectionPath(Direction.DOWN,Math.abs(lengthY)));
            movePriority.add(new DirectionPath(Direction.UP,0));
        } else {
            movePriority.add(new DirectionPath(Direction.UP,Math.abs(lengthY)));
            movePriority.add(new DirectionPath(Direction.DOWN,0));
        }
        Collections.sort(movePriority);
        for (DirectionPath directionPath:movePriority) {
            if (isDirectionAvailable(directionPath.getDirection())){
                return directionPath.getDirection();
            }
        }
        return null;
    }
    
    private boolean isDirectionAvailable(Direction direction){
        List<Field> currentBody = runningObject.getBody();
        List<Field> movedBody = new ArrayList<>();
        for (Field field:currentBody){
            Field newField = null;
            switch (direction) {
                case RIGHT: newField = new Field(field.getX()+1,field.getY());break;                            
                case LEFT: newField = new Field(field.getX()-1,field.getY());break;
                case DOWN: newField = new Field(field.getX(),field.getY()+1);break;
                case UP: newField = new Field(field.getX(),field.getY()-1);break;
            }
            movedBody.add(newField);
        }
        boolean isSpaceAvailable = world.isSpaceAvailable(movedBody);
        boolean isPartOfPath = false;
        if (isSpaceAvailable) {
            isPartOfPath = path.stream()
                                .map(fields->fields)
                                .anyMatch(field -> field.equals(movedBody));
        }
        return isSpaceAvailable && !isPartOfPath;
    }
    
    private boolean isPathCompleate() {
        return runningObject
                    .getBody()
                    .stream()
                    .anyMatch(field -> field.equals(targetField));
	}
}

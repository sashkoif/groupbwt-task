package com.tasks.groupbwttask;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.tasks.groupbwttask.models.Coordinator;
import com.tasks.groupbwttask.models.Field;
import com.tasks.groupbwttask.models.RunningObject;
import com.tasks.groupbwttask.models.World;

public class App 
{
    public static void main( String[] args )
    {
        int initX = 100;
        int initY = 100;
        World world = new World(initX, initY);

        int objectSizeX = 100;
        int objectSizeY = 100;
        RunningObject runningObject = new RunningObject(
                            world.getFields().get(0),objectSizeX,objectSizeY);

        Field targetField = world.getFields()
                            .get(new Random().ints(0,initX*initY).findFirst().getAsInt());

        Coordinator coordinator = new Coordinator(world, runningObject, targetField);
        
        List<List<Field>> path = coordinator.findPath();
        List<Field> coloredFields = path.stream()
                                        .map(fields->fields)
                                        .flatMap(fields->fields.stream())
                                        .collect(Collectors.toList());
        
    }
}

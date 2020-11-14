package com.tasks.groupbwttask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.tasks.groupbwttask.models.Coordinator;
import com.tasks.groupbwttask.models.Field;
import com.tasks.groupbwttask.models.RunningObject;
import com.tasks.groupbwttask.models.World;
import com.tasks.groupbwttask.views.MainGUI;

public class App 
{
    public static void main( String[] args )
    {
        MainGUI mainGUI = new MainGUI();
        mainGUI.setVisible(true);
    }

	public static Map<String,List<Field>> init(int objectSizeX, int objectSizeY, Field targetField) {
        int initX = 100;
        int initY = 100;
        World world = new World(initX, initY);
        
        RunningObject runningObject = new RunningObject(
                            world.getFields().get(0),objectSizeX,objectSizeY);

        // Field targetField = world.getFields()
        //                     .get(new Random().ints(0,initX*initY).findFirst().getAsInt());

        Coordinator coordinator = new Coordinator(world, runningObject, targetField);

        List<List<Field>> path = coordinator.findPath();
        List<Field> pathFields = path.stream()
                                         .map(fields->fields)
                                         .flatMap(fields->fields.stream())
                                         .collect(Collectors.toList());

        List<Field> rocks = world.getRocks();
        List<Field> body = new RunningObject(
            world.getFields().get(0),objectSizeX,objectSizeY).getBody();

        Map<String,List<Field>> result = new HashMap<>();
        result.put("pathFields", pathFields);
        result.put("rocks", rocks);
        result.put("body", body);
        return result;
	}
}
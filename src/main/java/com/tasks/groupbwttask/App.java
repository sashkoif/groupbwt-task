package com.tasks.groupbwttask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	public static Map<String,List<Field>> init(int objectSizeX, int objectSizeY, Field initField, Field targetField, List<Field> initRocks) {
        int initX = 100;
        int initY = 100;
        World world = new World(initX, initY);
        world.setRocks(initRocks);

        int initObjectX = initField.getX();
        int initObjectY = initField.getY();
        
        RunningObject runningObject = new RunningObject(initField,objectSizeX,objectSizeY);

        Coordinator coordinator = new Coordinator(world, runningObject, targetField);

        List<List<Field>> path = coordinator.findPath();
        List<Field> pathFields = path.stream()
                                         .map(fields->fields)
                                         .flatMap(fields->fields.stream())
                                         .collect(Collectors.toList());

        List<Field> rocks = world.getRocks();
        List<Field> body = new RunningObject(new Field(initObjectX, initObjectY),objectSizeX,objectSizeY).getBody();

        Map<String,List<Field>> result = new HashMap<>();
        result.put("pathFields", pathFields);
        result.put("rocks", rocks);
        result.put("body", body);
        return result;
	}
}
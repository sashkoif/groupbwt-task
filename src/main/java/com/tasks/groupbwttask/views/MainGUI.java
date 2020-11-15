package com.tasks.groupbwttask.views;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.tasks.groupbwttask.App;
import com.tasks.groupbwttask.models.Field;

public class MainGUI extends JFrame implements ActionListener {
    private List<JButton> grid = new ArrayList<>();

    JTextField targetFieldX;
    JTextField targetFieldY;
    JTextField objectFieldX;
    JTextField objectFieldY;
    JTextField initObjectFieldX;
    JTextField initObjectFieldY;
    JTextArea rocksField;


	public MainGUI() {
        super("Simple Example");
	    this.setBounds(50,50,1200,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getLayeredPane();
        
        for (int x=0;x<100;x++){
            for (int y=0;y<100;y++){
                JButton button = new JButton();
                button.setBounds(x*7, y*7, 7, 7);
                this.getLayeredPane().add(button);

                grid.add(button);
            }
        }

        JLabel targetLabelX = new JLabel("Target X:");
        targetLabelX.setBounds(750, 5, 90, 20);
        this.getLayeredPane().add(targetLabelX);
        targetFieldX = new JTextField();
        targetFieldX.setBounds(860, 5, 50, 20);
        this.getLayeredPane().add(targetFieldX);
        
        JLabel targetLabelY = new JLabel("Target Y:");
        targetLabelY.setBounds(930, 5, 90, 20);
        this.getLayeredPane().add(targetLabelY);
        targetFieldY = new JTextField();
        targetFieldY.setBounds(1050, 5, 50, 20);
        this.getLayeredPane().add(targetFieldY);

        JLabel objectLabelX = new JLabel("Object size X:");
        objectLabelX.setBounds(750, 35, 90, 20);
        this.getLayeredPane().add(objectLabelX);
        objectFieldX = new JTextField();
        objectFieldX.setBounds(860, 35, 50, 20);
        this.getLayeredPane().add(objectFieldX);
        
        JLabel objectLabelY = new JLabel("Object size Y:");
        objectLabelY.setBounds(930, 35, 90, 20);
        this.getLayeredPane().add(objectLabelY);
        objectFieldY = new JTextField();
        objectFieldY.setBounds(1050, 35, 50, 20);
        this.getLayeredPane().add(objectFieldY);

        JLabel initObjectLabelX = new JLabel("Start position X:");
        initObjectLabelX.setBounds(750, 65, 90, 20);
        this.getLayeredPane().add(initObjectLabelX);
        initObjectFieldX = new JTextField();
        initObjectFieldX.setBounds(860, 65, 50, 20);
        this.getLayeredPane().add(initObjectFieldX);
        
        JLabel initObjectLabelY = new JLabel("Start position Y:");
        initObjectLabelY.setBounds(930, 65, 90, 20);
        this.getLayeredPane().add(initObjectLabelY);
        initObjectFieldY = new JTextField();
        initObjectFieldY.setBounds(1050, 65, 50, 20);
        this.getLayeredPane().add(initObjectFieldY);

        JLabel rocksLabel = new JLabel("Rocks:");
        rocksLabel.setBounds(750, 95, 90, 20);
        this.getLayeredPane().add(rocksLabel);
        rocksField = new JTextArea();
        rocksField.setBounds(860, 95, 100, 300);
        JScrollPane scroll = new JScrollPane(rocksField);
        scroll.setBounds(860, 95, 100, 300);
        this.getLayeredPane().add(scroll);
        String rocksText = "";
        for (int i=0;i<100;i++){
            int randomX = new Random().ints(1,100).findFirst().getAsInt();
            int randomY = new Random().ints(1,100).findFirst().getAsInt();
            rocksText = rocksText.concat(""+randomX+","+randomY+";\n");
        }
        rocksField.setText(rocksText);
        
        JButton initButton = new JButton();
        initButton.setBounds(750, 410, 150, 40);
        initButton.setText("Start");
        initButton.addActionListener(this);
        this.getLayeredPane().add(initButton);
    }
    
    private Field convertStringToField(String rockString){
        String[] splited = rockString.split(",");
        return new Field(Integer.parseInt(splited[0]),Integer.parseInt(splited[1]));
    }

	@Override
	public void actionPerformed(ActionEvent e) {
        grid.stream().forEach(button->button.setBackground(Color.WHITE));

        int objectSizeX = Integer.parseInt(objectFieldX.getText());
        int objectSizeY = Integer.parseInt(objectFieldY.getText());

        Field targetField = new Field(Integer.parseInt(targetFieldX.getText()),
                                        Integer.parseInt(targetFieldY.getText()));

        Field initField = new Field(Integer.parseInt(initObjectFieldX.getText()),
                                        Integer.parseInt(initObjectFieldY.getText()));

        String rocksText = rocksField.getText();
        List<String> rosksString = Arrays.asList(rocksText.split(";\n"));
        List<Field> initRocks = rosksString.stream().map(this::convertStringToField).collect(Collectors.toList());
         

        Map<String,List<Field>> results = App.init(objectSizeX,objectSizeY,initField,targetField,initRocks);
        
        List<Field> pathFields = results.get("pathFields");
        for (Field pathField:pathFields){
            int indexPath = (pathField.getX()-1)*100+pathField.getY()-1;
            grid.get(indexPath).setBackground(Color.GRAY);
        }
        List<Field> body = results.get("body");
        for (Field bodyField:body){
            int indexBody = (bodyField.getX()-1)*100+bodyField.getY()-1;
            grid.get(indexBody).setBackground(Color.BLUE);
        }
        List<Field> rocks = results.get("rocks");
        for (Field rockField:rocks){
            int indexRock = (rockField.getX()-1)*100+rockField.getY()-1;
            grid.get(indexRock).setBackground(Color.BLACK);
        }

        int indexTarget = (targetField.getX()-1)*100+targetField.getY()-1;
        grid.get(indexTarget).setBackground(Color.RED);
	}    
}

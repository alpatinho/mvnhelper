/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *
 * @author lucas.bergmann
 */
public class AppGUI {

    public static void main(String[] args) {
        new AppGUI();
    }

    public AppGUI() {
        JFrame guiFrame = new JFrame();
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("MVNHelper 1.0");
        guiFrame.setSize(514, 807);
        guiFrame.setLocationRelativeTo(null);

        String[] fruitOptions = {"Apple", "Apricot", "Banana",
            "Cherry", "Date", "Kiwi", "Orange", "Pear", "Strawberry"};

        String[] vegOptions = {"Asparagus", "Beans", "Broccoli", "Cabbage",
            "Carrot", "Celery", "Cucumber", "Leek", "Mushroom",
            "Pepper", "Radish", "Shallot", "Spinach", "Swede",
            "Turnip"};

        final JPanel comboPanel = new JPanel();
        JLabel comboLbl = new JLabel("Fruits:");
        JComboBox fruits = new JComboBox(fruitOptions);

        comboPanel.add(comboLbl);
        comboPanel.add(fruits);

        final JPanel listPanel = new JPanel();
        listPanel.setVisible(false);
        JLabel listLbl = new JLabel("Vegetables:");
        JList vegs = new JList(vegOptions);
        vegs.setLayoutOrientation(JList.HORIZONTAL_WRAP);

        listPanel.add(listLbl);
        listPanel.add(vegs);

        JButton vegFruitBut = new JButton("Fruit or Veg");

        vegFruitBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //When the fruit of veg button is pressed
                //the setVisible value of the listPanel and
                //comboPanel is switched from true to 
                //value or vice versa.
                listPanel.setVisible(!listPanel.isVisible());
                comboPanel.setVisible(!comboPanel.isVisible());

            }
        });

        guiFrame.add(comboPanel, BorderLayout.NORTH);
        guiFrame.add(listPanel, BorderLayout.CENTER);
        guiFrame.add(vegFruitBut, BorderLayout.SOUTH);
        
        guiFrame.setResizable(false);
        guiFrame.setVisible(true);
    }

}

/*
 * Craig Morrison
 * Address Book Application
 * with Read/Write functionality
 *
 * Dr. Dan Panell
 * January 19, 2015
 * PRG/421
 */
package addressbook;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

/**
 * This class creates an address book window that allows
 * users to enter data, view it in a table, and write it to a file
 * @author craigery
 */
public final class AddressBookInterface extends JFrame implements ActionListener
{
    //window diminsions
    final int WINDOW_HEIGHT = 650;
    final int WINDOW_WIDTH = 450;
    
    //create instance of panels
    private JPanel inputPanel;
    private JPanel buttonPanel;
    private JPanel displayPanel;
    
    //create instance of buttons
    private JButton addEntryButton;
    private JButton exitButton;
    private JButton getDataButton;
    
    //variables to use in all classes
    String firstName, lastName;
    String emailAddress, cellNumber;
    int age;
    
    //variable for text output
    String textOut;
    
    //create objects to place in inputPanel
    JLabel firstNameLabel;
    JTextField firstNameField;
    JLabel lastNameLabel;
    JTextField lastNameField;
    JLabel ageLabel;
    JTextField ageField;
    JLabel emailAddressLabel;
    JTextField emailAddressField;
    JLabel cellNumberLabel;
    JTextField cellNumberField;
        
    //create text area variable
    JTextArea displayInput;
    
    //create String for fileName
    //user will not be able to specify different fileName
    final String addressBookFile = "AddressBookFile.txt";
    
    //creates FileWriter to append data to existing file
    final FileWriter fwriter;
    final PrintWriter outputFile;
    
    //variables to read data from file
    boolean endOfFile = false;

    public AddressBookInterface() throws IOException
    {
        //construct JFrame
        super("Address Book");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        AddressBook(); //sets above definitions to AddressBook Window
        
        //creates instance of FileWriter object
        fwriter = new FileWriter(addressBookFile, true);
        outputFile = new PrintWriter(fwriter);
        
        //add panels
        add(inputPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.EAST);
        add(displayPanel, BorderLayout.SOUTH);
        
        //pack and display panels into frame
        pack();
        setVisible(true);
    }
    
    public void AddressBook()
    {
        //create JPanel inputPanel
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(10,1));
        
        //create instances of each object and add to panel
        firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField(25);
        inputPanel.add(firstNameLabel);
        inputPanel.add(firstNameField);
        
        lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField(25);
        inputPanel.add(lastNameLabel);
        inputPanel.add(lastNameField);
        
        ageLabel = new JLabel("Age");
        ageField = new JTextField(5);
        inputPanel.add(ageLabel);
        inputPanel.add(ageField);
        
        emailAddressLabel = new JLabel("E-mail:");
        emailAddressField = new JTextField(25);
        inputPanel.add(emailAddressLabel);
        inputPanel.add(emailAddressField);
        
        cellNumberLabel = new JLabel("Cell Number:");
        cellNumberField = new JTextField(12);
        inputPanel.add(cellNumberLabel);
        inputPanel.add(cellNumberField);
        
        //create JPanel buttonPanel
        buttonPanel = new JPanel();
        
        //create and add buttons to panel
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);        
        addEntryButton = new JButton("Add Entry");
        addEntryButton.addActionListener(this);
        getDataButton = new JButton("Get Data");
        getDataButton.addActionListener(this);
        
        buttonPanel.add(addEntryButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(getDataButton);
        
        //create JPanel to display entries
        displayPanel = new JPanel();
        displayInput = new JTextArea(20, 40);
        displayInput.setEditable(false);
        displayPanel.add(displayInput);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton source;
        source = (JButton) e.getSource();
        
        if (source == exitButton)
        {
            outputFile.close();
            System.exit(0);
        }   
        
        else if (source == addEntryButton)
        {
                addToFile();   
        }
        
        else
            try 
            {
                getData();
            } 
            catch (FileNotFoundException ex) 
            {
                Logger.getLogger(AddressBookInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void addToFile()
    {
        //assign values to variables
        firstName = firstNameField.getText();
        lastName = lastNameField.getText();
        emailAddress = emailAddressField.getText();
        cellNumber = cellNumberField.getText();

        try
        {
            age = Integer.parseInt(ageField.getText());
            
            if (age >= 1 && age <= 120)
            {

            //display text in JTextArea
            textOut = (firstName + " " + lastName + 
                    "\nAge: " + age +  
                    "\ne-mail: " + emailAddress +
                    "\nCell Number: " + cellNumber);

            //appends new entries to end of list
            displayInput.append(textOut + "\n\n");

            //writes data to file
            outputFile.print(textOut + "\n\n");   
            }
            else
            {
                JOptionPane.showMessageDialog(null, 
                    "Age must be an integer value between 1 and 120.", 
                    "ERROR",
                    JOptionPane.WARNING_MESSAGE);
            }
        }

        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, 
                "Age must be an integer value between 1 and 120.", 
                "ERROR",
                JOptionPane.WARNING_MESSAGE);
        } 
    }

    public void getData() throws FileNotFoundException
    {
        FileInputStream fstream = new FileInputStream(addressBookFile);
        //get data from file
    }
    
    public static Scanner openFile(String addressBookFile)
    {
        Scanner scan;
        try
        {
            //create file object to allocate memory space
            File myList = new File (addressBookFile);
            scan = new Scanner(myList);
        }
        catch(FileNotFoundException e)
        {
            scan = null;
        }
        return scan;
    }
    
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Vaidehi
 */
public class ConfManager extends javax.swing.JFrame {

    // GUI components and database connection variables
    private Connection c;
    private PreparedStatement pat;
    private CallableStatement cat;
    private DefaultTableModel def;  
    private JTable jTable1;  

    //declaring the JTextFields
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phonenoField;
    private JTextField countryField;

    //declaring the  buttons
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JButton statsButton;

    //constructor
    public ConfManager() {
        initComponents();  
        Connect();         
        Load();           
    }

    //database connection
    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/conference", "root", "");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConfManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConfManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //loading attendees
    public void Load() {
        try {
            pat = c.prepareStatement("SELECT * FROM atrendees1");
            ResultSet rs = pat.executeQuery();
            ResultSetMetaData rss = rs.getMetaData();
            int columnCount = rss.getColumnCount();  

            def = (DefaultTableModel) jTable1.getModel();  
            def.setRowCount(0); 

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));  //add values from each column to row
                }
                def.addRow(row);  //add row to table model
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConfManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //clearing the fields
    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        phonenoField.setText("");
        countryField.setText("");
    }

    // Adds a new attendee
    private void addAtd(java.awt.event.ActionEvent evt) {
        try {
            String name = nameField.getText();
            String email = emailField.getText();
            String phoneno = phonenoField.getText();
            String country = countryField.getText();

            //inserting new attendee 
            pat = c.prepareStatement("INSERT INTO atrendees1 (name, email, phoneno, country) VALUES (?, ?, ?, ?)");
            pat.setString(1, name);
            pat.setString(2, email);
            pat.setString(3, phoneno);  
            pat.setString(4, country);
            pat.executeUpdate();

            JOptionPane.showMessageDialog(this, "Attendee added successfully!");
            Load();  
            clearFields();  
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add attendee: " + ex.getMessage());
        }
    }

    //editing an existing attendee
    private void editAtd(java.awt.event.ActionEvent evt) {
        try {
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an attendee to edit.");
                return;
            }

            int id = (int) def.getValueAt(selectedRow, 0);  
            String name = nameField.getText();
            String email = emailField.getText();
            String phoneno = phonenoField.getText();  
            String country = countryField.getText();

            pat = c.prepareStatement("UPDATE atrendees1 SET name = ?, email = ?, phoneno = ?, country = ? WHERE id = ?");
            pat.setString(1, name);
            pat.setString(2, email);
            pat.setString(3, phoneno);  
            pat.setString(4, country);
            pat.setInt(5, id);
            pat.executeUpdate();

            JOptionPane.showMessageDialog(this, "Attendee updated successfully!");
            Load();  
            clearFields();  
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update attendee: " + ex.getMessage());
        }
    }

    // Deletes an attendee
    private void deleteAtd(java.awt.event.ActionEvent evt) {
        try {
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an attendee to delete.");
                return;
            }

            int id = (int) def.getValueAt(selectedRow, 0);  
            pat = c.prepareStatement("DELETE FROM atrendees1 WHERE id = ?");
            pat.setInt(1, id);
            pat.executeUpdate();

            JOptionPane.showMessageDialog(this, "Attendee deleted successfully!");
            Load();  
            clearFields();  
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to delete attendee: " + ex.getMessage());
        }
    }

    //searching attendee
    private void searchAtd(java.awt.event.ActionEvent evt) {
        try {
            String name = nameField.getText();
            String phoneno = phonenoField.getText();
            String email = emailField.getText();
            String country = countryField.getText();

            //sql query for the non empty field
            StringBuilder query = new StringBuilder("SELECT * FROM atrendees1 WHERE 1=1");

            if (!name.isEmpty()) {
                query.append(" AND name LIKE '%").append(name).append("%'");
            }
            if (!phoneno.isEmpty()) {
                query.append(" AND phoneno LIKE '%").append(phoneno).append("%'");
            }
            if (!email.isEmpty()) {
                query.append(" AND email LIKE '%").append(email).append("%'");
            }
            if (!country.isEmpty()) {
                query.append(" AND country LIKE '%").append(country).append("%'");
            }

            pat = c.prepareStatement(query.toString());
            ResultSet rs = pat.executeQuery();
            def.setRowCount(0);  
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getObject(i));
                }
                def.addRow(row);  
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Search failed: " + ex.getMessage());
        }
    }

    // Generates statistics using a stored procedure
    private void genStats(java.awt.event.ActionEvent evt) {
    try {
        //callable to execute stored procedure
        cat = c.prepareCall("{CALL GetAtdStats()}");
        ResultSet rs = cat.executeQuery(); 
        
        //display stats
        StringBuilder stats = new StringBuilder("Attendee Statistics:\n\n");

        while (rs.next()) {
            String country = rs.getString("country");
            int attendeeCount = rs.getInt("attendee_count");
            stats.append("Country: ").append(country)
                 .append(", Count: ").append(attendeeCount)
                 .append("\n");
        }

        // dialog box display
        JOptionPane.showMessageDialog(this, stats.toString());
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to generate statistics: " + ex.getMessage());
    }
}

    // UI initialization 
    private void initComponents() {
        //initializing JTextFields for user inputs
        nameField = new JTextField();
        emailField = new JTextField();
        phonenoField = new JTextField();  
        countryField = new JTextField();

        //initializing JTable
        jTable1 = new JTable();  
        JScrollPane scrollPane = new JScrollPane(jTable1);
        def = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Phone No", "Country"}, 0);
        jTable1.setModel(def);

        //initialize Buttons
        addButton = new JButton("Add Attendee");
        editButton = new JButton("Edit Attendee");
        deleteButton = new JButton("Delete Attendee");
        searchButton = new JButton("Search Attendee");
        statsButton = new JButton("Generate Stats");

        //add action listeners to buttons
        addButton.addActionListener(evt -> addAtd(evt));
        editButton.addActionListener(evt -> editAtd(evt));
        deleteButton.addActionListener(evt -> deleteAtd(evt));
        searchButton.addActionListener(evt -> searchAtd(evt));
        statsButton.addActionListener(evt -> genStats(evt));

        //layout and add components 
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Phone No:"));
        panel.add(phonenoField);  // Changed to phonenoField
        panel.add(new JLabel("Country:"));
        panel.add(countryField);
        panel.add(addButton);
        panel.add(editButton);

        add(panel, BorderLayout.NORTH);

        // bottom- delete and stats
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(statsButton);
        add(buttonPanel, BorderLayout.SOUTH);

        //jFrame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConfManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConfManager().setVisible(true);
            }
        });
    }
}

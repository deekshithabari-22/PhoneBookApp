import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class PhoneBookApp {
    private Map<String, List<String>> phoneBook1;
    private Map<String, List<String>> phoneBook2;

    public PhoneBookApp() {
        phoneBook1 = new TreeMap<>();
	phoneBook2 = new TreeMap<>();
        createGUI();
    }

    private boolean isValidPhone(String phone) {
    	// Validates 10-digit phone numbers
    	return phone.matches("\\d{10}");
    }

    private boolean isValidEmail(String email) {
    	// Validates basic email address format
    	return email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }


    private void createGUI() {
        JFrame frame = new JFrame("Phonebook Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        // Create panels
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        JPanel outputPanel = new JPanel();

        // Input fields
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField();
	JLabel emailLabel = new JLabel("Email Address:");
        JTextField emailField = new JTextField();

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);
	inputPanel.add(emailLabel);
        inputPanel.add(emailField);

        // Buttons
        JButton addButton = new JButton("Add Contact");
	JButton deleteButton = new JButton("Delete Contact");
        JButton searchButton = new JButton("Search");
        JButton showAllButton = new JButton("Show All Contacts");

        buttonPanel.add(addButton);
	buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(showAllButton);

        // Output area
        JTextArea outputArea = new JTextArea(20, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        outputPanel.add(scrollPane);

        // Add panels to frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(outputPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
        	String name = nameField.getText().trim();
        	String phone = phoneField.getText().trim();
        	String email = emailField.getText().trim();

        	if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            		JOptionPane.showMessageDialog(frame, "Please enter name, phone number, and email address.");
            		return;
        	}

        	if (!isValidPhone(phone)) {
            		JOptionPane.showMessageDialog(frame, "Invalid phone number! Please enter a 10-digit number.");
            		return;
        	}

        	if (!isValidEmail(email)) {
            		JOptionPane.showMessageDialog(frame, "Invalid email address! Please enter a valid email.");
            		return;
        	}

       	        if (phoneBook1.containsKey(name) || phoneBook2.containsKey(phone)) {
            		JOptionPane.showMessageDialog(frame, (phoneBook1.containsKey(name) ? "Contact name " : "Phone number ") + "already exists.");
           		 return;
        	}

        	phoneBook1.put(name, new ArrayList<>(Arrays.asList(phone, email)));
        	phoneBook2.put(phone, new ArrayList<>(Arrays.asList(name, email)));
        	outputArea.append("Added: " + name + " -> " + phone + " -> " + email + "\n");
        	nameField.setText("");
        	phoneField.setText("");
        	emailField.setText("");
    	}
	});


	 deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String phone = phoneField.getText().trim();
                if(!name.isEmpty() && !phone.isEmpty()){
			if(phoneBook1.containsKey(name)){
				if(phoneBook1.get(name).get(0).equals(phone)){
					outputArea.append("Deleted: " + name + " -> " + phone + " -> " + phoneBook1.get(name).get(1) + "\n");
					phoneBook1.remove(name);
					phoneBook2.remove(phone);
				}
				else{
					JOptionPane.showMessageDialog(frame, "Invalid Pair of name and PhoneNumber.");
				}
			}
			else{
				JOptionPane.showMessageDialog(frame, "Invalid Contact details. ");
			}
		}
		else if(!name.isEmpty()){
			if(phoneBook1.containsKey(name)){
				outputArea.append("Deleted: " + name + " -> " + phoneBook1.get(name).get(0) + " -> " + phoneBook1.get(name).get(1) + "\n");
				phoneBook2.remove(phoneBook1.get(name).get(0));
				phoneBook1.remove(name);
			}
			else{
				JOptionPane.showMessageDialog(frame, "Invalid Contact Name.");
			}
		}
		else if(!phone.isEmpty()){
			if(phoneBook2.containsKey(phone)){
				outputArea.append("Deleted: " + phoneBook2.get(phone).get(0) + " -> " + phone + " -> " + phoneBook2.get(phone).get(1) + "\n");
				phoneBook1.remove(phoneBook2.get(phone).get(0));
				phoneBook1.remove(phone);
			}
			else{
				JOptionPane.showMessageDialog(frame, "Invalid Phone Number.");
			}
		}
		else{
			JOptionPane.showMessageDialog(frame, "Please Enter Details to Delete a Contact.");
		}
		nameField.setText("");
                phoneField.setText("");
	        emailField.setText("");
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
		String phone = phoneField.getText().trim();
		if(name.isEmpty() && phone.isEmpty()){
			JOptionPane.showMessageDialog(frame, " Enter Contact Details to search.");
		}
                else if (phoneBook1.containsKey(name) && phone.isEmpty()) {
                    outputArea.append("Found: " + name + " -> " + phoneBook1.get(name).get(0) +" -> "+ phoneBook1.get(name).get(1) + "\n");
                } else if(phoneBook2.containsKey(phone) && name.isEmpty()){
			outputArea.append("Found: " + phoneBook2.get(phone).get(0) + " -> " + phone + " -> " + phoneBook2.get(phone).get(1) + "\n");
		}else if(!name.isEmpty() && !phone.isEmpty()){
			if(phoneBook1.containsKey(name) && phoneBook1.get(name).get(0).equals(phone)){
				outputArea.append("Found: " + name + " -> " + phoneBook1.get(name).get(0) +" -> "+ phoneBook1.get(name).get(1) + "\n");
			}else{
				JOptionPane.showMessageDialog(frame, " Invalid Contact Details .");
			}
		}else {
                    if(!name.isEmpty()) outputArea.append("No contact found for: " + name + "\n");
		    else if(!phone.isEmpty()) outputArea.append("No contact found for: " + phone + "\n");
                }
		nameField.setText("");
                phoneField.setText("");
	        emailField.setText("");
            }
        });

        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
		if(phoneBook1.isEmpty()){
			outputArea.append("No Contacts Found.");
			return;
		}
                outputArea.append("Phonebook Entries:\n");
                for (String name : phoneBook1.keySet()) {
                    outputArea.append(name + " -> " + phoneBook1.get(name).get(0) + " -> " + phoneBook1.get(name).get(1) + "\n");
                }
            }
        });

        // Display the frame
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PhoneBookApp());
    }
}

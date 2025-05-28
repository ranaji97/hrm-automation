package com.utility;

public class EmployeeDetails {

	private String firstName;
	private String middleName;
	private String lastName;

	// Constructor
	public EmployeeDetails(String firstName, String middleName, String lastName) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
	}

	// Getters
	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	// Setters
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	// Method to update employee details
	public void updateEmployeeDetails(String firstName, String middleName, String lastName) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
	}

	// Display employee details
	public void displayEmployeeDetails() {
		System.out.println("First Name: " + firstName);
		System.out.println("Middle Name: " + middleName);
		System.out.println("Last Name: " + lastName);
	}

}

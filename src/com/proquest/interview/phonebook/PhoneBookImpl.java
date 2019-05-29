package com.proquest.interview.phonebook;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.proquest.interview.util.DatabaseUtil;

public class PhoneBookImpl implements PhoneBook {
	public Collection<Person> people =null;
	
	public PhoneBookImpl() throws Exception{
		//load from database
		people = DatabaseUtil.getPeople();
	}
	
	@Override
	public void addPerson(Person newPerson) throws Exception {
		// if there are more than one instance of the program running this logic will have problem with data maintained in the program
		DatabaseUtil.addPerson(newPerson);
		people.add(newPerson);
	}
	
	@Override
	public List<Person> findPerson(String firstName, String lastName) {
		// changing to return multiple since name will not be unique
		// making an assumption that names are formed by appending first name and last name with a space in between
		return people.stream()
				.filter(p->p.name.toUpperCase().equals(firstName.toUpperCase() + ' ' + lastName.toUpperCase()))
				.collect(Collectors.toList());
	}
	
	@Override
	public Collection<Person> getPeople(){
		return people;
	}
	
	public static void main(String[] args) {
		try {
			DatabaseUtil.initDB();  // Do not remove this line, it creates the simulated database.

			// Context: the basic idea is that the phone book lives in ("is persisted to") an
			// SQL database.  For this exercise, we're using a simulated database (that really just
			// lives in memory), but pretend that it is a "real", persisted-on-disk database.
			//
			// But ALL of the data should live in-memory in an instance of
			// the PhoneBookImpl class, AS WELL AS being persisted to the database.
			
			// TODO: 1. Create these new Person objects, and put them in both PhoneBook and Database.
			//    John Smith, (248) 123-4567, 1234 Sand Hill Dr, Royal Oak, MI
			//    Cynthia Smith, (824) 128-8758, 875 Main St, Ann Arbor, MI
			
			Person john = new Person("John Smith", "(248) 123-4567", "1234 Sand Hill Dr, Royal Oak, MI");
			Person cynthia = new Person("Cynthia Smith", "(824) 128-8758", "875 Main St, Ann Arbor, MI");
			
			PhoneBook phoneBook = new PhoneBookImpl();
			phoneBook.addPerson(john);
			phoneBook.addPerson(cynthia);

			// TODO 2: Print the whole phone book to System.out.
			System.out.println("Printing the whole phonebook");
			for(Person person:phoneBook.getPeople()){
				System.out.println(person);
			}
			
			// TODO 3: Find Cynthia Smith and print just her entry to System.out.
			System.out.println("Finding and printing Cynthia's record");
			for(Person person: phoneBook.findPerson("Cynthia", "Smith")){
				System.out.println(person);
			}
			
			// Hint: you don't have to implement these features strictly in that order.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

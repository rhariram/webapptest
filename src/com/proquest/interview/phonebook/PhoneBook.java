package com.proquest.interview.phonebook;

import java.util.Collection;
import java.util.List;

public interface PhoneBook {
	public List<Person> findPerson(String firstName, String lastName);
	public void addPerson(Person newPerson) throws Exception;
	public Collection<Person> getPeople();
}

package com.proquest.interview.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.proquest.interview.phonebook.Person;

/**
 * This class is just a utility class, you should not have to change anything here
 * @author rconklin
 */
public class DatabaseUtil {
	public static void initDB() {
		try {
			Connection cn = getConnection();
			Statement stmt = cn.createStatement();
			stmt.execute("CREATE TABLE PHONEBOOK (NAME varchar(255), PHONENUMBER varchar(255), ADDRESS varchar(255))");
			stmt.execute("INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES('Chris Johnson','(321) 231-7876', '452 Freeman Drive, Algonac, MI')");
			stmt.execute("INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES('Dave Williams','(231) 502-1236', '285 Huron St, Port Austin, MI')");
			cn.commit();
			cn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.hsqldb.jdbcDriver");
		return DriverManager.getConnection("jdbc:hsqldb:mem", "sa", "");
	}
	
	public static List<Person>getPeople() throws Exception{
		Connection cn = null;
		try {
			cn = getConnection();
			Statement stmt = cn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT NAME, PHONENUMBER, ADDRESS FROM PHONEBOOK");;
			
			List<Person> result = new ArrayList<Person>();
			while(rs.next()){
				result.add(new Person( rs.getString("NAME"),rs.getString("PHONENUMBER"),rs.getString("ADDRESS")));
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(cn!=null){
				cn.close();
			}
		}
	}
	
	public static void addPerson(Person person) throws Exception{
		Connection cn = null;
		try {
			cn = getConnection();
			PreparedStatement stmt = cn.prepareStatement("INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES(?,?,?)");
			//these are not handling nulls - need to handle null
			stmt.setString(1, person.getName());
			stmt.setString(2, person.getPhoneNumber());
			stmt.setString(3, person.getAddress());
			stmt.executeUpdate();
			cn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(cn!=null) {
				cn.close();
			}
		}
	}
}

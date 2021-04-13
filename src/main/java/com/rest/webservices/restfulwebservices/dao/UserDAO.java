package com.rest.webservices.restfulwebservices.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.rest.webservices.restfulwebservices.model.User;

@Component
public class UserDAO {

	public static List<User> users=new ArrayList();
	
	private static int usercount=3;
	static {
		users.add(new User("a",1,new Date()));
		users.add(new User("b",2,new Date()));
		users.add(new User("c",3,new Date()));
	}
	
	
	public List<User> findAll(){
		return users;
	}
	
	public User save(User user) {
		if(user.getId()==null)
		{
			user.setId(++usercount);
		}
		users.add(user);
		return user;
	}
	
	public User findOne(int id) {

		User user=null;
		if(users.stream().anyMatch(u->u.getId()==id)) {
		
			user = (User) users.stream().filter(u->u.getId()==id).collect(Collectors.toList()).get(0);
			return user;
		}
		return user;
	}
	
	public User deleteById(int id) {
		
		Iterator<User> itr = users.iterator();
		
		while(itr.hasNext()) {
			User user = itr.next();
			if(user.getId()==id) {
				itr.remove();
				return user;
			}
		}
		return null;
	}
}

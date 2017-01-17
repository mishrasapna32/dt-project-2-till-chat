package com.proj2.mvc.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

//import java.util.List;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.proj2.mvc.dao.FileUploadDAO;
import com.proj2.mvc.dao.UserDao;
import com.proj2.mvc.model.Error;
import com.proj2.mvc.model.UploadFile;
import com.proj2.mvc.model.User;
//import com.EATnBITE.mvc.model.Error;
@RestController
public class UserController {
Logger logger=LoggerFactory.getLogger(this.getClass());

@Autowired
private UserDao userDao;
@Autowired
private FileUploadDAO fileUploadDAO;
@RequestMapping("/home")
public String home(){
    return "home";
}
@RequestMapping(value="/login",method=RequestMethod.POST)
public ResponseEntity<?> login(@RequestBody User user,HttpSession session ){
	logger.debug("Entering USERCONTROLLER : LOGIN");
	logger.debug("USERNAME:" + user.getUsername() + " PASSWORD " + user.getPassword() );
	User validUser=userDao.authenticate(user);
	if(validUser==null){
		logger.debug("validUser is null");
		Error error=new Error(1,"Username and password doesnt exists...");
		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED); //401
	}
	else{
		//key [user] value [validUser- an object of type User]
		//user = [james,123,james@xyz.com,student,true,true]
		session.setAttribute("user", validUser);
		validUser.setOnline(true);
		userDao.updateUser(validUser); // to update online status in db
		logger.debug("validUser is not null");
		
		 //select * from proj2_profile_pics where username='adam';
		  UploadFile getUploadFile=fileUploadDAO.getFile(user.getUsername());
		  if(getUploadFile!=null){
	  	String name=getUploadFile.getFileName();
	  	System.out.println(getUploadFile.getData());
	  	byte[] imagefiles=getUploadFile.getData();
	  	try{
	  		String path="C:/Users/shiva/workspace1/Database/src/main/webapp/WEB-INF/resources/images/"+user.getUsername();
	  		File file=new File(path);
	  		//file.mkdirs();
	  		FileOutputStream fos = new FileOutputStream(file);//to Write some data 
	  		fos.write(imagefiles);
	  		fos.close();
	  		}catch(Exception e){
	  		e.printStackTrace();
	  		}
		  }
		
		return new ResponseEntity<User>(validUser,HttpStatus.OK);//200
	}
}


@RequestMapping(value="/register",method=RequestMethod.POST)
public ResponseEntity<?> registerUser(@RequestBody User user){
	//client will send only username, password, email, role 
	try{
	logger.debug("USERCONTROLLER=>REGISTER " + user);
	user.setStatus(true);
	user.setOnline(false);
	User savedUser=userDao.registerUser(user);
	logger.debug("User Id generated is " + savedUser.getId());
	if(savedUser.getId()==0){
		Error error=new Error(2,"Couldnt insert user details ");
		return new ResponseEntity<Error>(error , HttpStatus.CONFLICT);
	}
	else
		return new ResponseEntity<User>(savedUser,HttpStatus.OK);
	}catch(Exception e){
		e.printStackTrace();
		Error error=new Error(2,"Couldnt insert user details. Cannot have null/duplicate values " + e.getMessage());
		return new ResponseEntity<Error>(error , HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
@RequestMapping(value="/logout",method=RequestMethod.PUT)
public ResponseEntity<?> logout(HttpSession session){
	User user=(User)session.getAttribute("user");
	if(user!=null){
		user.setOnline(false);
		userDao.updateUser(user);
		try{
                       
	  		String path="C:/Users/shiva/workspace1/Database/src/main/webapp/WEB-INF/resources/images/"+user.getUsername();
	  		File file=new File(path);
	  		System.out.println(file.delete());
	  		
	  		}catch(Exception e){
	  		e.printStackTrace();
	  		}
	}
	session.removeAttribute("user");
	session.invalidate();
	return new ResponseEntity<Void>(HttpStatus.OK);
}

@RequestMapping(value="/getAllUsers",method=RequestMethod.GET)
public ResponseEntity<?> getAllUsers(HttpSession session){
	User user=(User)session.getAttribute("user");
	if(user==null)
	return new	ResponseEntity<Error>(new Error(1,"Unauthorized user"),HttpStatus.UNAUTHORIZED);
	else
	{
		List<User> users=userDao.getAllUsers(user);
		for(User u:users)
			System.out.println("IsONline " + u.isOnline());
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
}
}

package controllers;

import play.*;
import play.modules.facebook.FbGraph;
import play.modules.facebook.FbGraphException;
import play.mvc.*;
import play.mvc.Scope.Session;

import java.util.*;

import com.google.gson.JsonObject;
import com.restfb.FacebookClient;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.User;

public class Application extends Controller {

	public static void index() {
		try{
			FacebookClient fbClient = FbGraph.getFacebookClient();
			User profile = fbClient.fetchObject("me", com.restfb.types.User.class);
			Logger.info("profile=%s", profile.getName());
			user(profile.getName(),profile.getId());
		}catch(Exception ex){
			//not logged in, show button	
			login();
		}	
	}
	
	public static void user(String name,String id){
		render(name,id);
	}
	
	public static void login(){
		boolean isShowLoginButton=true;
		render(isShowLoginButton);
	}	
	

	public static void facebookLogin() {
		
		FacebookClient fbClient = FbGraph.getFacebookClient();
		
		User profile = fbClient.fetchObject("me", com.restfb.types.User.class);
		String uid = profile.getId()+" ";
		String name = profile.getFirstName();				
		 
		Session.current().put("username", uid); 

		index();
	}

	public static void facebookLogout() {
		Session.current().remove("username");
		FbGraph.destroySession();
		index();
	}
	public static void users(){
		render();
	}

}
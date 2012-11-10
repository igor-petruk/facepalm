package controllers;

import play.*;
import play.modules.facebook.FbGraph;
import play.modules.facebook.FbGraphException;
import play.mvc.*;
import play.mvc.Scope.Session;

import java.util.*;

import com.google.gson.JsonObject;
import com.restfb.FacebookClient;
import com.restfb.types.User;

public class Application extends Controller {

	public static void index() {
		render();
	}
	
	public static void index(String email) {
		render(email);
	}

	public static void facebookLogin() {
		
		FacebookClient fbClient = FbGraph.getFacebookClient();
		User profile = fbClient.fetchObject("me", com.restfb.types.User.class);
		String email = profile.getEmail();
		// do useful things
		Session.current().put("username", email); 

		index(email);
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
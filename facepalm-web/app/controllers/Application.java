package controllers;

import play.Logger;
import play.modules.facebook.FbGraph;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.Response;
import play.mvc.Scope.Session;

import com.restfb.FacebookClient;
import com.restfb.types.User;

import domain.JsonResponse;
import domain.LikeRepository;
import domain.LoginManager;
import domain.SocialApplication;

public class Application extends Controller {

	public static void index() {
		render();
	}
	
	public static void index(String email) {
		render(email);
	}
	
	public static void count(String siteUrl, String imageUrl)
	{
		Logger.info("Site url : %s image url : %s", siteUrl, imageUrl);
		
		String countResult = JsonResponse.getCount(0);
		
		renderJSON(countResult);
	}

	public static void like(String siteUrl, String imageUrl)
	{
		Logger.info("Site url : %s image url : %s", siteUrl, imageUrl);
		
		Session s = Session.current();
		SocialApplication app = SocialApplication.FACEBOOK;
		
		if ( LoginManager.isLoggedIn(app, s) ){
			
			Integer likeCount = LikeRepository.like(siteUrl, imageUrl);
			
			renderJSON(likeCount);
			
		} else {
			Response.current().status = Http.StatusCode.FORBIDDEN;
		}

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
	
	public static void reset(){
		Logger.info("Like repository is being cleared");
		LikeRepository.reset();
	}
}
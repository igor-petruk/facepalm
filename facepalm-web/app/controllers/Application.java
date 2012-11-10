package controllers;

import play.Logger;
import play.modules.facebook.FbGraph;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.Response;
import play.mvc.Scope.Session;

import com.restfb.FacebookClient;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.User;

import domain.JsonResponse;
import domain.LikeRepository;
import domain.LoginManager;
import domain.SocialApplication;

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
	

	public static void count(String siteUrl, String imageUrl)
	{
		Logger.info("Site url : %s image url : %s", siteUrl, imageUrl);
		
		boolean isLoogedIn = true;
		Integer value = LikeRepository.getLikeCount(siteUrl, imageUrl);
		String result = JsonResponse.getCount(value, isLoogedIn);
		
		renderJSON(result);
	}

	public static void like(String siteUrl, String imageUrl)
	{
		Logger.info("Site url : %s image url : %s", siteUrl, imageUrl);
		
		Session s = Session.current();
		SocialApplication app = SocialApplication.FACEBOOK;
		
		if ( LoginManager.isLoggedIn(app, s) ){
			
			Integer likeCount = LikeRepository.like(siteUrl, imageUrl);
			
			String countResult = JsonResponse.getCount(likeCount, true);
			
			renderJSON(countResult);
			
		} else {
			Response.current().status = Http.StatusCode.FORBIDDEN;
		}

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
	
	public static void reset(){
		Logger.info("Like repository is being cleared");
		LikeRepository.reset();
	}
}
package controllers;

import javax.persistence.TypedQuery;

import models.ImageEntity;
import play.Logger;
import play.db.jpa.JPA;
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

	static SocialApplication APP = SocialApplication.FACEBOOK;
	
	public static void index() {
		render();
	}
	
	public static void index(String email) {
		render(email);
	}
	
//	public static void count(String siteUrl, String imageUrl)
//	{
//		Integer value = LikeRepository.getLikeCount(siteUrl, imageUrl);
//		String result = JsonResponse.getCount(value);
//		
//		renderJSON(result);
//	}
	
	public static void count(String siteUrl, String imageUrl)
	{
		
		boolean wasLiked = false;
		if( LoginManager.isLoggedIn(APP, Session.current() ) ){
			String uToken = LoginManager.loggedUserToken(APP, Session.current() );
			ImageEntity ie = ImageEntity.find("siteUrl = ? and imageUrl = ? and userToken = ?", siteUrl, imageUrl, uToken).first();
			wasLiked = ie != null;
		}
		
		long value = ImageEntity.count("siteUrl = ? and imageUrl = ?", siteUrl, imageUrl);
		
		String result = JsonResponse.getCount( value, wasLiked );
		
		renderJSON(result);
	}

//	public static void like(String siteUrl, String imageUrl)
//	{
//		Logger.info("Site url : %s image url : %s", siteUrl, imageUrl);
//		
//		Session s = Session.current();
//		SocialApplication app = SocialApplication.FACEBOOK;
//		
//		if ( LoginManager.isLoggedIn(app, s) ){
//			
//			Integer likeCount = LikeRepository.like(siteUrl, imageUrl);
//			
//			String countResult = JsonResponse.getCount(likeCount);
//			
//			renderJSON(countResult);
//			
//		} else {
//			Response.current().status = Http.StatusCode.FORBIDDEN;
//		}
//
//	}
	
	public static void like(String siteUrl, String imageUrl)
	{
		
		Session s = Session.current();
		
		if ( LoginManager.isLoggedIn(APP, s) ){
			
			ImageEntity ie = new ImageEntity();
			
			ie.setSiteUrl(siteUrl);
			ie.setImageUrl(imageUrl);
			ie.setUserToken(LoginManager.loggedUserToken(APP, s));
			
			validation.valid(ie);
			if( validation.hasErrors() ){
				
				ie.save();
				
				long value = ImageEntity.count("siteUrl = ? and imageUrl = ?", siteUrl, imageUrl);
				
				String countResult = JsonResponse.getCount( value, true);
				
				renderJSON(countResult);
			} else {
				Response.current().status = Http.StatusCode.INTERNAL_ERROR;
			}
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
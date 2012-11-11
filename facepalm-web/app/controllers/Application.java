package controllers;

import java.util.Collections;
import java.util.List;

import models.ImageEntity;
import models.UserEntity;
import play.Logger;
import play.modules.facebook.FbGraph;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.Response;
import play.mvc.Scope.Session;
import domain.JsonResponse;
import domain.LoginManager;
import domain.SocialApplication;

public class Application extends Controller {

	static SocialApplication APP = SocialApplication.FACEBOOK;

	public static void index()
	{
		if( LoginManager.isLoggedIn(APP, session.current(), false) ){
			String uid = LoginManager.userId(APP, session.current() );
            UserEntity user = UserEntity.findById(uid);
            if(user==null){
                Session.current().remove( APP.sessionIdKey());
            }
            Logger.info("Redirect to user page");
            user ( uid );
		}
        else {
            Logger.info("render index with welcome page");
            render();
		}
	}

    public static void successful(){
        if( LoginManager.isLoggedIn(APP, session.current(), true) ){
           Logger.info("data="+LoginManager.userId(APP,session.current()));
            user(LoginManager.userId(APP,session.current()));
        }
        index();
    }

	public static void user(String uid)
	{
            UserEntity user = UserEntity.findById(uid);
            Logger.info("USER UID="+user);
            if( user != null ){
                List<ImageEntity> likeSet = ImageEntity.find("userToken = ?", uid).fetch();
                Collections.sort(likeSet);

                int size = 10;
                if( size > likeSet.size() ){
                    size = likeSet.size();
                }
                List<ImageEntity> cutList = likeSet.subList(0, size);
                Logger.info("Render user page with likes");
                render( user,  cutList );

            } else{
                Logger.info("USERID Redirect to index page");
                index();
            }


	}

	public static void login()
	{
		render();
	}

	public static void miniLogin(String siteUrl, String imageUrl)
	{
		render(siteUrl,imageUrl);
	}

	public static void count(String siteUrl, String imageUrl)
	{
		boolean wasLiked = false;
		if ( LoginManager.isLoggedIn(APP, Session.current(), false) ) {
			String uToken = LoginManager.userId(APP, Session.current());
			ImageEntity ie = ImageEntity.find("siteUrl = ? and imageUrl = ? and userToken = ?", siteUrl, imageUrl, uToken)
					.first();
			wasLiked = ie != null;
		}

		long value = ImageEntity.count("siteUrl = ? and imageUrl = ?", siteUrl, imageUrl);

		String result = JsonResponse.getCount(value, wasLiked);

		renderJSON(result);
	}

	public static void like(String siteUrl, String imageUrl)
	{

		Session s = Session.current();

		if ( LoginManager.isLoggedIn(APP, s, true) ) {
			
			String uToken = LoginManager.userId(APP, Session.current());
			ImageEntity ie = ImageEntity.find("siteUrl = ? and imageUrl = ? and userToken = ?", siteUrl, imageUrl, uToken)
					.first();
			
			boolean wasLiked = false;
			
			if( ie == null){	// new like from this user 

				ImageEntity ieNew = ImageEntity.valueOf( siteUrl, imageUrl, LoginManager.userId(APP, s) );
				ieNew.save();
				
				wasLiked = true;

			} else {	// unlike 
				ie.delete();
			}
			
			long value = ImageEntity.count("siteUrl = ? and imageUrl = ?", siteUrl, imageUrl);

			String countResult = JsonResponse.getCount(value, wasLiked);

			renderJSON(countResult);
				
		} else {
			Response.current().status = Http.StatusCode.FORBIDDEN;
		}

	}

	public static void facebookLogout()
	{
		Session.current().remove( APP.sessionIdKey());
		FbGraph.destroySession();
		index();
	}

	public static void users()
	{
		render();
	}
	
}

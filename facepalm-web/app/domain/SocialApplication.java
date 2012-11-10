package domain;

import play.Logger;
import play.modules.facebook.FbGraph;
import play.mvc.Scope.Session;

import com.restfb.FacebookClient;
import com.restfb.types.User;

public enum SocialApplication
{
	FACEBOOK{
		@Override
		public boolean isLoggedIn(Session currentSession)
		{
			boolean isLogged = true;
			try{
				FacebookClient fbClient = FbGraph.getFacebookClient();
				User profile = fbClient.fetchObject("me", com.restfb.types.User.class);
				isLogged = profile != null;
			} catch (Exception e){
				Logger.error(e, "Current user is not logged in %s", this.name());
				isLogged = false;
			}
			return isLogged;
		}
	};
	
	public abstract boolean isLoggedIn(Session currentSession);
}

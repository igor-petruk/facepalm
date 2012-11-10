package domain;

import play.Logger;
import play.modules.facebook.FbGraph;
import play.mvc.Scope.Session;

import com.restfb.FacebookClient;
import com.restfb.types.User;

public enum SocialApplication
{
	FACEBOOK {
		@Override
		public boolean loggedIn(Session currentSession)
		{
			return currentSession.contains( sessionIdKey() );
		}			
		
		@Override
		public void login(Session currentSession)
		{
			try {
				FacebookClient fbClient = FbGraph.getFacebookClient();
				User profile = fbClient.fetchObject("me", com.restfb.types.User.class);
				if(profile != null){
					currentSession.put(sessionIdKey(), profile.getId());
				}
			} catch (Exception e) {
				Logger.warn(e, "Current user is not logged in %s", this.name());
			}
		}

		@Override
		public String sessionIdKey()
		{
			return "facebook-user-id";
		}
	};

	public abstract boolean loggedIn(Session currentSession);
	
	public abstract void login(Session currentSession);

	public abstract String sessionIdKey();
}

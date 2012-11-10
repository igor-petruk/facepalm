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
		public boolean isLoggedIn(Session currentSession, boolean softCheck)
		{
			boolean isLogged = true;

			if ( softCheck ) {
				if ( !currentSession.contains(sessionIdKey()) ) {

					try {
						FacebookClient fbClient = FbGraph.getFacebookClient();
						User profile = fbClient.fetchObject("me", com.restfb.types.User.class);
						isLogged = profile != null;

						currentSession.put(sessionIdKey(), profile.getId());

					} catch (Exception e) {
						Logger.warn(e, "Current user is not logged in %s", this.name());
						isLogged = false;
					}
				}

			} else {

				try {
					FacebookClient fbClient = FbGraph.getFacebookClient();
					User profile = fbClient.fetchObject("me", com.restfb.types.User.class);
					isLogged = profile != null;

					currentSession.put(sessionIdKey(), profile.getId());

				} catch (Exception e) {
					Logger.warn(e, "Current user is not logged in %s", this.name());
					isLogged = false;
				}

			}
			return isLogged;
		}

		@Override
		public String sessionIdKey()
		{
			return "facebook-user-id";
		}
	};

	public abstract boolean isLoggedIn(Session currentSession, boolean softCheck);

	public abstract String sessionIdKey();
}

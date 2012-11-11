package domain;

import models.FqlUser;
import models.UserEntity;
import play.Logger;
import play.modules.facebook.FbGraph;
import play.mvc.Scope.Session;

import com.restfb.FacebookClient;
import com.restfb.types.User;

import java.util.List;

public enum SocialApplication
{
	FACEBOOK {
		@Override
		public boolean loggedIn(Session currentSession)
		{
			return currentSession.contains( sessionIdKey() );
		}			
		
		@Override
		public boolean login(Session currentSession)
		{
			try {
				FacebookClient fbClient = FbGraph.getFacebookClient();
                String query = "SELECT uid, first_name, last_name, pic_big FROM user where uid=me()";
                List<FqlUser> users = fbClient.executeQuery(query, FqlUser.class);

				
				if( users != null ){
					currentSession.put(sessionIdKey(), users.get(0).uid);
					
					if(UserEntity.hasNoUser(users.get(0).uid)){
						UserEntity ue = UserEntity.valueOf(users.get(0).uid,
                                users.get(0).first_name,
                                users.get(0).last_name,
                                users.get(0).pic_big);
						ue.save();
					}
					
				}
			} catch (Exception e) {
				
				Logger.warn(e, "Current user is not logged in %s", this.name());
				
				return false;
			}
			
			return true;
		}

		@Override
		public String sessionIdKey()
		{
			return "facebook-user-id";
		}
	};

	public abstract boolean loggedIn(Session currentSession);
	
	public abstract boolean login(Session currentSession);

	public abstract String sessionIdKey();
}

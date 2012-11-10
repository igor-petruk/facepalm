package domain;

import java.awt.geom.CubicCurve2D;

import play.mvc.Scope.Session;

public class LoginManager {
	
	public static boolean isLoggedIn(SocialApplication app, Session currentSession){
		return app.isLoggedIn(currentSession);
	}
	
	public static String loggedUserToken(SocialApplication app, Session currentSession){
		return currentSession.get( app.sessionIdKey() );
	}
	
}

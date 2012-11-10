package domain;

import java.awt.geom.CubicCurve2D;

import play.mvc.Scope.Session;

public class LoginManager {
	
	public static boolean isLoggedInMandatory(SocialApplication app, Session currentSession){
		return app.isLoggedIn(currentSession, false);
	}
	
	public static boolean isLoggedInSoft(SocialApplication app, Session currentSession){
		return app.isLoggedIn(currentSession, true);
	}
	
	public static String loggedUserToken(SocialApplication app, Session currentSession){
		return currentSession.get( app.sessionIdKey() );
	}
	
}

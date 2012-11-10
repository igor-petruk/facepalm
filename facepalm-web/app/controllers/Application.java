package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

public class Application extends Controller {

	public static void index()
	{
		Logger.info(">>> Index page <<<");
		render();
	}

	public static void count(String siteUrl, String imageUrl)
	{
		Logger.info(">>> count <<< site url : %s image url : %s", siteUrl, imageUrl);
		String countResult = String.format("{\"count\": %d}", 0);
		renderJSON(countResult);
	}

	public static void like(String siteUrl, String imageUrl, String userLogonId)
	{

	}

}
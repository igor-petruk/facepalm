package models;

import com.restfb.Facebook;

/**
 * Created with IntelliJ IDEA.
 * User: eacc
 * Date: 11/11/12
 * Time: 3:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class FqlUser {
    @Facebook
    public String uid;

    @Facebook
    public String name;

    @Facebook
    public String first_name;

    @Facebook
    public String last_name;

    @Facebook
    public String pic_big;
}

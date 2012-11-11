package domain;

import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.mvc.Scope;

/**
 * Created with IntelliJ IDEA.
 * User: eacc
 * Date: 11/11/12
 * Time: 2:10 AM
 * To change this template use File | Settings | File Templates.
 */
@OnApplicationStart
public class Bootstrap extends Job {

    public void doJob() {
      //  Scope.Session.current().clear();
    }

}
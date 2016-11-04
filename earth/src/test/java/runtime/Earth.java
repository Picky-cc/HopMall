/**
 * 
 */
package runtime;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Jetty Server starter. Use mockMode(Enable mock service implement)
 * 
 * @author lute
 *
 */
public class Earth {

	/**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        long begin = System.currentTimeMillis();

		WebAppContext webapp = new WebAppContext("webapp", "");
		webapp.setDefaultsDescriptor("./src/test/resources/runtime/webdefault.xml");
		
		// add mock web.xml here to replace the default web.xml under webapp and enable mockMode
		webapp.setDescriptor("./src/test/resources/runtime/web.local.xml");
		webapp.setMaxFormContentSize(-1);
		
		Server server = new Server(9090);
		server.setAttribute("org.eclipse.jetty.server.Request.maxFormContentSize", "-1");
        server.setHandler(webapp);
        server.start();
        System.out.println("Jetty Server started, use " + (System.currentTimeMillis() - begin) + " ms");
    }
}

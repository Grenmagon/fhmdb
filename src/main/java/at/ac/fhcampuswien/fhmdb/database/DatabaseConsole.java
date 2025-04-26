package at.ac.fhcampuswien.fhmdb.database;

import org.h2.tools.Console;
import org.h2.tools.Server;

public class DatabaseConsole {
    private static Server webServer;
    //auf Datenbank zugreifen, über Browser
    public static void startConsole() {
        try {
            webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082");
            webServer.start();
            System.out.println("H2 Console running at: " + webServer.getURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopConsole() {
        if (webServer != null) {
            webServer.stop();
            System.out.println("H2 Console stopped.");
        }
    }
}

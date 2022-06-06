package source;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class Initializer {

    static public Preferences pref = Preferences.userNodeForPackage(Pref.class);
    static public String lang = pref.get("lang", "pl");
    static public String country = pref.get("country", "PL");
    static public Locale l = new Locale(lang, country);
    static public ResourceBundle r = ResourceBundle.getBundle("Bundle", l);

}

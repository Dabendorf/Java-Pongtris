package pongtris;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Diese Klasse ist aus dem Internet uebernommen und gibt die relative URL einzelner Dateien des Javaverzeichnisses wieder.<br>
 * <b>Quelle:</b> http://forum.byte-welt.net/members/spacerat/2-von-arbeits-installations-und-resourcepfaden.html<br>
 * <br>
 * @author Landei
 * @version 1.0
 * 
 */
public final class BaseURL {
    private static final String JAR_SIG = ".jar!/";
    private static final String CONCAT = ".class";
    private static final File CURRENT = new File(".");
 
    /**
     * Gibt die Basis-URL des Archivs zurueck, in welchem sich die Klasse
     * "clazz" befindet. Aehnlich wie
     *   clazz.getProtectionDomain().getCodeSource().getLocation();
     * nur um einiges zuverlaessiger, weil es auch dann eine URL zurueck-
     * gibt, wenn fuer die Klasse keine ProtectionDomain definiert wurde.
     *
     * @param clazz Die Klasse fuer welche die Basis-URL benoetigt wird
     * @return Archiv Basis-URL
     * @throws NullPointerException falls "clazz" null ist.
     */
    public static URL getJarBase(Class<?> clazz) {
        URL rc = clazz.getResource(clazz.getSimpleName() + CONCAT);
        String name = clazz.getName().replace('.', '/').concat(CONCAT);
        name = rc.toString().replace(name, "");
        try {
            rc = new URL(null, name);
            return rc;
        } catch (MalformedURLException e) {
            // should never happen
            throw new RuntimeException("jar dir could not be determined");
        }
    }
 
    /**
     * Ermittelt den Pfad des Archivs, in welchem sich "clazz" befindet.
     * Die Methode dient also hauptsaechlich zur Ermittlung von
     * Installationspfaden.
     * @param clazz Eine Klasse aus dem Archiv fuer welche der Pfad
     *              ermittelt werden soll.
     * @return Archiv-URL
     * @throws NullPointerException falls "clazz" null ist.
     */
    public static URL getCodeBase(Class<?> clazz) {
        URL rc = getJarBase(clazz);
        if (rc.getProtocol().equals("jar")) {
            String name = rc.toString();
            int i = name.indexOf(JAR_SIG);
            name = name.substring(4, i);
            try {
                rc = new URL(null, name);
                rc = new URL(rc, "./");
            } catch (MalformedURLException e) {
                // should never happen
                throw new RuntimeException("code base could not be determined");
            }
        }
        return rc;
    }
 
    /**
     * Gibt das Arbeitsverzeichnis in welchem die Anwendung ausgefuehrt wird
     * als URL zurueck.
     * @return URL Arbeitsverzeichnis
     */
    public static URL getDocumentBase() {
        try {
            String rc = getWorkingDir().toURI().toURL().toString();
            return new URL(null, rc.substring(0, rc.length() - 2));
        } catch (MalformedURLException e) {
            // should never happen
            throw new RuntimeException("document base could not be determined");
        }
    }
 
    /**
     * Gibt das Arbeitsverzeichnis in welchem die Anwendung ausgefuehrt wird
     * zurueck.
     * @return File Arbeitsverzeichnis
     */
    public static File getWorkingDir() {
        return getAbsoluteFile(CURRENT);
    }
 
    /**
     * Dies ist ein Workaround, der Dateisysteme berifft, in denen Links,
     * vorallem Verzeichnis-Links als Datei existieren (z.B. Windows ".lnk").
     * In Java werden solche Verzeichnis-Links teilweise als Verzeichnis
     * und teilweise als Datei betrachtet, was haeufig zu Problemen fuehrt.
     * Der Workaround ist noch nicht ganz perfekt.
     * @param f Eine Datei wird als Parameter uebernommen.
     * @return Gibt eine Datei zurueck.
     */
    public static File getAbsoluteFile(File f) {
        try {
            f = f.getCanonicalFile();
        } catch (IOException e) {
            f = f.getAbsoluteFile();
        }
        return f;
    }
 
    /**
     * Gibt das Zielverzeichnis einer File-URL als File-Objekt zurueck.
     * @param u Eine URL als Parameter.
     * @return File Zielverzeichnis einer File-URL
     */
    public static File URLtoFile(URL u) {
        if(!"file".equalsIgnoreCase(u.getProtocol())) {
            throw new IllegalArgumentException("protocol is not 'file'");
        }
        return new File(u.getFile());
    }
}
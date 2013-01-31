package creamy.utils;

import java.io.*;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * クラスパス内ファイルを操作するためのユーティリティクラス
 * 
 * @author miyabetaiji
 */
public class ResourceUtil {
    /**
     * 引数で指定したパッケージ下のファイル/デリレクトリのリストを取得する
     * @param packageName 検索するパッケージ
     * @return ファイル/デリレクトリのURLリスト
     */
    public static List<URL> getResources(String packageName) throws MalformedURLException, IOException {
        String packagePath = packageName.replace('.', '/');
        URL url = Thread.currentThread().getContextClassLoader().getResource(packagePath);
        if (url == null)
            return null;
        else
            return getResources(url);
    }

    /**
     * 引数で指定したパッケージ下のファイル/デリレクトリのリストを取得する
     * @param url 検索するパッケージのURL
     * @return ファイル/デリレクトリのURLリスト
     */
    public static List<URL> getResources(URL url) throws MalformedURLException, IOException {
        String protocol = url.getProtocol();
        if (protocol.equals("file"))
            return getResourcesFromFile(url);
        else if (protocol.equals("jar"))
            return getResourcesFromJar(url);
        else
            return null;
    }

    /**
     * ファイルシステムからファイル/デリレクトリのリストを取得する
     * @param url 検索するパッケージのURL
     * @return ファイル/デリレクトリのURLリスト
     */
    public static List<URL> getResourcesFromFile(URL url) throws MalformedURLException {
        return getResourcesFromFile(new File(url.getFile()));
    }

    /**
     * ファイルシステムからファイル/デリレクトリのリストを取得する
     * @param dir 検索するディレクトリ
     * @return ファイル/デリレクトリのURLリスト
     */
    public static List<URL> getResourcesFromFile(File dir) throws MalformedURLException {
        List<URL> urls = new ArrayList<URL>();
        for (File file : dir.listFiles()) {
            urls.add(file.toURI().toURL());
            if (file.isDirectory()) urls.addAll(getResourcesFromFile(file));
        }
        return urls;
    }

    /**
     * Jarファイルからファイル/デリレクトリのリストを取得する
     * @param url 検索するパッケージのURL
     * @return ファイル/デリレクトリのURLリスト
     */
    public static List<URL> getResourcesFromJar(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        if (!(connection instanceof JarURLConnection)) return null;
        JarFile jarFile = ((JarURLConnection)connection).getJarFile();
        List<String> paths = new ArrayList<String>();
        for (Enumeration e = jarFile.entries(); e.hasMoreElements();) {
            ZipEntry entry = (ZipEntry) e.nextElement();
            if (entry.isDirectory()) {
                paths.add(entry.getName().replaceAll("/$", ""));
            } else {
                paths.add(entry.getName());
            }
        }
        List<URL> urls = new ArrayList<URL>();
        String searchStr = url.getFile().replaceAll("^file:.+!/", "");
        for (String path : paths) {
            if (path.startsWith(searchStr) && !path.equals(searchStr)) {
                urls.add(Thread.currentThread().getContextClassLoader().getResource(path));
            }
        }
        return urls;
    }

    /**
     * インプットストリームをStringに変換する
     * @param is インプットストリーム
     * @return ストリームの文字列表現
     */
    public static String inputStreamToString(InputStream is) throws IOException {
        return inputStreamToString(is, 4096);
    }

    /**
     * インプットストリームをStringに変換する
     * @param is インプットストリーム
     * @param bufsize バッファサイズ
     * @return ストリームの文字列表現
     */
    public static String inputStreamToString(InputStream is, int bufsize) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder builder = new StringBuilder();
        char[] buf = new char[bufsize];
        int numRead;
        while (-1 < (numRead = reader.read(buf))) {
            builder.append(buf, 0, numRead);
        }
        return builder.toString();
    }
}
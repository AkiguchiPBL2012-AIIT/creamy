package creamy.property;

import java.io.*;
import java.util.Properties;

/**
 * プロパティファイルに関するユーティリティクラス
 * @author Ryusaburo Tanaka (Professor Akiguchi's PBL 2012, AIIT)
 */
public class PropertyUtil {

    /**
     * プロパティファイルのロード（実行ディレクトリ or クラスパスから）
     * @param   プロパティファイル名
     * @return 読み込み済みのプロパティ
     */
    public static Properties loadProperty(String fileName) throws IOException {
        Properties prop    = new Properties();
        if(new File("." + File.separator + fileName).exists()){
            // 実行ディレクトリに設定ファイルが存在する場合
            prop.load(new FileInputStream(fileName));
        } else {
            InputStream is = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(fileName);
            if (is == null) throw new FileNotFoundException(fileName + " not found.");
            prop.load(is);
        }
        return prop;
    }
}

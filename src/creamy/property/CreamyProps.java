    package creamy.property;

import java.io.IOException;
import java.util.Properties;

    /**
    * 設定ファイル（creamy.properties）の管理クラス
    * 
    * @author Ryusaburo Tanaka (Professor Akiguchi's PBL 2012, AIIT)
    */
    public class CreamyProps {

        /**
        * creamy フレームワークの設定ファイル名
        */
        private static final String FILE_PROPERTIES ="creamy.properties";

        /**
        * 設定ファイルの値群を格納するオブジェクト
        */
        private static Properties prop              = null;

        /**
        * プロパティファイル読み込み
        * @return 読み込みの成功／失敗
        */
        public static void loadProperties() {
            try {
                prop = PropertyUtil.loadProperty(FILE_PROPERTIES);
            } catch (IOException ex) {
                throw new RuntimeException("Failed to load creamy.properties", ex);
            }
        }

        /**
         * 設定ファイル値取得
         * @param key   設定値のキー
         * @return  設定値
         */
        public static String getValue(String key){
            return prop.getProperty(key);
        }
    }

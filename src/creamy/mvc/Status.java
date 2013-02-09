package creamy.mvc;

/**
 * レスポンスの結果を表すステータス
 * @author miyabetaiji
 */
public enum Status {
    /** 正常 */
    OK, 
    /** 不正なリクエスト */
    BAD_REQUEST, 
    /** 例外発生 */
    INTERNAL_ERROR,
    /** データ検証エラー */
    VALIDATION_ERR;
}

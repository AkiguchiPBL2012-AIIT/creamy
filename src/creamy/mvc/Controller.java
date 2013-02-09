/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.mvc;

import creamy.global.ApplicationData;
import java.util.Map;

/**
 * コントローラの基底クラス
 * ViewとModelとの連携を行う
 * @author miyabetaiji
 */
public abstract class Controller extends Results {
    
    private Map<String,Object> continualData;
    private Map<String, Object> requestParams;

    /**
     * 現在のブラウザがスコープのContinualDataを取得する
     * @return 現在のブラウザがスコープのContinualData
     */
    protected Map<String,Object> getContinualData() {
        return this.continualData;
    }

    /**
     * 現在のブラウザがスコープのContinualDataをセットする
     * @param continualData セットするcontinualData
     */
    void setContinualData(Map<String,Object> continualData) {
        this.continualData = continualData;
    }

    /**
     * ポストパラメータをセットする
     * @param params セットするパラメータ
     */
    protected void setRequestParams(Map<String, Object> params) {
        requestParams = params;
    }
    
    /**
     * objへポストパラメータをバインドする
     * @param obj バインド対象のオブジェクト
     * @return パラメータバインド済みのオブジェクト
     */
    protected BindingResult bind(Object obj) {
        return MvcUtil.bindMapModel(requestParams, obj);
    }
    
    /**
     * ポストパラメータを取得する
     * @param key パラメータのKey
     * @return keyに対応するパラメータ
     */
    protected Object params(String key) {
        return requestParams.get(key);
    }
    
    /**
     * ApplicationData(グローバルオブジェクト)を取得する
     * @return ApplicationData(グローバルオブジェクト)
     */
    protected Map<String,Object> getApplicationData() {
        return ApplicationData.getInstance().getData();
    }
}

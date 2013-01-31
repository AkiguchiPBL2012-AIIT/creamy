package creamy.activity;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ActivityFactory、Velocity(VTL)間で授受するデータを格納するクラス.
 * IDの採番、管理を行う. IDはVTLマクロrender呼出し(putParam/putParams)ごとに採番する.
 * 
 * @author miyabetaiji
 */
public class RenderParameter {
    /**
     * ID管理のためのシーケンスNo
     */
    private int seqNo = 0;
    /**
     * パラメータデータ. KeyはIDに紐つく. Valueはデータ
     */
    private Map<String,Map<String,Object>> params = new HashMap<String,Map<String,Object>>();

    private static DecimalFormat formatter = new DecimalFormat("000000000");

    RenderParameter() {}

    /**
     * パラメータデータにデータをPUTする. VTLマクロ renderから呼出される
     * @param key 変数名
     * @param value 値
     * @return 採番したID
     */
    public String putParam(final String key, final Object value) {
        String id = getId();
        params.put(id, new HashMap<String,Object>() {{ put(key,value); }});
        return id;
    }

    /**
     * パラメータデータにデータをPUTする. VTLマクロ renderから呼出される
     * @param keys 変数名のリスト
     * @param values 値のリスト
     * @return 採番したID
     */
    public String putParams(List<String> keys, List<Object> values) {
        String id = getId();
        Map<String,Object> data = new HashMap<String,Object>();
        for (int i = 0; i < keys.size(); i++) data.put(keys.get(i), values.get(i));
        params.put(id, data);
        return id;
    }

    /**
     * パラメータデータを取得する
     * @param id
     * @return パラメータデータ
     */
    Map<String,Object> getParams(String id) {
        return params.get(id);
    }

    private String getId() { return "C" + formatter.format(seqNo++); }
}

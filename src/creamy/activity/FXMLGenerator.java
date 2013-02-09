package creamy.activity;

import creamy.property.CreamyProps;
import creamy.property.PropertyUtil;
import creamy.utils.ResourceUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Stack;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * VTLをVelocityで処理し、FXMLを生成するクラス
 * 
 * @author miyabetaiji
 */
public class FXMLGenerator {
    /**
     * Velocityプロパティファイル名
     */
    private static final String PROP_FILE = "velocity.properties";
    /**
     * helpersパッケージ名称
     */
    private static final String HELPERS_PATH = "helpers";

    FXMLGenerator() {
        Properties props;
        try {
            props = PropertyUtil.loadProperty(PROP_FILE);
        } catch (IOException ex) {
            throw new VelocityException("Failed to load " + PROP_FILE, ex);
        }
        Velocity.init(props);
    }

    /**
     * VTL入りのFXMLをVelocityでレンダリングする。
     * レンダリングの際にHelperのマージとVTLの非コメント化を行う
     * @param fxmlUrl ファイルURL
     * @param fieldParams レンダリング時にContextにPUTするフィールド変数
     * @param takeoverParams レンダリング時にContextにPUTする親から引継がれた変数
     * @param renderParam 子FXMLレンダリング時に生成した変数を格納するオブジェクト
     * @return レンダリングした結果のXML文字列
     */
    synchronized String generate(URL fxmlUrl, Map<Field, Object> fieldParams,
            Map<String,Object> takeoverParams, RenderParameter renderParam) {
        VelocityContext context = new VelocityContext();
        // Contextを設定する
        for (Field key : fieldParams.keySet()) context.put(key.getName(), fieldParams.get(key));
        for (Entry<String,Object> e : takeoverParams.entrySet()) context.put(e.getKey(), e.getValue());
        context.put("renderParam", renderParam);
        // テンプレートとHelpers下のマクロファイルをマージする
        StringBuilder builder = new StringBuilder();
        builder.append(loadHelpers());
        builder.append(uncommentVTL(loadTemplate(fxmlUrl)));
        String vtl = builder.toString();
        if (CreamyProps.getValue("debug.showVTL") != null &&
                CreamyProps.getValue("debug.showVTL").toLowerCase().equals("true"))
            System.out.println(vtl);
        // レンダリング
        StringWriter writer = new StringWriter();
        boolean result = Velocity.evaluate(context, writer, fxmlUrl.getFile(), vtl);
        if (!result) throw new VelocityException("Failed to render " + fxmlUrl.getFile());
        writer.flush();
        String xml = writer.toString();
        if (CreamyProps.getValue("debug.showFXML") != null &&
                CreamyProps.getValue("debug.showFXML").toLowerCase().equals("true"))
            System.out.println(xml);
        return xml;
    }

    private StringBuilder loadHelpers() {
        StringBuilder builder = new StringBuilder();
        try {
            for (URL url : ResourceUtil.getResources(HELPERS_PATH)) {
                builder.append(ResourceUtil.inputStreamToString(url.openStream()));
            }
        } catch (IOException ex) {
            throw new VelocityException("Failed to load helpers", ex);
        }
        return builder;
    }

    private InputStream loadTemplate(URL fxmlUrl) {
        try {
            return fxmlUrl.openStream();
        } catch (IOException ex) {
            throw new VelocityException("Failed to read " + fxmlUrl.toString(), ex);
        }
    }

    /**
     * コメント化されたVTLの識別子(<!-- %)
     */
    private static final String VTL_MARKER = "^\\s*%";
    /**
     * テキスト変換において一時的に使用するプレフィックス
     */
    private static final String VTL_QUOTE_PRE = "@@@VTL";
    /**
     * テキスト変換において一時的に使用するポストフィックス
     */
    private static final String VTL_QUOTE_POST = "VTL@@@";
    /**
     * コメント化されたVTLを識別する正規表現パターン
     */
    private static Pattern pattern = Pattern.compile(VTL_MARKER + ".*", Pattern.DOTALL);
    /**
     * XMLビルダ
     */
    private static DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    /**
     * XMLからテキストに変換するトランスフォーマ
     */
    private static TransformerFactory transformerFactory = TransformerFactory.newInstance();
    /**
     * FXML内のコメントアウトされたVTLを非コメント化する
     * @param is FXMLストリーム
     * @return VTLを非コメント化したFXML文字列
     */
    private static String uncommentVTL(InputStream is) {
        // FXMLをDOMパーサでパースする       
        DocumentBuilder builder;
        Document document;
        try {
            builder = builderFactory.newDocumentBuilder();
            document = builder.parse(is);
        } catch (ParserConfigurationException ex) {
            throw new FXMLLoadException("Failed to create DocumentBuilder", ex);
        } catch (SAXException ex) {
            throw new FXMLLoadException("Failed to parse fxml", ex);
        } catch (IOException ex) {
            throw new FXMLLoadException("Failed to read fxml", ex);
        }
        // VTLコメントNodeをテキストNodeに置換える
        int seqNo = 0;
        Map<String,String> comments = new HashMap<String,String>();
        Stack<Node> nodes = new Stack<Node>();
        nodes.add(document.getDocumentElement());
        while (!nodes.empty()) {
            NodeList children = nodes.pop().getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                nodes.push(child);
                if (!(child instanceof Comment)) continue;
                if (!pattern.matcher(((Comment)child).getData()).find()) continue;
                Comment comment = (Comment)child;
                String seachText = VTL_QUOTE_PRE + seqNo++ + VTL_QUOTE_POST;
                String valueText = comment.getData().replaceFirst(VTL_MARKER, "");
                comments.put(seachText, valueText);
                Text text = document.createTextNode(seachText);
                comment.getParentNode().replaceChild(text, comment);
            }
        }
        // DOMをStringとして出力する
        StringWriter writer = new StringWriter();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
        } catch (TransformerConfigurationException ex) {
            throw new FXMLLoadException("Failed to create Transformer", ex);
        } catch (TransformerException ex) {
            throw new FXMLLoadException("Failed to translate DOM to String", ex);
        }
        writer.flush();
        String xml = writer.toString();
        // 置換えたテキストNodeの文字列を元のテキストに再置換する
        for (Entry<String,String> entry : comments.entrySet()) {
            xml = xml.replace(entry.getKey(), entry.getValue());
        }
        return xml.replaceAll("\\?><", "\\?>\n<");
    }
}

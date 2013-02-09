package creamy.db;

import com.avaje.ebean.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ユーザModelの基底クラス。ユーザはこのクラスを継承してModelクラスを作成する。
 * EBeanの各種メソッドをラップ。
 */
@javax.persistence.MappedSuperclass
public class Model {

    /**
     * EntityのPrimary Keyを取得。
     * @return Primary Key
     */
    private Integer _getId() {
        try {
            for(Field idField : getClass().getDeclaredFields()){
                if (idField.isAnnotationPresent(javax.persistence.Id.class)) {
                    idField.setAccessible(true);
                    return (Integer) idField.get(this);
                }
            }
            return null;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * EntityのPrimary Keyを設定。
     * @param Primary Key
     */
    private void _setId(Object id) {
        try {
            for(Field idField : getClass().getDeclaredFields()){
                if (idField.isAnnotationPresent(javax.persistence.Id.class)) {
                    idField.setAccessible(true);
                    idField.set(this, (Integer) id);
                }
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Entityをsava。 (inserts)
     *
     */
    public void save() {
        Ebean.save(this);
    }

    /**
     * Entityをsava。 (inserts)
     *
     * @param 接続するサーバー名
     */
    public void save(String server) {
        Ebean.getServer(server).save(this);
    }

    /**
     * many-to-many維持して、save。
     */
    public void saveManyToManyAssociations(String path) {
        Ebean.saveManyToManyAssociations(this, path);
    }

    /**
     * many-to-manyを維持して、save。
     *
     * @param 接続するサーバー名
     */
    public void saveManyToManyAssociations(String server, String path) {
        Ebean.getServer(server).saveManyToManyAssociations(this, path);
    }

    /**
     * Entityを更新。
     */
    public void update() {
        Ebean.update(this);
    }

    /**
     * 指定したサーバーのEntityを更新。
     *
     * @param 接続するサーバー名
     */
    public void update(String server) {
        Ebean.getServer(server).update(this);
    }

    /**
     * 指定したIDのEntityを更新。
     * @param Primary Key
     */
    public void update(Object id) {
        _setId(id);
        Ebean.update(this);
    }

    /**
     * 指定したIDと、指定したサーバーのEntityを更新。
     *
     * @param Primary Key
     * @param 接続するサーバー名
     */
    public void update(Object id, String server) {
        _setId(id);
        Ebean.getServer(server).update(this);
    }

    /**
     * Entityを削除。
     */
    public void delete() {
        Ebean.delete(this);
    }

    /**
     * 指定したサーバーのEntityを削除。
     *
     * @param 接続するサーバー名
     */
    public void delete(String server) {
        Ebean.getServer(server).delete(this);
    }

    /**
     * データベースからのこのEntityをリフレッシュ。
     */
    public void refresh() {
        Ebean.refresh(this);
    }

    /**
     * 指定したサーバーのデータベースからこの実体をリフレッシュ。
     *
     * @param 接続するサーバー名
     */
    public void refresh(String server) {
        Ebean.getServer(server).refresh(this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        Object id = _getId();
        Object otherId = ((Model) other)._getId();
        if (id == null) {
            return false;
        }
        if (otherId == null) {
            return false;
        }
        return id.equals(otherId);
    }

    @Override
    public int hashCode() {
        Object id = _getId();
        return id == null ? super.hashCode() : id.hashCode();
    }
    /**
     * Ebean queriesのhelper。
     *
     *
     * @see <a href="http://www.avaje.org/static/javadoc/pub/">Ebean API
     * documentation</a>
     */
    public static class Finder<I, T> implements Query<T> {
        private static final long serialVersionUID = 8531245739641223373L; 
        private final Class<I> idType;
        private final Class<T> type;
        private final String serverName;
        private Query<T> query;

        /**
         * Finderを作成。
         * @param Primary Key
         * @param Entity Class
         */
        public Finder(Class<I> idType, Class<T> type) {
            this("default", idType, type);
        }

        /**
         * Finderを作成。
         * 
         * @param 接続するサーバー名
         * @param Primary Key
         * @param Entity Class
         */
        @SuppressWarnings("unchecked")
        public Finder(String serverName, Class<I> idType, Class<T> type) {
            this.type = type;
            this.idType = idType;
            this.serverName = serverName;
        }

        private EbeanServer server() {
            return Ebean.getServer(serverName);
        }

        /**
         * 接続するサーバーを変更
         * 
         * @param 接続するサーバー名
         * 
         * @return Finder<I, T>
         */
        @SuppressWarnings("unchecked")
        public Finder<I, T> on(String server) {
            return new Finder(server, idType, type);
        }

        /**
         * Entityのすべてを返す。
         * 
         * @return Entityのリスト
         */
        public List<T> all() {
            return server().find(type).findList();
        }

        /**
         * Primary Keyを元に、Entityを返す。
         * 
         * @return Entity
         */
        public T byId(I id) {
            return server().find(type, id);
        }

        /**
         * Primary Keyで参照を検索。
         * @param Primary Key
         * 
         * @return 参照Type
         */
        public T ref(I id) {
            return server().getReference(type, id);
        }

        /**
         * データベースに戻らずに、Entityのリストをローカルにソートしたフィルタを作成。
         * 
         * @return Filter<T>
         */
        public Filter<T> filter() {
            return server().filter(type);
        }

        /**
         * queryの作成。
         * 
         * @return Query<T>
         */
        public Query<T> query() {
            return server().find(type);
        }

        /**
         * 次のPrimary Keyの値を返す。
         * 
         * @return ID
         */
        @SuppressWarnings("unchecked")
        public I nextId() {
            return (I) server().nextId(type);
        }

        /**
         * queryのキャンセル。
         */
        @Override
        public void cancel() {
            query().cancel();
        }

        /**
         * queryのコピー。
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> copy() {
            return query().copy();
        }

        /**
         * propertyをロードするためにパスを指定。
         * @param propertyの記載されたファイルへのパス
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> fetch(String path) {
            return query().fetch(path);
        }

        /**
         * propertyをロードするためにパスを指定とjoinConfigを指定。
         * 
         * @param propertyの記載されたファイルへのパス
         * @param joinConfig
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> fetch(String path, FetchConfig joinConfig) {
            return query().fetch(path, joinConfig);
        }

        /**
         * propertyをロードするためのパスを指定とfetchPropertiesを指定。
         * 
         * @param propertyの記載されたファイルへのパス
         * @param fetchProperties
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> fetch(String path, String fetchProperties) {
            return query().fetch(path, fetchProperties);
        }

        /**
         * 関連するプロパティ、fetchPropertiesを使用するためにFetchConfigを指定。
         * 
         * @param 関連するプロパティ
         * @param fetchプロパティ
         * @param FetchConfig
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> fetch(String assocProperty, String fetchProperties, FetchConfig fetchConfig) {
            return query().fetch(assocProperty, fetchProperties, fetchConfig);
        }

        /**
         * 多数listのフィルタを適用します。
         * 
         * @param プロパティ名
         * 
         * @return ExpressionList<T>
         */
        @Override
        public ExpressionList<T> filterMany(String propertyName) {
            return query().filterMany(propertyName);
        }

        /**
         * バックグラウンドのスレッドでIDの検索を実行。
         * 
         * @return FutureIds<T>
         */
        @Override
        public FutureIds<T> findFutureIds() {
            return query().findFutureIds();
        }

        /**
         * バックグラウンドのスレッドで複数のクエリを実行。
         * 
         * @return FutureList<T>
         */
        @Override
        public FutureList<T> findFutureList() {
            return query().findFutureList();
        }

        /**
         * バックグラウンドのスレッドでcountのクエリを実行。
         * 
         * @return FutureRowCount<T>
         */
        @Override
        public FutureRowCount<T> findFutureRowCount() {
            return query().findFutureRowCount();
        }

        /**
         * queryを実行し、IDのリストとして結果を返却。
         * 
         * @return オブジェクトのリスト
         */
        @Override
        public List<Object> findIds() {
            return query().findIds();
        }

        /**
         * queryを実行し、オブジェクトのリストとして結果を返却。
         * 
         * @return オブジェクトのリスト
         */
        @Override
        public List<T> findList() {
            return query().findList();
        }

        /**
         * queryを実行し、オブジェクトのマップとして結果を返却。
         * 
         * @return オブジェクトのマップ
         */
        @Override
        public Map<?, T> findMap() {
            return query().findMap();
        }

        /**
         * queryを実行し、オブジェクトのマップとして結果を返却。
         * 
         * @param マップのkey
         * @param 返却してほしいオブジェクトのクラス
         * 
         * @return オブジェクトのマップ
         */
        @Override
        public <K> Map<K, T> findMap(String a, Class<K> b) {
            return query().findMap(a, b);
        }

        /**
         * queryのためのPagingListを返却
         * 
         * @param ページサイズ
         * 
         * @return PagingList<T>
         */
        @Override
        public PagingList<T> findPagingList(int pageSize) {
            return query().findPagingList(pageSize);
        }

        /**
         * queryが返却するEntityの数を返却。
         * 
         * @return Entityの数
         */
        @Override
        public int findRowCount() {
            return query().findRowCount();
        }

        /**
         * queryを実行し、1セットのオブジェクトとして結果を返却
         * 
         * @return オプジェクとのセット
         */
        @Override
        public Set<T> findSet() {
            return query().findSet();
        }

        /**
         * queryを実行し一つのEntityを返却。マッチしなかった場合はnullを返却
         * 
         * @return Entity
         */
        @Override
        public T findUnique() {
            return query().findUnique();
        }
        
        public void findVisit(QueryResultVisitor<T> visitor) {
            query().findVisit(visitor);
        }

        @Override
        public QueryIterator<T> findIterate() {
            return query().findIterate();
        }

        /**
         * queryに使われたExpressionFactoryを返却。
         * 
         * @retun ExpressionFactory
         */
        @Override
        public ExpressionFactory getExpressionFactory() {
            return query().getExpressionFactory();
        }

        /**
         * 最初のカラムのidを返却。
         * 
         * @retun ID
         */
        @Override
        public int getFirstRow() {
            return query().getFirstRow();
        }

        /**
         * queryの実行のために生成されたSQLを返却。
         * 
         * @retun SQL文
         */
        @Override
        public String getGeneratedSql() {
            return query().getGeneratedSql();
        }

        /**
         * queryで得られた最後のIDを返却
         * 
         * @return ID
         */
        @Override
        public int getMaxRows() {
            return query().getMaxRows();
        }

        /**
         * queryで使用する準備ができていたRawSqlを返却。
         * 
         * @return RawSql
         */
        @Override
        public RawSql getRawSql() {
            return query().getRawSql();
        }

        /**
         * queryのタイプを返却。(List, Set, Map, Bean, rowCount etc)
         * 
         * @return Query.Type
         */
        @Override
        public Query.Type getType() {
            return query().getType();
        }

        @Override
        public UseIndex getUseIndex() {
            return query().getUseIndex();
        }

        /**
         * queryの持っているhaving節を返却
         * 
         * @return ExpressionList<T>
         */
        @Override
        public ExpressionList<T> having() {
            return query().having();
        }

        /**
         * having節をExpressionに追加して、Queryを返却。
         * 
         * @param having節を追加したExpression
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> having(com.avaje.ebean.Expression addExpressionToHaving) {
            return query().having(addExpressionToHaving);
        }

        /**
         * having節を追加して、Queryを返却。
         * 
         * @param having節
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> having(String addToHavingClause) {
            return query().having(addToHavingClause);
        }

        /**
         * queryがautoFetchになっていれば、trueを返却。
         * 
         * @return boolean
         */
        @Override
        public boolean isAutofetchTuned() {
            return query().isAutofetchTuned();
        }

        /**
         * {@link #fetch(String)}　と同じ
         */
        @Deprecated
        @Override
        public Query<T> join(String path) {
            return query().join(path);
        }

        /**
         * {@link #fetch(String, FetchConfig)}　と同じ
         */
        @Deprecated
        @Override
        public Query<T> join(String path, JoinConfig joinConfig) {
            return query().join(path, joinConfig);
        }

        /**
         * {@link #fetch(String, String)}　と同じ
         */
        @Deprecated
        @Override
        public Query<T> join(String assocProperty, String fetchProperties) {
            return query().join(assocProperty, fetchProperties);
        }

        @Deprecated
        @Override
        public Query<T> join(String assocProperty, String fetchProperties, JoinConfig joinConfig) {
            return query().join(assocProperty, fetchProperties, joinConfig);
        }

        /**
         * order by節に昇順、降順のプロパティを追加したOrderBy<T>を返却。
         * <p>{@link #orderBy}　と同じ。
         * 
         * @return OrderBy<T>
         */
        @Override
        public OrderBy<T> order() {
            return query().order();
        }

        /**
         * order by節をセットし、新しくorder by節を反映したQueryを返却。
         * <p>{@link #orderBy(String)} と同じ。
         * 
         * @param order by 節
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> order(String orderByClause) {
            return query().order(orderByClause);
        }

        /**
         * order by節に昇順、降順のプロパティを追加したOrderBy<T>を返却。
         * <p>@link #order}　と同じ。
         */
        @Override
        public OrderBy<T> orderBy() {
            return query().orderBy();
        }

         /**
         * order by節をセットし、新しくorder by節を反映したQueryを返却。
         * <p>{@link #order(String)} と同じ。
         * 
         * @param order by 節
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> orderBy(String orderByClause) {
            return query().orderBy(orderByClause);
        }

        /**
         * Select　節をセットし、セットされたQueryを返却。
         * select("id,name")
         * 
         * @param fetchProperties
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> select(String fetchProperties) {
            return query().select(fetchProperties);
        }

        /**
         * Autofetchを利用するかセットし、セットされたQueryを返却。
         * 
         * @param boolean
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> setAutofetch(boolean autofetch) {
            return query().setAutofetch(autofetch);
        }

        /**
         * バックグラウンドのスレッドで継続させるべき列の数をセットし、セットされたQueryを返却。
         * 
         * @param 列の数
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> setBackgroundFetchAfter(int backgroundFetchAfter) {
            return query().setBackgroundFetchAfter(backgroundFetchAfter);
        }

        /**
         * JDBCにStatement.fetchSizeを変換させるためのサイズをセットし、そのQueryを返却。
         * 
         * @param fetchサイズ
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> setBufferFetchSizeHint(int fetchSize) {
            return query().setBufferFetchSizeHint(fetchSize);
        }

        /**
         * QueryにDistinctにするかセットし、そのqueryを返却
         * 
         * @param boolean
         * 
         * @retun Query<T>
         */
        @Override
        public Query<T> setDistinct(boolean isDistinct) {
            return query().setDistinct(isDistinct);
        }

        /**
         * 最初の行番号をセットし、セットしたqueryを返却。
         * 
         * @param 最初の行番号
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> setFirstRow(int firstRow) {
            return query().setFirstRow(firstRow);
        }

        /**
         * queryにIDをセット。
         * 
         * @param ID オブジェクト
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> setId(Object id) {
            return query().setId(id);
        }

        /**
         * 一列づつの処理させるqueryのリスナーをセットし、そのQueryを返却。
         * 
         * @param 処理させるqueryのリスナー
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> setListener(QueryListener<T> queryListener) {
            return query().setListener(queryListener);
        }

        /**
         * loadBeanCacheを利用するかどうかをセットし、そのQueryを返却。
         * 
         * @param boolean
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> setLoadBeanCache(boolean loadBeanCache) {
            return query().setLoadBeanCache(loadBeanCache);
        }

        /**
         * mapのkeyを使ってpropertyをセットし、そのQueryを返却。
         * 
         * @param mapのkey
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> setMapKey(String mapKey) {
            return query().setMapKey(mapKey);
        }

        /**
         * queryで返却する最大数をセットし、そのQueryを返却。
         * 
         * @param 返却する最大数
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> setMaxRows(int maxRows) {
            return query().setMaxRows(maxRows);
        }

        /**
         * OrderByオブジェクトを使って、order by節を置換します。
         * <p>{@link #setOrderBy(com.avaje.ebean.OrderBy)} と同じ
         * 
         * @param OrderBy<T>
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> setOrder(OrderBy<T> orderBy) {
            return query().setOrder(orderBy);
        }

        /**
         * OrderByオブジェクトを使って、order by節を置換します。
         * <p>{@link #setOrder(com.avaje.ebean.OrderBy)} と同じ
         * 
         * @param OrderBy<T>
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> setOrderBy(OrderBy<T> orderBy) {
            return query().setOrderBy(orderBy);
        }

        /**
         * そのポジションに指定されたパラメータにバインドをセットし、そのQueryを返却。
         * 
         * @param ポジション
         * @param ポジションを指定するオブジェクト
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> setParameter(int position, Object value) {
            return query().setParameter(position, value);
        }

        /**
         * 一つの指定されたパラメータをバインドをセットし、そのQueryを返却。
         * 
         * @param パラメータ
         * @param パラメータを指定するオブジェクト
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> setParameter(String name, Object value) {
            return query().setParameter(name, value);
        }

        /**
         * Deprecated.
         */
        @Deprecated
        @Override
        public Query<T> setQuery(String oql) {
            return query().setQuery(oql);
        }

        /**
         * 
         * RawSqlをセットし、そのQueryを返却。
         * 
         * @param RawSql
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> setRawSql(RawSql rawSql) {
            return query().setRawSql(rawSql);
        }

        /**
         * read-onlyにするかどうかセットし、そのQueryを返却。
         * 
         * @param boolean
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> setReadOnly(boolean readOnly) {
            return query().setReadOnly(readOnly);
        }

        /**
         * timeoutをセットし、そのQueryを返却
         * 
         * @param timeout値(seconds)
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> setTimeout(int secs) {
            return query().setTimeout(secs);
        }

        /**
         * Cacheを利用するかどうかをセットし、そのQueryを返却。
         * 
         * @param boolean
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> setUseCache(boolean useBeanCache) {
            return query().setUseCache(useBeanCache);
        }

        /**
         * Cacheを利用するかどうかをセットし、そのQueryを返却。
         * 
         * @param boolean
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> setUseQueryCache(boolean useQueryCache) {
            return query().setUseQueryCache(useQueryCache);
        }
        @Override
        public Query<T> setUseIndex(UseIndex useIndex) {
            return query().setUseIndex(useIndex);
        }

        /**
         * 'vanilla mode'を使用するかをセットし、そのQueryを返却。
         * 
         * @param boolean
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> setVanillaMode(boolean vanillaMode) {
            return query().setVanillaMode(vanillaMode);
        }

        /**
         * where節に追加できるExpressionListを返却。
         * 
         * @return ExpressionList<T>
         */
        @Override
        public ExpressionList<T> where() {
            return query().where();
        }

        /**
         * where節に一つのExpressionを追加し、そのQueryを返却。
         * 
         * @param where節に追加するExpression
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> where(com.avaje.ebean.Expression expression) {
            return query().where(expression);
        }

        /**
         * where節を追加し、そのQueryを返却。
         * 
         * @param where節
         * 
         * @return Query<T>
         */
        @Override
        public Query<T> where(String addToWhereClause) {
            return query().where(addToWhereClause);
        }
    }
}
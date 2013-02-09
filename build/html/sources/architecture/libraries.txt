=============================================
使用するライブラリ
=============================================

テンプレートエンジン 
=============================================
CreamyではFXMLをテンプレートとして利用できるようになっています。

テンプレートエンジンにはApache Velocity 1.7を採用しています。

テンプレートには、以下のようにVTL(Velocity Template Language)が記述できます。

.. code-block:: xml

  #if( $name )
    <Label fx:id="name" text="$name" />
  #end


JPA永続化とO/Rマッパ
=============================================
JPAに従ったデータ永続化のために、O/Rマッパとして、Ebean 2.7.3を採用しています。

ModelとなるクラスはJPAに従って記述することができます。

.. code-block:: java

  @Entity
  @Table(name="company")  
  public class Company extends Model{
      @Id
      private Integer id;
      
      private String name;
  
      public void setId(Integer id){ this.id = id; }
  
      public Integer getId(){ return id; }
  
      public void setName(String name){ this.name = name; }
      
      public String getName(){ return name; }
  }


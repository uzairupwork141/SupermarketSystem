
package panal;

public class M_manu {
    String icon; 
    String name;
    MenuType type ;

    public M_manu(String icon, String name, MenuType type) {
        this.icon = icon;
        this.name = name;
        this.type = type;
    }

    public M_manu() {
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public MenuType getType() {
        return type;
    }
  
    public static enum MenuType
    {
        TITLE, MANU, EMPTY;
    }


}

package com.yong.eyepetizer.lib.data;

/**
 * <b>Project:</b> com.yong.eyepetizer.lib.data <br>
 * <b>Create Date:</b> 2016/6/10 <br>
 * <b>Author:</b> qingyong <br>
 * <b>Description:</b> 发现更多 <br>
 */
public class DisMore {

    /**
     * id : 24
     * name : 时尚
     * alias : null
     * bgPicture : http://img.wdjimg.com/image/video/22192a40de238fe853b992ed57f1f098_0_0.jpeg
     * bgColor :
     */

    private int id;
    private String name;
    private Object alias;
    private String bgPicture;
    private String bgColor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getAlias() {
        return alias;
    }

    public void setAlias(Object alias) {
        this.alias = alias;
    }

    public String getBgPicture() {
        return bgPicture;
    }

    public void setBgPicture(String bgPicture) {
        this.bgPicture = bgPicture;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }
}

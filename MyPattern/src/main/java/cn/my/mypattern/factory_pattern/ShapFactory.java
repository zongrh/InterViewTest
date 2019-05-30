package cn.my.mypattern.factory_pattern;

/**
 * FileName: ShapFactory
 * Author: nanzong
 * Date: 2019/5/29 5:45 PM
 * Description:
 * History:
 */
public class ShapFactory {

    public Shape getShap(String shapType) {
        if (shapType == null) {
            return null;
        }
//        equalsIgnoreCase忽略大小写
        if (shapType.equalsIgnoreCase("CIRCLE")) {
            return new Circle();
        } else if (shapType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle();
        } else if (shapType.equalsIgnoreCase("SQUARE")) {
            return new Square();
        }
        return null;
    }

}

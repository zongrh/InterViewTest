package cn.my.mypattern.factory_pattern;

/**
 * FileName: FactoryPatternDemo
 * Author: nanzong
 * Date: 2019/5/29 5:48 PM
 * Description:
 * History:
 */
public class FactoryPatternDemo {

    public static void main(String[] args) {
        ShapFactory shapFactory = new ShapFactory();

        Shape shape1 = shapFactory.getShap("circle");
        shape1.draw();


        Shape shape2 = shapFactory.getShap("rectangle");
        shape2.draw();

        Shape shape3 = shapFactory.getShap("square");
        shape3.draw();

    }
}

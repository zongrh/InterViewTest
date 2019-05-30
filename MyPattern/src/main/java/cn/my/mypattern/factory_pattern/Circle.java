package cn.my.mypattern.factory_pattern;

/**
 * FileName: Circle
 * Author: nanzong
 * Date: 2019/5/29 5:45 PM
 * Description:
 * History:
 */
public class Circle implements Shape {

    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}

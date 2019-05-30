package cn.my.mypattern.factory_pattern;

/**
 * FileName: Square
 * Author: nanzong
 * Date: 2019/5/29 5:30 PM
 * Description:
 * History:
 */
public class Square implements Shape {


    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}

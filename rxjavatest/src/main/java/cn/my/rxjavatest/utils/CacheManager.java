package cn.my.rxjavatest.utils;

import cn.my.rxjavatest.model.FoodList;

/**
 * FileName: CacheManager
 * Author: nanzong
 * Date: 2019/5/29 9:18 AM
 * Description:
 * History:
 */
public class CacheManager {
    private static CacheManager instance;
    private FoodList foodListJson;
    private CacheManager(){

    }
    public static CacheManager getInstance(){
        if (instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }
    public FoodList getFoodListJsonData(){
        return this.foodListJson;
    }

    public void setFoodListJsonData(FoodList data) {
        this.foodListJson = data;
    }

}

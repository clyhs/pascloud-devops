package com.pascloud.mybatis.multi;

/**
 * 
 * @date 2019年8月1日 上午9:18:20
 *
 * @author 大鱼
 *
 */
public class DataSourceHolder {

	//线程本地环境
    private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();
    //设置数据源
    public static void setDataSource(String customerType) {
        dataSources.set(customerType);
    }
    //获取数据源
    public static String getDataSource() {
        return (String) dataSources.get();
    }
    //清除数据源
    public static void clearDataSource() {
        dataSources.remove();
    }
}
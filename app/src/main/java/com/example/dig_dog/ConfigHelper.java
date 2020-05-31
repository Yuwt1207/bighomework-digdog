package com.example.dig_dog;

// 单例，记录用户名
public class ConfigHelper {
    public String UserName;

    private ConfigHelper() {

    }
    public  static ConfigHelper getInstance() {
        return Singleton.instance;
    }

    private static class Singleton{
        private static ConfigHelper instance = new ConfigHelper();
    }
}

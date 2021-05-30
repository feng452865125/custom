package com.chunhe.custom.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.util.Lists;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.Iterator;
import java.util.List;

/**
 * dict search.
 * 数据字典的工具类 数据字典的内容保存在xml文件中 工具类初始化时，传入xml文件路径，解析xml文件获得json格式的数据
 * 提供根据数据种类获得数据、根据数据种类和键值获得标记信息、根据数据类型获得所有标记信息、
 * 根据数据种类和键值获得所有标记信息的操作
 */
public class DictUtils {

    public static final Logger logger = LogManager.getLogger(DictUtils.class);

    private static JSONArray dict = new JSONArray();
    //    private static JSONArray groups = new JSONArray();
    private static List<JSONObject> arrays = Lists.newArrayList();

    /**
     * 初始化数据字典工具类 传入xml文件路径，解析xml文件获得json格式的数据
     */
    public static void init(String xmlName) {
        new DictUtils(xmlName);
    }

    /**
     * init dict data.
     */
    public DictUtils(String xmlName) {
        try {
            SAXReader saxReader = new SAXReader();
            logger.info("数据字典 加载ing...");
            Document document = saxReader.read(DictUtils.class.getResourceAsStream("/" + xmlName));
            dict = parseDict(document.selectNodes("//app/dicts/dict"));
        } catch (Exception e) {
            logger.error("[DictUtils] exception: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 将列表格式的数据字典转化为json数组格式并返回
     */
    private JSONArray parseDict(List dictElement) {
        JSONArray dict = new JSONArray();
        for (Object object : dictElement) {
            Element dicElement = (Element) object;
            JSONObject dic = parseDict(dicElement);
            dict.add(dic);
        }
        return dict;
    }


    /**
     * 传入需要的数据种类 遍历索引json数组判断数据字典是否经过分组，在数据字典中找到对应的标签组的json数组并返回
     * 若未找到返回null
     */
    public static JSONObject findDicByType(String type) {
        for (JSONObject index : arrays) {
            if (type.equals(index.getString("type"))) {
                // search in dict and return object.
                Iterator<Object> iter = dict.iterator();
                while (iter.hasNext()) {
                    JSONObject dic = (JSONObject) iter.next();
                    if (dic.getString("type").equals(type)) {
                        return dic;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 传入需要的数据种类和键值 获得对应数据种类的标签组的json数组，获得数组中的所有键值，进行遍历。
     * 当匹配到目标键值，返回键值对应的标记 未匹配到返回null
     */
    public static String findValueByTypeAndKey(String type, Integer keyInt) {
        String key = String.valueOf(keyInt);
        JSONObject jsonObject = findDicByType(type);
        JSONArray dict = jsonObject.getJSONArray("dict");
        for (int i = 0; i < dict.size(); i++) {
            JSONObject dic = dict.getJSONObject(i);
            String prefix = dic.getString("value");
            JSONArray keys = dic.getJSONArray("keys");
            for (int j = 0; j < keys.size(); j++) {
                JSONObject keyObj = keys.getJSONObject(j);
                if (key.equals(prefix + keyObj.getString("key"))) {
                    return keyObj.getString("value");
                }
            }
        }
        return null;
    }

    /**
     * get value by type and key.
     */
    @Deprecated
    public static Integer findKeyByTypeAndValue(String type, String value) {
        JSONObject jsonObject = findDicByType(type);
        JSONArray dict = jsonObject.getJSONArray("dict");
        for (int i = 0; i < dict.size(); i++) {
            JSONObject dic = dict.getJSONObject(i);
            JSONArray keys = dic.getJSONArray("keys");
            for (int j = 0; j < keys.size(); j++) {
                JSONObject keyObj = keys.getJSONObject(j);
                if (value.equals(keyObj.getString("value"))) {
                    return keyObj.getInteger("key");
                }
            }
        }
        return null;
    }

    /**
     * get all list values by type
     * <p>
     * 传入数据种类，获得所有标记内容的列表
     */
    public static List<String> findValuesByType(String type) {
        return findValuesByTypeAndKey(type, null);
    }

    /**
     * get list values by type and keys array.
     * <p>
     * 传入数据种类与一组键值，获得对应标记内容的列表并返回 若传入键值为空，则返回所有的标记内容
     */
    public static List<String> findValuesByTypeAndKey(String type, String keyArray) {
        List<String> values = Lists.newArrayList();
        JSONObject jsonObject = findDicByType(type);
        JSONArray dict = jsonObject.getJSONArray("dict");
        for (int i = 0; i < dict.size(); i++) {
            JSONObject dic = dict.getJSONObject(i);
            String prefix = dic.getString("value");
            JSONArray keys = dic.getJSONArray("keys");
            for (int j = 0; j < keys.size(); j++) {
                JSONObject keyObj = keys.getJSONObject(j);
                if (keyArray == null) {
                    values.add(keyObj.getString("value"));
                }
                if (keyArray != null && keyArray.contains(prefix + keyObj.getString("key"))) {
                    values.add(keyObj.getString("value"));
                }
            }
        }
        return values;
    }

    /**
     * get all list values by type
     */
    @Deprecated
    public static <T> List<T> findKeysByType(String type) {
        return findKeysByTypeAndValue(type, null);
    }

    /**
     * get keys by type and value array.
     */
    @Deprecated
    public static <T> List<T> findKeysByTypeAndValue(String type, String valueArray) {
        List<T> values = Lists.newArrayList();
        JSONObject jsonObject = findDicByType(type);
        JSONArray dict = jsonObject.getJSONArray("dict");
        for (int i = 0; i < dict.size(); i++) {
            JSONObject dic = dict.getJSONObject(i);
            JSONArray keys = dic.getJSONArray("keys");
            for (int j = 0; j < keys.size(); j++) {
                JSONObject keyObj = keys.getJSONObject(j);
                if (valueArray == null) {
                    values.add((T) keyObj.get("key"));
                }
                if (valueArray != null && valueArray.contains(keyObj.getString("value"))) {
                    values.add((T) keyObj.get("key"));
                }
            }
        }
        return values;
    }


    /**
     * 从元素中解析出键值，并以json数组格式返回
     */
    private JSONArray parseKey(Element ele) {
        JSONArray keys = new JSONArray();
        Iterator iter = ele.elementIterator("key");
        while (iter.hasNext()) {
            JSONObject key = new JSONObject();
            Element keyElement = (Element) iter.next();
            key.put("key", keyElement.attributeValue("key"));
            key.put("value", keyElement.attributeValue("value"));
            keys.add(key);
        }
        return keys;
    }

    /**
     * 从元素中解析出种类、名字和键值组，将数据类型与分组标志位添加到索引json数组后，以json数组格式返回
     */
    private JSONObject parseDict(Element dicElement) {
        JSONObject dic = new JSONObject();
        dic.put("type", dicElement.attributeValue("type"));
        dic.put("name", dicElement.attributeValue("name"));
        dic.put("keys", parseKey(dicElement));
        JSONObject index_item = new JSONObject();
        index_item.put("type", dicElement.attributeValue("type"));
        arrays.add(index_item);
        return dic;
    }


    public static void main(String[] args) {
        DictUtils.init("dict.xml");
        List<String> keys = DictUtils.findKeysByType("sexType");
        System.out.println(keys);
    }


}

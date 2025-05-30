package com.tongji.xiaohashu.count.biz.redis.count;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author tongji
 * @time 2025/5/20 13:37
 * @description 管理 Redis Key 模式和字段定义
 */
@Service
public class CountSchema {
    private final Map<String, List<String>> schema = new HashMap<>();

//    public void register(String keyPattern, List<String> fields) {
//        schema.put(keyPattern, fields);
//    }

    public CountSchema() {
        // 可从配置文件动态加载
        schema.put("count_content", Arrays.asList("like", "collect", "comment"));
        schema.put("count_user", Arrays.asList("following", "follower", "note"));
    }

    public List<String> getFields(String keyPattern) {
        return schema.getOrDefault(keyPattern, Collections.emptyList());
    }

    public int getFieldIndex(String keyPattern, String field) {
        return getFields(keyPattern).indexOf(field);
    }

    public int getFieldCount(String keyPattern) {
        return getFields(keyPattern).size();
    }
}

package com.xiaoxu.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xx
 * @create 2021/3/11 16:22
 */
@Controller
@RequestMapping("click")
public class StarsController{

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("delete")
    public void del(){
        redisTemplate.delete("key");
    }

    @RequestMapping("index")
    public String index(){
        Integer key = (Integer) redisTemplate.opsForValue().get("key");
        if(key == null)
            redisTemplate.opsForValue().set("key", 1);
        return "index";
    }

    @RequestMapping("star")
    @ResponseBody
    public Map<String, Object> click(String key){
        Integer star = (Integer) redisTemplate.opsForValue().get(key);
        if(star != null){
            star++;
            redisTemplate.opsForValue().set(key, star);
            HashMap<String, Object> map = new HashMap<>();
            map.put("star", star);
            return map;
        }
        return null;
    }


}

package com.xiaoxu.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xx
 * @create 2021/3/11 16:22
 */
@Controller
@RequestMapping("click")
public class StarsController{

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("stars")
    @ResponseBody
    public Map<String, Object> stars(String name){
        Map<String, Object> map = new HashMap<>();
        ZSetOperations<String, Object> zadd = redisTemplate.opsForZSet();
        zadd.incrementScore("paihang", name, 1);
        map.put("result", 1);
        return map;
    }

    @RequestMapping("paihang")
    public String index1(ModelMap model){
        ZSetOperations<String, Object> zadd = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<Object>> paihang = zadd.reverseRangeWithScores("paihang", 0, -1);
        assert paihang != null;
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = paihang.iterator();
        List<Members> list = new ArrayList<>();
        while(iterator.hasNext()){
            Members member = new Members();
            ZSetOperations.TypedTuple<Object> next = iterator.next();
            member.setName((String) next.getValue());
            member.setStar(next.getScore());
            list.add(member);
        }
        model.put("members", list);
        return "paihang";
    }


    @RequestMapping("paihang/index")
    public String toPaiHang(ModelMap model){
        List<Members> members = new ArrayList<>();
        members.add(new Members("xiaoxu", (double) 1));
        members.add(new Members("xiaoli", (double) 1));
        members.add(new Members("xiaojiang", (double) 1));
        model.put("members", members);
        ZSetOperations<String, Object> zadd = redisTemplate.opsForZSet();
        zadd.add("paihang", "xiaoxu", 1);
        zadd.add("paihang", "xiaoli", 1);
        zadd.add("paihang", "xiaojiang", 1);
        return "paihang";
    }

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

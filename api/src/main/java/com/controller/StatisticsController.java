package com.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.LaoshiEntity;
import com.entity.ZuoyepigaiEntity;
import com.entity.ZuoyexinxiEntity;
import com.entity.view.ZuoyepigaiView;
import com.service.ZuoyepigaiService;
import com.service.ZuoyexinxiService;
import com.utils.MPUtil;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
public class StatisticsController {

    @Autowired
    private ZuoyepigaiService zuoyepigaiService;

    @RequestMapping(value = "/statistics")
    public R statistics() {
        EntityWrapper<ZuoyepigaiEntity> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("suoshukemu,max(pingfen) as max,min(pingfen) as min,avg(pingfen) as avg,count(pingfen) as count")
                .groupBy("suoshukemu");
        List<Map<String, Object>> maps = zuoyepigaiService.selectMaps(wrapper);

        for (Map<String, Object> mp : maps) {
            EntityWrapper<ZuoyepigaiEntity> wrapper1 = new EntityWrapper<>();
            wrapper1.eq("suoshukemu", mp.get("suoshukemu"));
            wrapper1.ge("pingfen", 60);
            int pass = zuoyepigaiService.selectCount(wrapper1);
            mp.put("pass", pass);


            EntityWrapper<ZuoyepigaiEntity> wrapper2 = new EntityWrapper<>();
            wrapper2.eq("suoshukemu", mp.get("suoshukemu"));
            wrapper2.isNotNull("xueshengzuoye");
            int finish = zuoyepigaiService.selectCount(wrapper1);
            mp.put("finish_count", finish);
            System.out.println(mp);
        }
        return R.ok("查询成功").put("data", maps);
    }
}

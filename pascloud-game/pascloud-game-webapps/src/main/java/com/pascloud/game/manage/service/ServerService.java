package com.pascloud.game.manage.service;

import org.springframework.stereotype.Service;

import com.pascloud.core.utils.SymbolUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务器
 *
 */
@Service
public class ServerService {

    /**
     * 获取网页属性
     * <p>aa:bb<br>cc:dd</></p>
     *
     * @param string
     * @return
     */
    public Map<String, String> getHTMLProPerites(String string) {
        Map<String, String> map = new HashMap<>();
        if (string == null) {
            return map;
        }

        if (string != null) {
            String[] properites = string.split("<br>");
            if (properites != null) {
                for (String str : properites) {
                    str=str.trim().replace("\n","<br>").replace("\t","&nbsp;&nbsp;");     //替换换行符合缩进
                    String[] keyValue = str.split(SymbolUtil.MAOHAO_REG);
                    if (keyValue.length == 2) {
                        map.put(keyValue[0], keyValue[1]);
                    }
                }
            }
        }
        return map;
    }
}

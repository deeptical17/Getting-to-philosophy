/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hd.wiki.controller;

import com.hd.wiki.service.GettingToPhilosophy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Deepti
 */
@Controller
public class WikiController {

    @Autowired
    GettingToPhilosophy gettingToPhilosophy;

    /**
     * get date for update
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "wiki/{name}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map> getUpdateData(@PathVariable String name) {
        Map param = new LinkedHashMap();
        Map map = new LinkedHashMap();
        try {
            param = gettingToPhilosophy.getPathFor(name);
//            param.put(name, "https://en.wikipedia.org/wiki/" + name);
               map.put(name, "https://en.wikipedia.org/wiki/" + name);
            for (Iterator<Map.Entry<String, String>> it = param.entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, String> entry = it.next();
                map.put(entry.getKey(), entry.getValue());
            }
        } catch (Exception ex) {
            Logger.getLogger(WikiController.class.getName()).log(Level.SEVERE, null, ex);
            param.put(name, "No record Found");
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
//        param.put("ITEMID", String.valueOf(id));
//        param.put("REFID", String.valueOf(id));
//        param = itemService.getUpdateData(param);

    }
}

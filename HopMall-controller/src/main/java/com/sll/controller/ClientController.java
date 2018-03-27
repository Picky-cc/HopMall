package com.sll.controller;

import com.sll.model.Client;
import com.sll.service.ClientService;
import com.sll.service.ZXingTest;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/ssl/client" ,method = RequestMethod.GET)
    public String getClient(){
        return "client";
    }

    @RequestMapping(value = "/ssl/clientInfo" ,method = RequestMethod.GET)
    public String getClientInfo(){
        return "clientInfo";
    }

    @RequestMapping(value = "/ssl/clientInfos" , method = RequestMethod.GET)
    public @ResponseBody List<Client> getClientInfo(HttpServletRequest request, ModelMap model) throws Exception{
        List<Client> clients = clientService.getClient();
        for (Client client : clients){
            logger.info("This is ClientInfo : name - {} , age - {} ",client.getName(),client.getAge());
            logger.error("-------------------------------------------------------------------");
        }
        return clients;
    }

    @RequestMapping(value = "/ssl/clientByName" , method = RequestMethod.GET)
    public @ResponseBody List<Client> getClientInfoByName(@RequestParam Map<String,String> paramMap){
        String name = paramMap.get("username");
        if (name == null){
            return null;
        }
        List<Client> clients = clientService.getClientByName(name);
        return clients;
    }

    @RequestMapping(value = "/ssl/QRImage" , method = RequestMethod.POST)
    public @ResponseBody String getQRImage(@RequestParam Map<String,String> paramMap) throws Exception{
        String content = paramMap.get("content");
        if (content == null){
            return null;
        }
        String image = ZXingTest.generalQRcode(content);
        image = image.replaceAll("\n","").replaceAll("\r","");
        Map<String,String> response = new HashMap<>();
        response.put("image",image);
        JSONObject json = JSONObject.fromObject(response);
        return json.toString();
    }
}



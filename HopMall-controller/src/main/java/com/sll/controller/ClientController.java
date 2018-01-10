package com.sll.controller;

import com.sll.model.Client;
import com.sll.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/ssl/client" ,method = RequestMethod.GET)
    public String getClient(){
        return "client";
    }

    @RequestMapping(value = "/ssl/client" , method = RequestMethod.POST)
    public @ResponseBody List<Client> getClientInfo(HttpServletRequest request, ModelMap model) throws Exception{
        List<Client> clients = clientService.getClient();
        model.addAttribute("clients",clients);
        for (Client client : clients){
            logger.info("ClientInfo : name - {} , age - {} ",client.getName(),client.getAge());
        }
        return clients;
    }
}

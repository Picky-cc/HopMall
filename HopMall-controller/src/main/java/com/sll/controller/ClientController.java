package com.sll.controller;

import com.sll.model.Client;
import com.sll.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/ssl/client" ,method = RequestMethod.GET)
    public String getClient(){
        return "client";
    }

    @RequestMapping(value = "/ssl/client" , method = RequestMethod.POST)
    public String getClientInfo(HttpServletRequest request, ModelMap model) throws Exception{
        List<Client> clients = clientService.getClient();
        model.addAttribute("clients",clients);
        return "client";
    }
}

package com.sll.serviceImpl;

import com.sll.model.Client;
import com.sll.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientService clientService;

    public List<Client> getClient() {
        List<Client> list = clientService.getClient();
        if (list == null || list.size() == 0){
            return null;
        }else {
            return list;
        }
    }
}

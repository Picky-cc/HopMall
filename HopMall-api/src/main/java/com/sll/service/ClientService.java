package com.sll.service;

import com.sll.model.Client;

import java.util.List;

public interface ClientService {

    public List<Client> getClient();

    public List<Client> getClientByName(String name);
}

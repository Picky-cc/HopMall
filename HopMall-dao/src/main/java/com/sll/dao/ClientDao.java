package com.sll.dao;

import com.sll.model.Client;

import java.util.List;

public interface ClientDao {

    public List<Client> getClient();

    public List<Client> getClientByName(String name);
}

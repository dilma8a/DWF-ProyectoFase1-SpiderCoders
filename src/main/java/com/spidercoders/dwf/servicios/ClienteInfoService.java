package com.spidercoders.dwf.servicios;

import com.spidercoders.dwf.pojos.ClienteInfo;

import java.util.List;

public interface ClienteInfoService {

    List<ClienteInfo> findAll();

    ClienteInfo findByDui(String dui);

    ClienteInfo findById(Integer id);

    void save(ClienteInfo cliente);

    void update(ClienteInfo cliente);
}

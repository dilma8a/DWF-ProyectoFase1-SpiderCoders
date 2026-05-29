package com.spidercoders.dwf.servicios.impl;

import com.spidercoders.dwf.dao.ClienteInfoDao;
import com.spidercoders.dwf.pojos.ClienteInfo;
import com.spidercoders.dwf.servicios.ClienteInfoService;

import java.util.List;

public class ClienteInfoServiceImpl implements ClienteInfoService {

    private final ClienteInfoDao clienteInfoDao;

    public ClienteInfoServiceImpl() {
        this.clienteInfoDao = new ClienteInfoDao();
    }

    @Override
    public List<ClienteInfo> findAll() {
        return clienteInfoDao.findAll();
    }

    @Override
    public ClienteInfo findByDui(String dui) {
        return clienteInfoDao.findByDui(dui);
    }

    @Override
    public ClienteInfo findById(Integer id) {
        return clienteInfoDao.findById(id);
    }

    @Override
    public void save(ClienteInfo cliente) {
        clienteInfoDao.save(cliente);
    }

    @Override
    public void update(ClienteInfo cliente) {
        clienteInfoDao.update(cliente);
    }
}

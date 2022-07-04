package org.peerstock.server.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractConverter {

    @Autowired
    private ModelMapper modelMapper;

    protected ModelMapper getModelMapper() {
        return modelMapper;
    }
}

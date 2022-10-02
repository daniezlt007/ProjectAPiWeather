package com.apiweather.service;

import com.apiweather.model.WeatherObject;
import com.apiweather.model.response.TempResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WeatherObjectService {

    public void salvar(TempResponse tempResponse);
    public List<TempResponse> findAll();
    public List<TempResponse> findByNomeCIdade(String nome);
    public List<TempResponse> findByUf(String uf);

}

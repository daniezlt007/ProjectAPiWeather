package com.apiweather.repository;

import com.apiweather.model.WeatherObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherObjectRepository extends JpaRepository<WeatherObject, Long> {

    public List<WeatherObject> findByNomeCidade(String nomeCidade);
    public List<WeatherObject> findByUf(String uf);

}

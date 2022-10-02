package com.apiweather.service;

import com.apiweather.exception.ResourceNotFoundException;
import com.apiweather.model.Coordenada;
import com.apiweather.model.Main;
import com.apiweather.model.WeatherObject;
import com.apiweather.model.response.TempResponse;
import com.apiweather.repository.WeatherObjectRepository;
import com.apiweather.util.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceConsumerOpenApiWeather implements WeatherObjectService {

    @Value("${spring.application.secretkey}")
    private String apiKey;

    @Value("${spring.aplication.urlbaseconsumer}")
    private String urlBase;

    @Autowired
    private WeatherObjectRepository weatherObjectRepository;

    public TempResponse serviceSearchTempForCityStateInBrazil(String cidade, String uf) {
        try {
            HttpEntity<String> response = getStringHttpEntity(cidade, uf);
            TempResponse tempResponse = getObjectTempResponse(response, uf);
            if(tempResponse != null){
                salvar(tempResponse);
                return tempResponse;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HttpEntity<String> getStringHttpEntity(String cidade, String uf) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        StringBuilder stringBuilderParams = getStringParamsBuilder(cidade, uf);

        Map<String,String> params = new HashMap<>();

        String urlTemplate = getStringUrlTemplate(stringBuilderParams);

        HttpEntity<String> response = getStringHttpEntityResponse(restTemplate, entity, params, urlTemplate);

        return response;
    }

    private String getStringUrlTemplate(StringBuilder stringParamsBuilder) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(urlBase)
                .queryParam("q", stringParamsBuilder.toString())
                .queryParam("lang", "pt_BR")
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .encode()
                .toUriString();
        return urlTemplate;
    }

    private HttpEntity<String> getStringHttpEntityResponse(RestTemplate restTemplate, HttpEntity<?> entity, Map<String, String> params, String urlTemplate) {
        try {
            HttpEntity<String> response = restTemplate.exchange(
                    urlTemplate,
                    HttpMethod.GET,
                    entity,
                    String.class,
                    params
            );
            return response;
        }catch (HttpClientErrorException e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    private StringBuilder getStringParamsBuilder(String cidade, String uf) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(cidade);
        stringBuilder.append(",");
        stringBuilder.append("br-" + uf);
        return stringBuilder;
    }

    private TempResponse getObjectTempResponse(HttpEntity<String> response, String uf) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode nodeMain  = root.get("main");
        JsonNode nodeCoord = root.get("coord");
        JsonNode nodeCidade = root.get("name");

        Main main = new Main();
        main.setTemp(nodeMain.get("temp").asDouble());
        main.setTempMin(nodeMain.get("temp_min").asDouble());
        main.setTempMax(nodeMain.get("temp_max").asDouble());
        Coordenada coord = new Coordenada();
        coord.setLat(nodeCoord.get("lat").asDouble());
        coord.setLon(nodeCoord.get("lon").asDouble());
        String nomeCidade = nodeCidade.asText();
        TempResponse tempResponse = new TempResponse(main, coord, nomeCidade, uf);
        return tempResponse;
    }

    @Override
    public void salvar(TempResponse tempResponse) {
        if(tempResponse != null){
            this.weatherObjectRepository.save(new WeatherObject(tempResponse));
        }
    }

    @Override
    public List<TempResponse> findAll() {
        List<TempResponse> allTempResponse = new ArrayList<>();
        List<WeatherObject> allWeatherObject = this.weatherObjectRepository.findAll();
        return getTempResponses(allWeatherObject, allTempResponse);
    }

    @Override
    public List<TempResponse> findByNomeCIdade(String nomeCidade) {
        List<TempResponse> byNomeCidadeTempResponse = new ArrayList<>();
        List<WeatherObject> byNomeCidadeWeatherObject = this.weatherObjectRepository.findByNomeCidade(nomeCidade);
        return getTempResponses(byNomeCidadeWeatherObject, byNomeCidadeTempResponse);
    }

    @Override
    public List<TempResponse> findByUf(String uf) {
        List<TempResponse> byUfTempResponse = new ArrayList<>();
        List<WeatherObject> byUfWeatherObject = this.weatherObjectRepository.findByUf(uf.toUpperCase());
        return getTempResponses(byUfWeatherObject, byUfTempResponse);
    }

    private List<TempResponse> getTempResponses(List<WeatherObject> allWeather,List<TempResponse> allTempResponse) {
        for (WeatherObject weatherObject: allWeather) {
            allTempResponse.add(new TempResponse(weatherObject));
        }
        return allTempResponse;
    }
}

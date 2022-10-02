package com.apiweather.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {

    public static final String URL_BASE = "https://api.openweathermap.org/data/2.5/weather";

    public static String converterDataHora(LocalDateTime agora){
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String horaFormatada = formatterHora.format(agora);
        return horaFormatada;
    }


}

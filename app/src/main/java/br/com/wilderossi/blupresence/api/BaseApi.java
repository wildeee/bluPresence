package br.com.wilderossi.blupresence.api;

import android.os.AsyncTask;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public abstract class BaseApi<T> extends AsyncTask<Void, Void, T> implements ApiInterface {

    protected final String baseUrl;
    protected final RestTemplate rest;

    public BaseApi(String baseUrl){
        this.baseUrl = baseUrl;
        this.rest = new RestTemplate();
        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    protected abstract String getRelativeUrl();

    @Override
    public String getUrl() {
        return baseUrl + getRelativeUrl();
    }
}

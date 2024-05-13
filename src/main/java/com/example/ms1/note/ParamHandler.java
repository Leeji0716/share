package com.example.ms1.note;

import lombok.Getter;
import lombok.Setter;

import java.net.URLEncoder;
import java.util.SplittableRandom;

@Getter
@Setter
public class ParamHandler {
    private String keyword;
    private Boolean isSearchModal;
    private String sort;

    public ParamHandler(){
        this.keyword = "";
        this.isSearchModal = false;
        this.sort = "Date";
    }
    public String getQueryParam(){
        return String.format("?keyword=%s&isSearchModal=%s&sort=%s", URLEncoder.encode(keyword), isSearchModal.toString(), sort);
    }

    public String getParamUrl(String url){
        return url + getQueryParam();
    }

    public String getRedirectUrl(String url){
        return "redirect:" + url + getQueryParam();
    }
}

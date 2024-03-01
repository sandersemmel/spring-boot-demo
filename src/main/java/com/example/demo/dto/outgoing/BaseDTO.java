package com.example.demo.dto.outgoing;

import java.util.List;

public class BaseDTO<T> {
    
    List<T> _data;
    String explanation;

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public List<T> getData() {
        return _data;
    }

    public void setData(List<T> data) {
        _data = data;
    }
}

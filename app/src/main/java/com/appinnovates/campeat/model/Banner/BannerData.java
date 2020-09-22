
package com.appinnovates.campeat.model.Banner;

import com.google.gson.annotations.Expose;

import java.util.List;

@SuppressWarnings("unused")
public class BannerData {

    @Expose
    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

}

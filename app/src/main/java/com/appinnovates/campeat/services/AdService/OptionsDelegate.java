package com.appinnovates.campeat.services.AdService;

import java.util.ArrayList;

public interface OptionsDelegate {
    void options(ArrayList<Option> options);
    void failure(String message);
}

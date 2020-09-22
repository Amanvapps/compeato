package com.appinnovates.campeat.services.AdService;

import java.util.ArrayList;

public interface PointsDelegate {
    void onSuccess(ArrayList<TADPEntry> tadpEntries,ArrayList<TADPEntry> tadEntries,int totalTADPPoints,int totalTADPoints);
    void onFailure(String message);
}

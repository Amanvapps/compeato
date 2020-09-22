package com.appinnovates.campeat.services.BookingService;

import android.content.Context;

import com.appinnovates.campeat.model.BookingDealModel;
import com.appinnovates.campeat.model.HomeBranchDeals.BranchDealModel;
import com.appinnovates.campeat.services.DealService.DealStore;
import com.appinnovates.campeat.utils.DateFormatUtil;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingDealStore {
    private static DealStore dealStore = new DealStore();

    public static List<BookingDealModel> saveData(List<ParseObject> objects,Context context) {

        List<BookingDealModel> bookingDealModelsList = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            for (ParseObject parseObject : objects) {
                bookingDealModelsList.add(fetchBooking(parseObject,context));
            }
        }
        return bookingDealModelsList;
    }

    public static BookingDealModel fetchBooking(ParseObject parseObject, Context context){
        BookingDealModel bookingDealModel = new BookingDealModel();
        bookingDealModel.setId(parseObject.getObjectId());
        try {
            int peopleCount = (int) parseObject.getNumber("no_of_people");
            bookingDealModel.setNo_of_people(String.valueOf(peopleCount));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Date bookingDate = (Date) parseObject.get("booking_date");
        Date fulfillmentDate = (Date) parseObject.get("fulfillment_date");
        if (bookingDate != null) {
            bookingDealModel.setBooking_date(DateFormatUtil.getDateInString(bookingDate));
        }
        if (fulfillmentDate != null) {
            bookingDealModel.setFulfillment_date(DateFormatUtil.getDateInString(fulfillmentDate));
        }
        bookingDealModel.setHas_fulfilled_yn(parseObject.getString("has_fulfilled_yn"));
        ParseObject dealBranchParseObject = parseObject.getParseObject("deal_branch");
        bookingDealModel.setBookingPointer(parseObject);
        BranchDealModel branchDealModel = dealStore.saveBranchDeal(dealBranchParseObject, context);
        bookingDealModel.setBranchDealModel(branchDealModel);
        return  bookingDealModel;
    }

    public static ParseObject setBookingModel(ParseObject parseObject, Context context){
        BookingDealModel bookingDealModel = new BookingDealModel();
        bookingDealModel.setId(parseObject.getObjectId());
        try {
            int peopleCount = (int) parseObject.getNumber("no_of_people");
            bookingDealModel.setNo_of_people(String.valueOf(peopleCount));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Date bookingDate = (Date) parseObject.get("booking_date");
        Date fulfillmentDate = (Date) parseObject.get("fulfillment_date");
        if (bookingDate != null) {
            bookingDealModel.setBooking_date(DateFormatUtil.getDateInString(bookingDate));
        }
        if (fulfillmentDate != null) {
            bookingDealModel.setFulfillment_date(DateFormatUtil.getDateInString(fulfillmentDate));
        }
        bookingDealModel.setHas_fulfilled_yn(parseObject.getString("has_fulfilled_yn"));
        ParseObject dealBranchParseObject = parseObject.getParseObject("deal_branch");
        bookingDealModel.setBookingPointer(parseObject);
        BranchDealModel branchDealModel = dealStore.saveBranchDeal(dealBranchParseObject, context);
        bookingDealModel.setBranchDealModel(branchDealModel);
        return  parseObject;
    }

}

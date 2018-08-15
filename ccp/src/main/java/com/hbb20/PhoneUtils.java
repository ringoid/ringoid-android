package com.hbb20;

import android.content.Context;
import android.text.TextUtils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;


public class PhoneUtils {

    public static String getPhone(Context context, String data) {
        if (TextUtils.isEmpty(data)) return null;
        Phonenumber.PhoneNumber number = getNumber(context, data);
        return number == null ? data.replaceAll("[^0-9]", "") : String.valueOf(number.getNationalNumber());
    }

    public static String getCode(Context context, String data) {
        Phonenumber.PhoneNumber number = getNumber(context, data);
        return number == null ? null : String.valueOf(number.getCountryCode());
    }

    private static Phonenumber.PhoneNumber getNumber(Context context, String data) {
        Phonenumber.PhoneNumber number;
        try {
            number = PhoneNumberUtil.getInstance().parse(data, null);
        } catch (NumberParseException e) {
            return null;
        }
        return number;
    }

}

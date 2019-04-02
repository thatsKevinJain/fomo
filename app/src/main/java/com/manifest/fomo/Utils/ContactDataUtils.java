package com.manifest.fomo.Utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.manifest.fomo.DetailedTypes.DetailedInfo;

import java.util.ArrayList;

public class ContactDataUtils {

    public static ArrayList<DetailedInfo> fetchContactDetails(Context context) {

        String[] CONTACTS_SUMMARY_PROJECTION = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.TIMES_CONTACTED,
                ContactsContract.Contacts.PHOTO_ID
        };

        ArrayList<DetailedInfo> appUsageInfos = new ArrayList<>();
        try (Cursor c = context.getContentResolver()
                .query(ContactsContract.Contacts.CONTENT_URI, CONTACTS_SUMMARY_PROJECTION,
                        ContactsContract.Contacts.TIMES_CONTACTED + " > 0",
                        null, ContactsContract.Contacts.TIMES_CONTACTED + " DESC")) {
            int nameID = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            int durationID = c.getColumnIndex(ContactsContract.Contacts.TIMES_CONTACTED);
            int phoneNumberID = c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
            while (c.moveToNext()) {
                appUsageInfos.add(new DetailedInfo(c.getString(nameID), c.getInt(durationID)));
            }
            c.close();
        }
        return appUsageInfos;
    }

    public static long fetchTotalTimesContacted(Context context) {

        String[] CONTACTS_SUMMARY_PROJECTION = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.TIMES_CONTACTED,
                ContactsContract.Contacts.PHOTO_ID
        };

        long timesContacted = 0;
        try (Cursor c = context.getContentResolver()
                .query(ContactsContract.Contacts.CONTENT_URI, CONTACTS_SUMMARY_PROJECTION,
                        ContactsContract.Contacts.TIMES_CONTACTED + " > 0",
                        null, ContactsContract.Contacts.TIMES_CONTACTED + " DESC")) {
            int nameID = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            int durationID = c.getColumnIndex(ContactsContract.Contacts.TIMES_CONTACTED);
            int phoneNumberID = c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
            while (c.moveToNext()) {
                timesContacted += c.getInt(durationID);
            }
            c.close();
        }
        return timesContacted;
    }
}

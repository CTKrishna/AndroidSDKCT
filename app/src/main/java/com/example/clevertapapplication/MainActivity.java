package com.example.clevertapapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;
import com.clevertap.android.sdk.CTInboxListener;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import com.clevertap.android.sdk.interfaces.NotificationHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements CTInboxListener, DisplayUnitListener {
    public Button btn , bt , b ,butto;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CleverTapAPI.createNotificationChannel(getApplicationContext(),"K01","Push","MESSI MESSI", NotificationManager.IMPORTANCE_MAX,true);

// Creating a Notification Channel With Sound Support

    //Push templates
        CleverTapAPI.setNotificationHandler((NotificationHandler)new PushTemplateNotificationHandler());
// How to add a sound file to your app : https://developer.clevertap.com/docs/add-a-sound-file-to-your-android-app

        super.onCreate(savedInstanceState);
        CleverTapAPI.getDefaultInstance(this).setDisplayUnitListener(this);
        setContentView(R.layout.activity_main);
        CleverTapAPI clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE);
        //App Inbox
        if (clevertapDefaultInstance != null) {
            //Set the Notification Inbox Listener
            clevertapDefaultInstance.setCTNotificationInboxListener(this);
            //Initialize the inbox and wait for callbacks on overridden methods
            clevertapDefaultInstance.initializeInbox();
        }
        clevertapDefaultInstance.pushEvent("product viewed");

        btn = findViewById(R.id.btn1);
        bt=findViewById(R.id.btn2);
        b=findViewById(R.id.btn3);
        butto=findViewById(R.id.btn4);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
                profileUpdate.put("Name", "Krishna Bansal");                  // String
                profileUpdate.put("Identity", 100);                    // String or number
                profileUpdate.put("Email", "clevertap@gmail.com");               // Email address of the user
                profileUpdate.put("Phone", "+14155551234");                 // Phone (with the country code, starting with +)
                profileUpdate.put("Gender", "M");                           // Can be either M or F
                profileUpdate.put("DOB", new Date());                       // Date of Birth. Set the Date object to the appropriate value first

                profileUpdate.put("Photo", "www.foobar.com/image.jpeg");    // URL to the Image

// optional fields. controls whether the user will be sent email, push etc.
                profileUpdate.put("MSG-email", false);                      // Disable email notifications
                profileUpdate.put("MSG-push", true);                        // Enable push notifications
                profileUpdate.put("MSG-sms", false);                        // Disable SMS notifications
                profileUpdate.put("MSG-dndPhone", true);                  // Opt out phone                                                                    number from SMS                                                                  notifications
                profileUpdate.put("MSG-dndEmail", true);                  // Opt out phone                                                                    number from SMS                                                                  notifications
                ArrayList<String> stuff = new ArrayList<String>();
                stuff.add("bag");
                stuff.add("shoes");
                profileUpdate.put("MyStuff", stuff);                        //ArrayList of Strings

                String[] otherStuff = {"Jeans","Perfume"};
                profileUpdate.put("MyStuff", otherStuff);                   //String Array

                clevertapDefaultInstance.pushProfile(profileUpdate);
            }

        });
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // event with properties
                HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
                prodViewedAction.put("Product Name", "Casio Chronograph Watch");
                prodViewedAction.put("Category", "Mens Accessories");
                prodViewedAction.put("Price", 59.99);
                prodViewedAction.put("Date", new java.util.Date());

                clevertapDefaultInstance.pushEvent("Product viewed", prodViewedAction);

/**
 * Data types
 * The value of a property can be of type Date (java.util.Date), an Integer, a Long, a Double,
 * a Float, a Character, a String, or a Boolean.
 *
 * Date object
 * When a property value is of type Date, the date and time are both recorded to the second.
 * This can be later used for targeting scenarios.
 * For e.g. if you are recording the time of the flight as an event property,
 * you can send a message to the user just before their flight takes off.
 */
            }
        });
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> chargeDetails = new HashMap<String, Object>();
                chargeDetails.put("Amount", 300);
                chargeDetails.put("Payment Mode", "Credit card");
                chargeDetails.put("Charged ID", 24052013);

                HashMap<String, Object> item1 = new HashMap<String, Object>();
                item1.put("Product category", "books");
                item1.put("Book name", "The Millionaire next door");
                item1.put("Quantity", 1);

                HashMap<String, Object> item2 = new HashMap<String, Object>();
                item2.put("Product category", "books");
                item2.put("Book name", "Achieving inner zen");
                item2.put("Quantity", 1);

                HashMap<String, Object> item3 = new HashMap<String, Object>();
                item3.put("Product category", "books");
                item3.put("Book name", "Chuck it, let's do it");
                item3.put("Quantity", 5);

                ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
                items.add(item1);
                items.add(item2);
                items.add(item3);

                clevertapDefaultInstance.pushChargedEvent(chargeDetails, items);

            }
        });
        butto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clevertapDefaultInstance.showAppInbox();
            }
        });



    }

    @Override
    public void inboxDidInitialize() {


    }
    @Override
    public void inboxMessagesDidUpdate() {

    }
    //Native Display
    @Override
    public void onDisplayUnitsLoaded(ArrayList<CleverTapDisplayUnit> units) {
        CleverTapAPI.getDefaultInstance(this).pushEvent("Welcome to the Application");

        // you will get display units here
        // implement your logic to create your display views using these Display Units here
        for (int i = 0; i <units.size() ; i++) {
            CleverTapDisplayUnit unit = units.get(i);
            prepareDisplayView(unit);
            Log.d("CleverTap","Native dispay printed"+units);
         //   CleverTapAPI.getDefaultInstance(this).pushDisplayUnitViewedEventForID(unit.getUnitID());

        }//CleverTapAPI.getDefaultInstance(this).getAllDisplayUnits();
    }

    private void prepareDisplayView(CleverTapDisplayUnit unit) {
        //TODO: Set display

    }
}
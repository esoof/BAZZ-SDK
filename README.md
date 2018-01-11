# BAZZ-SDK
SDK for 3rd party developers to leverage the features of BAZZ.

<!-- toc -->

* [Prerequisites](#prerequisites)
* [Installation](#installation)
* [Release notes](#release-notes)
* [Usage](#usage)
  * [Preparing your app](#preparing-your-app)
    * [Adding classes](#adding-classes)
    * [Gradle](#gradle)
    * [Manifest](#manifest)
  * [Initializing the SDK](#initializing)
  * [BAZZ operation modes](#bazz-operation-modes)
  * [Driving detection](#driving-detection)
  * [Incoming messages](#incoming-messages)
  * [Configuring operation](#configuring-operation)
    * [Messages queue](#messages-queue)
    * [Controlling flow](#controlling-flow)
    * [Controlling playback](#controlling-playback)
    * [Internal setting screens](#internal-setting-screens)
    * [More settings](#more-settings)
  * [Handle user interaction yourself](#handle-user-interaction-yourself)
    * [Take over treatment of messages](#take-over-treatment-of-messages)
    * [Message life cycle](#message-life-cycle)
    * [Play prompts using TTS](#play-prompts-using-tts)
    * [Play prompts from resources](#play-prompts-from-resources)
    * [Play prompts from SD](#play-prompts-from-sd)
    * [Ask for user commands](#ask-for-user-commands)
    * [Reply to sender with SMS](#reply-to-sender-with-sms)
    * [Callback to sender](#callback-to-sender)
    * [Record a voice reply](#record-a-voice-reply)
    * [Upload voice reply to server](#upload-voice-reply-to-server)
  * [User analytics](#user-analytics)
    * [New user registration](#new-user-registration)
    * [Event callback](#event-callback)

<!-- toc stop -->

# Prerequisites

* BAZZ SDK works on **Android** devices with OS version **4.0.1** and up.
* BAZZ SDK requires a connected android device (i.e. **data** or **WiFi** connection) to work.
* You will need a developer account with BAZZ. Please [Contact Us](mailto:support@bazz.co) for details.

# Installation

Clone or download BAZZ-SDK from here, and extract it on your computer.
In it you will find a demo application for how the BAZZ SDK can be used.

You can import this project to Android Studio, and use it as a reference
or base to make your alterations.

If you already have an existing project, or want to create a new one,
The BAZZ SDK comes in the form of **bazz_sdk.aar**. You should receive it from us, along with
the App ID.

To add it to your project, follow the steps below:

1. With your project open in Android Studio, select '**File => New => New Module**'
2. In the dialog opened, select '**Import .JAR/.AAR Package**', and click '**Next**'
3. In the '**File name**' line - browse to the '**bazz_sdk**' folder of the demo project,
   and select the **aar** file. Click on '**Finish**'
4. After Gradle Sync is done, go to your project settings: right-click on the project module, and select '**Open Module Settings**'.
5. Select your **app** module in the left pane, and click the '**Dependencies**' tab. Click the **plus (+)** sign, and select '**Module dependency**'. Select '**:bazz_sdk**', and click '**OK**' twice.

Done. Now you can start integrating the BAZZ SDK as explained in the next sections.

# Release notes

## What's new in version 1.04.27

* ```init ``` function: Initialization process is asynchronous, so added a variation of the 'init' function to support async operation - see 'Initializing' section.

* ```handlePermissions ``` function: Since we added the option for your application to instruct the SDK not to handle permissions, thgis function is added to allow you to query state of permissions.

* ```setInternalUIOptions ``` function: Added a function to allow your application to control the display of various UI elements (e.g. the MIC popup while recording user voice).

* ```getBluetoothDevicesList & setBluetoothDevice ``` functions: Added to allow your app to handle Bluetooth settings of the SDK (in stead of relying on the SDK internal UI).

* ```getWhatsAppGroupsInfoList & setWhatsAppGroupInfo ``` functions: Added to allow your app to handle WhatsApp groups blocking settings of the SDK (in stead of relying on the SDK internal UI).

* ```getTTSlanguagesList, get/setEnglishTTSLanguage, get/setOtherTTSLanguage, getTTSLangDisplayName  ``` functions: Added to allow your app to handle TTS settings of the SDK (in stead of relying on the SDK internal UI).



# Usage

## Preparing your app

**IMPORTANT:** set your app '**minSdkVersion**' to **16**, and '**targetSdkVersion**' to **19**

### Adding classes

- Add a new java file: '**BazzNotificationServiceLink.java**':

```java
package <your app project package>

import com.esoof.android.bazz_sdk.BroadcastReceiver_Notifications;

public class BazzNotificationServiceLink extends BroadcastReceiver_Notifications {
}
```

- Add a new file: '**MyApplication.java**':

```java
package <your app project package>

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
    }
}
```

- Do not forget to add the reference to it in your Android Manifest under the '**Application**' header:

```xml
    <Application
        ...
        android:name=".MyApplication"
        />
```

**Note:** if you already have an '**Application**' class in your app - just add the code explained [below](#initializing)


### Gradle

- In your app **build.gradle**, make sure you have **minSdkVersion 16** and **targetSdkVersion 19**.

- Under **dependencies** add ``` compile 'com.google.android.gms:play-services:7.8.0' ```

- Under **dependencies** add ``` compile 'com.android.volley:volley:1.0.0' ```


### Manifest

- Yes, we know - we're nagging... but this is important - make sure you have these setup:

```xml
    <!-- THIS IS VERY IMPORTANT - make sure your build.gradle also defines these values -->
    <uses-sdk
        android:minSdkVersion="16"
        android:targetSdkVersion="19" />
```

- Add the following permissions to your '**AndroidManifest.xml**' file:
(yes we know - there are tons of them, but they are all required for
the operation of BAZZ...)

```xml
    <uses-permission android:name="android.permission.GET_TASKS"/>

    <uses-permission android:name="android.permission.CALL_PHONE" />

    <uses-permission android:name="android.permission.BROADCAST_STICKY" />

    <uses-permission android:name="android.permission.READ_CONTACTS" />
    <uses-permission android:name="android.permission.GET_ACCOUNTS" />

    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />

    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

    <uses-permission android:name="android.permission.READ_PHONE_STATE" />

    <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS" />

    <uses-permission android:name="android.permission.MODIFY_PHONE_STATE" android:required="false" />

    <uses-permission android:name="android.permission.RECEIVE_SMS"/>
    <uses-permission android:name="android.permission.READ_SMS" />
    <uses-permission android:name="android.permission.READ_MMS" />

    <uses-permission android:name="android.permission.WRITE_SMS" />
    <uses-permission android:name="android.permission.SEND_SMS" />
    <uses-permission android:name="android.Manifest.permission.SEND_SMS" />

    <uses-permission android:name="android.permission.RECEIVE_MMS"/>
    <uses-permission android:name="android.permission.RECEIVE_WAP_PUSH"/>


    <uses-permission android:name="android.permission.RECEIVE_HEADSET_PLUG" />
    <uses-permission android:name="android.permission.GET_ACCOUNTS" />
    <uses-permission android:name="android.permission.BLUETOOTH" />

    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />

    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />

    <uses-permission android:name="android.permission.WAKE_LOCK"/>
    <uses-permission android:name="android.permission.DISABLE_KEYGUARD"/>

    <uses-permission android:name="com.google.android.gms.permission.ACTIVITY_RECOGNITION"/>

    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
```

- Also add this inside the '**Application**' tag:

```xml
    <!-- Add this to enable BAZZ to detect driving -->
    <meta-data android:name="com.google.android.gms.version" android:value="@integer/google_play_services_version" />

    <!-- Add this to enable BAZZ to detect driving -->
    <service
        android:name="com.esoof.android.bazz_sdk.ActivityRecognitionIntentService"
        android:label="@string/app_name"
        android:exported="false">
    </service>

    <!-- Add this to enable BAZZ to handle IM messaging apps -->
    <service android:name=".BazzNotificationServiceLink"
        android:label="@string/app_name"
        android:permission="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE"
        android:exported="false">

        <intent-filter>
            <action android:name="android.service.notification.NotificationListenerService" />
        </intent-filter>
    </service>
```

## Initializing

- In your '**Application**' java class, add a global instance variable for BazzLib, and the APP_ID you received from us:

```java
    public static BazzLib mBazzLib = null;
    
    private static final String APP_ID = "<Your BAZZ SDK app id here>";
```

- In the '**onCreate**' function, add the following lines:

(for existing users - as mentioned above, we changed the way the 'init' function works, so use this code)

```java
        mBazzLib = new BazzLib();

        if (mBazzLib!=null)
        {
            boolean bAllowSDKToShowPermissionsUI = false;
        
            // APP_ID is a string you will receive from us when we register your app
            mBazzLib.init(this, APP_ID, bAllowSDKToShowPermissionsUI, new BazzLib.BazzInitDoneListener() {
                @Override
                public void onBazzInitDone(String error, ArrayList<String> missingPermissions)
                {
                    if (error==null)
                    {
                        // You must call these functions, too to initialize the app properly
                        mBazzLib.setAppName("<Your app user-friendly name>");
                        mBazzLib.setMainActivity("<Class name of your main activity (e.g. MainActivity)>");

                        // This call allows you to control what elements of UI are hidden
                        mBazzLib.setInternalUIOptions(
                                bShowMicPopup,
                                bShowStartedDrivingPopup,
                                bShowStateInNotifications);

                        // These calls enable treatment of the IM messaging apps
                        mBazzLib.setIncomingWorkWithWhatsapp(true);
                        mBazzLib.setIncomingWorkWithMessenger(true);
                        mBazzLib.setIncomingWorkWithLine(true);
                        mBazzLib.setIncomingWorkWithGmail(true);
                        mBazzLib.setIncomingWorkWithLine(true);

                        // *** You must add this to enable access to IM messaging apps ***
                        bindService(new Intent(getApplicationContext(), BazzNotificationServiceLink.class), new ServiceConnection() {
                            @Override
                            public void onServiceConnected(ComponentName className, IBinder service) {
                            }
                            @Override
                            public void onServiceDisconnected(ComponentName arg0) {
                            }
                        }, Context.BIND_AUTO_CREATE);
                    }
                }
            });					            
        }
```

Parameters:

- **bAllowSDKToShowPermissionsUI:** if 'true' then SDK will display all permission UI, if 'false' - your application is responsible to get permissions.

The other new paramater to the 'init' function is a listener, where the SDK will notify the app of completion and results of the SDK initialization:

```java
        new BazzLib.BazzInitDoneListener() {
            @Override
                public void onBazzInitDone(String error, ArrayList<String> missingPermissions)
                {
                }
            }
```

In the callback you will get:

- **error:** null if all ok, or an error message if an error occured
- **missingPermissions:** an array of missing permissions



**Note:** For the sake of backward compatibility, the old way to initialize still works, but it is async, and defaults to SDK displaying the permissions UI.

```java
        mBazzLib = new BazzLib();

        if (mBazzLib!=null)
        {
            // APP_ID is a string you will receive from us when we register your app
            mBazzLib.init(this,APP_ID);
            
            // You must call these functions, too to initialize the app properly
            mBazzLib.setAppName("<Your app user-friendly name>");
            mBazzLib.setMainActivity("<Class name of your main activity (e.g. MainActivity)>");

            // These calls enable treatment of the IM messaging apps
            mBazzLib.setIncomingWorkWithWhatsapp(true);
            mBazzLib.setIncomingWorkWithMessenger(true);
            mBazzLib.setIncomingWorkWithLine(true);
            mBazzLib.setIncomingWorkWithGmail(true);
            mBazzLib.setIncomingWorkWithLine(true);

            // *** You must add this to enable access to IM messaging apps ***
            bindService(new Intent(getApplicationContext(), BazzNotificationServiceLink.class), new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName className, IBinder service) {
                }
                @Override
                public void onServiceDisconnected(ComponentName arg0) {
                }
            }, Context.BIND_AUTO_CREATE);
        }
```

- In the '**onTerminate**' function, add the following lines:

```java
        if (mBazzLib!=null)
        {
            mBazzLib.shutdown(this);
            mBazzLib = null;
        }
```

- **Important:** if you need your app to work with your own server - set the server URL by calling the following function **before** you call 'mBazzLib.init...':

```java
        mBazzLib = new BazzLib();

        if (mBazzLib!=null)
        {
            // Set a custom server URL:
            mBazzLib.setServerURL("<your server URL>");
        
            // APP_ID is a string you will receive from us when we register your app
            mBazzLib.init(this,APP_ID);
            ...
```            




## BAZZ operation modes

BAZZ has 3 operation modes:

* **Off**: BAZZ is idle
* **Always**: BAZZ is ON, and monitoring incoming messages, to treat them
* **When driving**: BAZZ is monitoring driving conditions (using 'sensors' like GPS, Bluetooth, Wired headset, connection to charger), and will handle incoming messages only when in a drive

By default BAZZ is in '**Always**' mode (you can see a green indicator in the device top status line).
To change modes use the following calls:

```java
    if (MyApplication.mBazzLib!=null)
    {
        MyApplication.mBazzLib.setBazzMode(BazzLib.BAZZ_MODE_OFF);
        
        MyApplication.mBazzLib.setBazzMode(BazzLib.BAZZ_MODE_ALWAYS_ON);

        MyApplication.mBazzLib.setBazzMode(BazzLib.BAZZ_MODE_ON_WHILE_DRIVING);
    }
```


To read the current mode call:

```java
    if (MyApplication.mBazzLib!=null)
    {
        int currMode = MyApplication.mBazzLib.getBazzMode();
    }
```


To check if BAZZ is '**active**' (i.e. treating messages):

```java
    if (MyApplication.mBazzLib!=null)
    {
        boolean isBazzActive = MyApplication.mBazzLib.getBazzActive();
    }
```


You can also listen to changes in BAZZ mode (e.g. when BAZZ turns ON cause it detected driving state):

```java
    MyApplication.mBazzLib.setOnBazzModeChangedListener(new BazzLib.BazzModeChangedListener() {
        @Override
        public void onBazzModeChanged() {
            // MYTODO: do somthing here cause BAZZ mode has changed...
        }
    });
```


As mentioned above, the BAZZ SDK indicates it's current state in the system notifications area (aka 'status line' - the top line on the screen) - you will see an **orange** icon when BAZZ is in '**When driving**' mode and waiting for a drive, or a **green** icon when BAZZ is either in '**Always**' mode or in "**When driving**' and in a drive. If you want to turn this feature off or on, use:

```java
    if (MyApplication.mBazzLib!=null)
    {
        boolean isIconInNotificationsAreaOn = MyApplication.mBazzLib.getShowStateInNotificationsArea();
        
        MyApplication.mBazzLib.setShowStateInNotificationsArea(true/false);
    }
```



## Driving detection

BAZZ uses various 'sensors' to detect driving conditions (decide if the device is now in a vehicle driving, or when it stopped). 

Use the following code to allow your app to monitor these events"

```java
    if (MyApplication.mBazzLib!=null)
    {
            //--- BAZZ driving detection events ----------------------------

            MyApplication.mBazzLib.setOnDrivingEventListener(new BazzLib.BazzDrivingEventsListener() {
                @Override
                public void onDrivingSensorEvent(String source, boolean newState) {

                    HandleCallbackFromLib("Driving sensor '"+source+"': "+(newState?"On":"Off"));
                }

                @Override
                public void onDrivingStarted() {
                    HandleCallbackFromLib("Driving started");
                }

                @Override
                public int onPlayDriveStartedPrompt() {
                    HandleCallbackFromLib("Play 'driving started' prompt");
                    return 0;
                }

                @Override
                public void onDrivingEnded() {
                    HandleCallbackFromLib("Driving ended");
                }

                @Override
                public int onPlayDriveEndedPrompt() {
                    HandleCallbackFromLib("Play 'driving ended' prompt");
                    return 0;
                }
            });
    }
```

**Note:** when it detects '**drive started**' or '**drive ended**' events, BAZZ plays default sounds prompts. You can change those, and assign your own resource ID by returning it from inside '**onPlayDriveStartedPrompt**' and/or '**onPlayDriveEndedPrompt**' callbacks.



BAZZ uses the following '**sensors**' to decide on drive/stop: 
- a mix of sensors and algorytms to device on driving based on speed, accelarations, etc.
- detection of Bluetooth connection with Bluetooth devices (e.g. Car multimedia system).
- user plugging in a wired headset.
- user pluggin the phone into a charger/docking station.

You can can enable/disable these using:

```java
    if (MyApplication.mBazzLib!=null)
    {
        MyApplication.mBazzLib.setDriveDetectByDriving(true/false);
        
        MyApplication.mBazzLib.setDriveDetectByBluetooth(true/false);
        
        MyApplication.mBazzLib.setDriveDetectByWiredHeadset(true/false);
        
        MyApplication.mBazzLib.setDriveDetectByCharger(true/false);
    }
```

You can can query current enable/disable state using:

```java
    if (MyApplication.mBazzLib!=null)
    {
        boolean bOn;
    
        bOn = MyApplication.mBazzLib.getDriveDetectByDriving();
        
        bOn = MyApplication.mBazzLib.getDriveDetectByBluetooth();
        
        bOn = MyApplication.mBazzLib.getDriveDetectByWiredHeadset();
        
        bOn = MyApplication.mBazzLib.getDriveDetectByCharger();
    }
```

## Bluetooth devices ignore list

User may not want ALL his/her Bluetooth devices to activate BAZZ (e.g. connecting your phone to your smart watch does not mean you're driving...). Use this code to popup the internal '**Bluetooth devices**' setting screen:

```java
    if (MyApplication.mBazzLib!=null)
    {
        MyApplication.mBazzLib.popupBluetoothIgnoreSettingsUI();
    }
```

## Incoming Messages

BAZZ is all about treating messages. It can intercept incoming messages from various sources, such as SMS/TEXT, Whatsapp, etc. and allow the user to hear & respond to them using voice only.

You can can enable/disable Messaging apps using:

```java
    if (MyApplication.mBazzLib!=null)
    {
        MyApplication.mBazzLib.setIncomingWorkWithSMSorMMS(true/false);
        
        MyApplication.mBazzLib.setIncomingWorkWithWhatsapp(true/false);
        
        MyApplication.mBazzLib.setIncomingWorkWithMessenger(true/false);
        
        MyApplication.mBazzLib.setIncomingWorkWithLine(true/false);
        
        MyApplication.mBazzLib.setIncomingWorkWithGmail(true/false);
        
        MyApplication.mBazzLib.setIncomingWorkWithWeChat(true/false);
    }
```

You can can query current enable/disable state using:

```java
    if (MyApplication.mBazzLib!=null)
    {
        boolean bOn;
    
        bOn = MyApplication.mBazzLib.getIncomingWorkWithSMSorMMS();
        
        bOn = MyApplication.mBazzLib.getIncomingWorkWithWhatsapp();
        
        bOn = MyApplication.mBazzLib.getIncomingWorkWithMessenger();
        
        bOn = MyApplication.mBazzLib.getIncomingWorkWithLine();
        
        bOn = MyApplication.mBazzLib.getIncomingWorkWithGmail();
        
        bOn = MyApplication.mBazzLib.getIncomingWorkWithWeChat();
    }
```

To be notified of incoming messages in your app, use:

```java
    MyApplication.mBazzLib.setOnIncomingMessagesListener(new BazzLib.BazzIncomingMessagesListener() {
        @Override
        public boolean onIncomingMessagesListener(String type,
                                                  String phone,
                                                  String name,
                                                  String text)
        {
            // handle the messages here...
        
            return false;
        }
    });
```

The parametrs here are:

- type: the type of the incoming message. possible values are:
```java
    BazzLib.MSG_TYPE_SMS
    BazzLib.MSG_TYPE_MMS
    BazzLib.MSG_TYPE_WHATSAPP_CONTACT
    BazzLib.MSG_TYPE_WHATSAPP_GROUP_PLAY_NAME_ONLY
    BazzLib.MSG_TYPE_WHATSAPP_GROUP_PLAY_CONTENT
    BazzLib.MSG_TYPE_WECHAT_CONTACT
    BazzLib.MSG_TYPE_WECHAT_GROUP_PLAY_NAME_ONLY
    BazzLib.MSG_TYPE_WECHAT_GROUP_PLAY_CONTENT
    BazzLib.MSG_TYPE_INCOMING_CALL
    BazzLib.MSG_TYPE_FACEBOOK
    BazzLib.MSG_TYPE_LINE
    BazzLib.MSG_TYPE_GMAIL
```
- phone: phone number of sender (if obtainable)
- name: name of sender
- text: body of message

**Note:** you can stop BAZZ form handling the message by returning **true** from the callback function.

## Configuring operation

### Messages queue

When BAZZ detects an incoming message, it adds it to a queue. During normal operation, messages are pulled from this queue, and treated (or sent to your app for treatment) on a FIFO order.

On some conditions (such as when user is on a phone call, or the phone is MUTE), BAZZ pauses polling the queue for messages, to not disturb the user.

If you need to, you can force BAZZ to pause treatment of messages from the queue if you need to (new messages will still be added to the queue).

To do this:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.pauseQueueTreatment();
        // - or -
        MyApplication.mBazzLib.resumeQueueTreatment();
    }
```

You can get the current state:

```java
    if (MyApplication.mBazzLib != null)
    {
        boolean queueTreatmentIsPaused = MyApplication.mBazzLib.isQueueTreatmentPaused();
    }
```

Question - what if your app calls these pause/resume methods in high frequency, thus creates an erratic behaviour to the user? You can define a timeout filter for the resume method. I.E. if you called 'pause', then calls to 'resume' will only take affect X seconds after the last 'pause'.

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setPauseQueueTimeout(int timeoutInSecs);
    }
```


There may be a scenario where such a condition arises when BAZZ is already treating a message or even just playing a message to the user, and your app will want to force BAZZ to stop distracting the user at once.

To do this:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.stopUserInteractionAtOnce();
    }
```



### Controlling flow

Normally, when BAZZ gets an incoming message, it treats it like this:

First it pops up a screen as an indication to the driver (and to allow the driver to stop message handling at all time by a touch anywhere (note - this is a "panic button", user does **NOT** have to touch the screen to do this, he/she can command BAZZ to stop using voice commands, too).

This full screen popup will obscure all screen, hence interfere with the driver using the phone for navigation or such.

So BAZZ also can display only a small floating bubble to allow the driver to still see and use the map.

You can configure BAZZ to **never** or **always** use this floating bubble, or use it only when navigating:

```java
        MyApplication.mBazzLib.setUseFloatingBubble(BazzLib.FLOATING_BUBBLE_NEVER);

        MyApplication.mBazzLib.setUseFloatingBubble(BazzLib.FLOATING_BUBBLE_ALWAYS);

        MyApplication.mBazzLib.setUseFloatingBubble(BazzLib.FLOATING_BUBBLE_IF_NAVIGATING);
        
        nSel = MyApplication.mBazzLib.getUseFloatingBubble();
```

Then BAZZ plays a voice prompt "new message from..." and the sender name or phone. Next step can be configured by your app:
- BAZZ prompts the user to say '**stop**' or '**continue**', then listens to the user's command (**default** setting).
- BAZZ can automatically continue to reading the message content.
- BAZZ can stop here.

To configure this:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setVoiceCommandsAfterSenderDoWhat(BazzLib.VOICE_COMMANDS_AFTER_SENDER_WAIT_COMMAND);
        // - or -
        MyApplication.mBazzLib.setVoiceCommandsAfterSenderDoWhat(BazzLib.VOICE_COMMANDS_AFTER_SENDER_READ_CONTENT);
        // - or -
        MyApplication.mBazzLib.setVoiceCommandsAfterSenderDoWhat(BazzLib.VOICE_COMMANDS_AFTER_SENDER_DO_NOTHING);
    }
```

You can get the current setting:

```java
    if (MyApplication.mBazzLib != null)
    {
        int current = MyApplication.mBazzLib.getVoiceCommandsAfterSenderDoWhat();
    }
```

Now BAZZ plays the message content. Next step can be configured by your app:
- BAZZ prompts the user to select the reply method by voice command ('**stop**', '**callback**', '**text**', '**record**', '**repeat**'), then listens to the user's command (**default** setting).
- BAZZ can automatically send a pre-configured text message.
- BAZZ can stop here.

To configure this:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setVoiceCommandsAfterContentDoWhat(BazzLib.VOICE_COMMANDS_AFTER_CONTENT_WAIT_COMMAND);
        // - or -
        MyApplication.mBazzLib.setVoiceCommandsAfterContentDoWhat(BazzLib.VOICE_COMMANDS_AFTER_CONTENT_SEND_TEXT);
        // - or -
        MyApplication.mBazzLib.setVoiceCommandsAfterContentDoWhat(BazzLib.VOICE_COMMANDS_AFTER_CONTENT_DO_NOTHING);
    }
```

You can get the current setting:

```java
    if (MyApplication.mBazzLib != null)
    {
        int current = MyApplication.mBazzLib.getVoiceCommandsAfterContentDoWhat();
    }
```

### Special case: Whatsapp group messages

For Whatsapp **group** messages, there is an additional configuration you can make:
- do not play them at all
- play the "a message from group <name> (and only once per drive)
- treat it like a 'normal' message

To configure this:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setWhatsappGroupTreatmentMode(BazzLib.WHATSAPP_GROUP_TREATMENT_DO_NOT_PLAY);
        // - or -
        MyApplication.mBazzLib.setWhatsappGroupTreatmentMode(BazzLib.WHATSAPP_GROUP_TREATMENT_PLAY_NAME_ONLY);
        // - or -
        MyApplication.mBazzLib.setWhatsappGroupTreatmentMode(BazzLib.WHATSAPP_GROUP_TREATMENT_PLAY_ALL);
    }
```

You can get the current setting:

```java
    if (MyApplication.mBazzLib != null)
    {
        int current = MyApplication.mBazzLib.getWhatsappGroupTreatmentMode();
    }
```

### Whatsapp groups ignore list

User may not want BAZZ to read ALL his/her Whatsapp groups (some of them can be really noisey...) Use this code to popup the internal '**Whatsapp groups**' setting screen:

```java
    if (MyApplication.mBazzLib!=null)
    {
        MyApplication.mBazzLib.popupWhatsappGroupsIgnoreSettingsUI();
    }
```



###Incoming calls

You can also control treatment of incoming **calls**:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setIncomingHandleIncomingCalls(true/false);
    }
```

You can get the current setting:

```java
    if (MyApplication.mBazzLib != null)
    {
        boolean bOn = MyApplication.mBazzLib.getIncomingHandleIncomingCalls();
    }
```

###Sound notifications from other apps

You can mute/unmute sound of notifications from other apps:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setIncomingMuteOthers(true/false);
    }
```

You can get the current setting:

```java
    if (MyApplication.mBazzLib != null)
    {
        boolean bOn = MyApplication.mBazzLib.getIncomingMuteOthers();
    }
```

###Blocking unknown senders

And finally - you can ignore messages from unknown senders:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setIncomingReadBlockedOrUnknownSenders(true/false);
    }
```

You can get the current setting:

```java
    if (MyApplication.mBazzLib != null)
    {
        boolean bOn = MyApplication.mBazzLib.getIncomingReadBlockedOrUnknownSenders();
    }
```

###Setting predefined TEXT reply

You can change the pre-defined TEXT:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setTextReply("Bla bla bla...");
    }
```

You can get the current setting:

```java
    if (MyApplication.mBazzLib != null)
    {
        String currStr = MyApplication.mBazzLib.getTextReply();
    }
```


### Controlling playback

**Playback volume**
You can set the playback volume (0-15):

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setPlaybackVolume(12);
    }
```

You can get the current setting:

```java
    if (MyApplication.mBazzLib != null)
    {
        int currVolume = MyApplication.mBazzLib.getPlaybackVolume();
    }
```

**Playback Headset volume**
You can set the playback volume for a connected **wired headset** (0-15):

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setHeadsetVolume(8);
    }
```

You can get the current setting:

```java
    if (MyApplication.mBazzLib != null)
    {
        int currVolume = MyApplication.mBazzLib.getHeadsetVolume();
    }
```

**Playback speed (for Hebrew)**

If you're handling **Hebrew** text messages, you can set the speed of message content playback (1-5):

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setPlaybackSpeed(3);
    }
```

You can get the current setting:

```java
    if (MyApplication.mBazzLib != null)
    {
        int currSpeed = MyApplication.mBazzLib.getPlaybackSpeed();
    }
```

### Internal setting screens

**Bluetooth playback devices configuration**

Due to the complexity of Bluetooth interface standards, user may have to configure the type of Bluetooth device he/she has manually. To open the (internal) '**Bluetooth devices**' setting screen:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.popupBluetoothDeviceSettingsUI();
    }
```


**Text-To-Speech**

You can configure BAZZ to handle multiple languages for text content readout. To open the setting screen:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.popupTextToSpeechSettingsUI();
    }
```


**System settings required - enabling 'notification access' to your app**

To allow BAZZ to read IM messaging apps input, the user must manuall enable your app in the **system** '**notifications**' settings screen. To open it:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.popupNotificationAccessUI();
    }
```

To check if this is needed:

```java
    if (MyApplication.mBazzLib != null)
    {
        boolean bAppEnabledInSystemNotifications = MyApplication.mBazzLib.getIsNotificationAccessEnabled();
    }
```


### More settings

You can set driver name / phone / gender:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setDriverName("<name>");
        MyApplication.mBazzLib.setDriverPhone("<phone>");
        MyApplication.mBazzLib.setDriverGenderIsFemale(true/false);
    }
```

You can get the current settings:

```java
    if (MyApplication.mBazzLib != null)
    {
        String  name      = MyApplication.mBazzLib.getDriverName();
        String  phone     = MyApplication.mBazzLib.getDriverPhone();
        boolean bIsFemale = MyApplication.mBazzLib.getDriverGenderIsFemale();
    }
```




## Handle user interaction yourself


### Take over treatment of messages

When BAZZ pulls a message from the queue for the user, it first calls the following callback. You can use it to take control of the message, and handle it yourself in your app (in stead of letting the SDK handle it):

```java
    MyApplication.mBazzLib.setOnNewMessageFromQueueListener(new BazzLib.BazzNewMessageFromQueueListener() {
        @Override
        public int  onNewMessageFromQueueListener(String type,
                                                  String phone,
                                                  String name,
                                                  String text,
                                                  String audioPartId,
                                                  long   msgId)
        {
            // handle the messages here...
        
        	// this stops treatment of message, and erases it from the queue (ignore message)
            return BazzLib.NEW_MESSAGE_FROM_QUEUE_RESULT_DONT_HANDLE_AND_ERASE;
            
            - or - 
        
        	// this stops treatment of message, but keeps it in the queue. It will be treated again after a timeout
            return BazzLib.NEW_MESSAGE_FROM_QUEUE_RESULT_DONT_HANDLE_AND_KEEP;
            
            - or - 
        
        	// instructs BAZZ SDK to handle this message (show UI, play sender name, ask for commands etc.) (default value)
            return BazzLib.NEW_MESSAGE_FROM_QUEUE_RESULT_HANDLED_IN_SDK;
            
            - or - 
        
        	// tells BAZZ SDK to pause treatments, and lets your app handle the message.
        	// *** Call 'requestPrepareForMessageTreatment' to prepare for message treatment ***
        	// *** When app is done - be sure to call 'endMessageTreatment' !!! ***
            return BazzLib.NEW_MESSAGE_FROM_QUEUE_RESULT_HANDLED_IN_APP;
        }
    });
```

### Message life cycle

If you selected to return NEW_MESSAGE_FROM_QUEUE_RESULT_HANDLED_IN_APP, you have control over how a message is handled. You can use the methods to play the sender name, content, and ask the user for voice commands (see in the next section).

In order for the SDK to be ready for your handling of the message, you need to first call a method to prepare the system for your actions (e.g. connect to car bluetooth, take audio focuds, etc.):

```java
    MyApplication.mBazzLib.setOnNewMessageFromQueueListener(new BazzLib.BazzNewMessageFromQueueListener() {
        @Override
        public int  onNewMessageFromQueueListener(String type,
                                                  String phone,
                                                  String name,
                                                  String text,
                                                  String audioPartId,
                                                  long   msgId)
        {
		if (MyApplication.mBazzLib != null)
    		{
    			// first save all the message parameters in your app for future use:
    			msgType        = type;
		        msgName        = name;
        		msgPhone       = phone;
		        msgText        = text;
        		msgAudioPartId = audioPartId;
    		
	        	// Now call 'requestPrepareForMessageTreatment' to prepare for message treatment ***
        		MyApplication.mBazzLib.requestPrepareForMessageTreatment("PrepareForMessageTreatment");
    		}
        
        	// tells BAZZ SDK to pause treatments, and lets your app handle the message.
        	// *** When app is done - be sure to call 'endMessageTreatment' !!! ***
            return BazzLib.NEW_MESSAGE_FROM_QUEUE_RESULT_HANDLED_IN_APP;
        }
    });


```

The 'requestPrepareForMessageTreatment' accepts 1 parameter:

- **requestDescriptor:** this freeform string is used to describe the request, and is returned with the request result, so you can identify the request and the current stage it was requested, to build the 'state machine' of your message treatment (see in the demo app).

Return value:

- **requestId:** the id of the request you asked to send




The 'requestPrepareForMessageTreatment' method, as well as the other methods starting with 'request...' are async methods - they are queued inside the SDK, and treated asynchronically. When the SDK finished handling a request, or when it detects an error, it will return the result to the app using a new callback:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setOnBazzRequestResultListener(new BazzLib.BazzRequestResultListener() {
            @Override
            public boolean onRequestResult(String requestId, String requestDescriptor, String requestResult, String requestError)
            {
            	if (requestError!=null)
            	{
            		// an error occured - handle it, but do not forget to call 'endMessageTreatment' if you want to terminate the message treatment       		
            		// ...
            		
        		MyApplication.mBazzLib.endMessageTreatment(true/false);
            	} else {
            	
            		// request succeeded. continue with 'state machine' according to 'resultDescriptor' (see demo app for an example)	
            		// ...
            	}
            
                return false;
            }
        });
    }
```

In the callback you will get:

- **requestId:** the id of the message you asked to send
- **requestDescriptor:** the descriptor you sent for this text
- **requestResult:** "ok" any other result - depending on the request
- **requestError:** null if all ok, or an error message if an error occured





**Important!** do NOT forget to call the following function at end of message treatment - so that BAZZ SDK will remove it from the queue, and continue to the next message.

```java
    if (MyApplication.mBazzLib != null)
    {
    	// set the paramater here to true if you want to remove the message from the queue.
        MyApplication.mBazzLib.endMessageTreatment(true/false);
    }
```


### Play prompts using TTS

You can play your own text prompts using TTS. To do this:

```java
    if (MyApplication.mBazzLib != null)
    {
        String requestId = MyApplication.mBazzLib.requestPlayText(text,descriptor);
    }
```

Parameters are:

- **text:** the actual text to play to the user
- **descriptor:** a string to represent the 'meaning' of this text (you can use it in your app when you get the callback of playback finished to identify it)

Return value:

is a String holding the ID of the request - you can use it to get the response from:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setOnBazzRequestResultListener(new BazzLib.BazzRequestResultListener() {
            @Override
            public boolean onRequestResult(String requestId, String requestDescriptor, String requestResult, String requestError)
            {
            	//...
            
                return false;
            }
        });
    }
```

In the callback you will get:

- **requestId:** the id of the message you asked to send
- **requestDescriptor:** the descriptor you sent for this text
- **requestResult:** "ok" any other result - depending on the request
- **requestError:** null if all ok, or an error message if an error occured

### Play prompts from resources

You can play a MP3 file from the 'raw' folder in your app 'res'. To do this:

```java
    if (MyApplication.mBazzLib != null)
    {
        String requestId = MyApplication.mBazzLib.requestPlayResource(resID, descriptor);
    }
```

Parameters are:

- **resID:** the resource id of the MP3 file (e.g. R.raw.shoutout)
- **descriptor:** a string to represent the 'meaning' of this voice prompt (you can use it in your app when you get the callback of playback finished to identify it)

Return value:

is a String holding the ID of the request - you can use it to get the response from:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setOnBazzRequestResultListener(new BazzLib.BazzRequestResultListener() {
            @Override
            public boolean onRequestResult(String requestId, String requestDescriptor, String requestResult, String requestError)
            {
            	//...
            
                return false;
            }
        });
    }
```

In the callback you will get:

- **requestId:** the id of the message you asked to send
- **requestDescriptor:** the descriptor you sent for this text
- **requestResult:** "ok" any other result - depending on the request
- **requestError:** null if all ok, or an error message if an error occured

### Play prompts from SD

You can play a MP3 file from your SD card. To do this:

```java
    if (MyApplication.mBazzLib != null)
    {
        String requestId = MyApplication.mBazzLib.requestPlayFromSD(path, descriptor);
    }
```

Parameters are:

- **path:** the path to the the MP3 file on your SD card
- **descriptor:** a string to represent the 'meaning' of this voice prompt (you can use it in your app when you get the callback of playback finished to identify it)

Return value:

is a String holding the ID of the request - you can use it to get the response from:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setOnBazzRequestResultListener(new BazzLib.BazzRequestResultListener() {
            @Override
            public boolean onRequestResult(String requestId, String requestDescriptor, String requestResult, String requestError)
            {
            	//...
            
                return false;
            }
        });
    }
```

In the callback you will get:

- **requestId:** the id of the message you asked to send
- **requestDescriptor:** the descriptor you sent for this text
- **requestResult:** "ok" any other result - depending on the request
- **requestError:** null if all ok, or an error message if an error occured


### Ask for user commands

You can ask BAZZ to record and analyze a vocal command form the user. To do this:

```java
    if (MyApplication.mBazzLib != null)
    {
        String requestId = MyApplication.mBazzLib.requestStopContinueVoiceMenu(String title, String subTitle, String descriptor, long timeout);
        
        // --- Or ---
        
        String requestId = MyApplication.mBazzLib.requestTextCallbackRecordStopVoiceMenu(String title, String subTitle, String descriptor, long timeout);
    }
```

Parameters are:

- **title:** text to be displayed at the top of the popup "Mic" showing when waiting for user voice command
- **subTitle:** text to be displayed at the bottom of the popup "Mic" showing when waiting for user voice command
- **descriptor:** a string to represent the 'meaning' of this voice menu (you can use it in your app when you get the callback of playback finished to identify it)
- **timeout:** a long value - the timeout to wait for user to respond

Return value:

is a String holding the ID of the request - you can use it to get the response from:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setOnBazzRequestResultListener(new BazzLib.BazzRequestResultListener() {
            @Override
            public boolean onRequestResult(String requestId, String requestDescriptor, String requestResult, String requestError)
            {
            	//...
            
                return false;
            }
        });
    }
```

In the callback you will get:

- **requestId:** the id of the message you asked to send
- **requestDescriptor:** the descriptor you sent for this text
- **requestResult:** the command the user said
- **requestError:** null if all ok, or an error message if an error occured




### Reply to sender with SMS

You can ask BAZZ to send a pre-defined SMS back to sender. To do this:

```java
    if (MyApplication.mBazzLib != null)
    {
        String requestId = MyApplication.mBazzLib.requestSendSMS(String senderPhone, null, String descriptor);
        
        // --- Or ---
        
        String requestId = MyApplication.mBazzLib.requestSendSMS(String senderPhone, String webLinkToVoiceRecording, String descriptor);
    }
```

Parameters are:

- **senderPhone:** phone number to send the SMS to
- **webLinkToVoiceRecording:** you can record a voice reply and upload it to BAZZ servers, then get a web link to the voice reply and send it via SMS
- **descriptor:** a string to represent the 'meaning' of this voice menu (you can use it in your app when you get the callback of playback finished to identify it)

Return value:

is a String holding the ID of the request - you can use it to get the response from:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setOnBazzRequestResultListener(new BazzLib.BazzRequestResultListener() {
            @Override
            public boolean onRequestResult(String requestId, String requestDescriptor, String requestResult, String requestError)
            {
            	//...
            
                return false;
            }
        });
    }
```

In the callback you will get:

- **requestId:** the id of the message you asked to send
- **requestDescriptor:** the descriptor you sent for this text
- **requestResult:** "ok" or null if error
- **requestError:** null if all ok, or an error message if an error occured





### Callback to sender

You can ask BAZZ to call back to sender. To do this:

```java
    if (MyApplication.mBazzLib != null)
    {
        String requestId = MyApplication.mBazzLib.requestMakeCall(String senderPhone, String descriptor);
    }
```

Parameters are:

- **senderPhone:** phone number to call
- **descriptor:** a string to represent the 'meaning' of this voice menu (you can use it in your app when you get the callback of playback finished to identify it)

Return value:

is a String holding the ID of the request - you can use it to get the response from:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setOnBazzRequestResultListener(new BazzLib.BazzRequestResultListener() {
            @Override
            public boolean onRequestResult(String requestId, String requestDescriptor, String requestResult, String requestError)
            {
            	//...
            
                return false;
            }
        });
    }
```

In the callback you will get:

- **requestId:** the id of the message you asked to send
- **requestDescriptor:** the descriptor you sent for this text
- **requestResult:** "ok" or null if error
- **requestError:** null if all ok, or an error message if an error occured




### Record a voice reply

You can ask BAZZ to record a voice reply (up to 15 seconds), so you can send it back to sender. To do this:

```java
    if (MyApplication.mBazzLib != null)
    {
        String requestId = MyApplication.mBazzLib.requestRecordToSD(String recordingTitle, String descriptor);
    }
```

Parameters are:

- **recordingTitle:** a text to display on top of the recording popup
- **descriptor:** a string to represent the 'meaning' of this voice menu (you can use it in your app when you get the callback of playback finished to identify it)

Return value:

is a String holding the ID of the request - you can use it to get the response from:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setOnBazzRequestResultListener(new BazzLib.BazzRequestResultListener() {
            @Override
            public boolean onRequestResult(String requestId, String requestDescriptor, String requestResult, String requestError)
            {
            	//...
            
                return false;
            }
        });
    }
```

In the callback you will get:

- **requestId:** the id of the message you asked to send
- **requestDescriptor:** the descriptor you sent for this text
- **requestResult:** the path to the recorded voice file on your device SD card
- **requestError:** null if all ok, or an error message if an error occured



### Upload voice reply to server

After you finished recording the voice reply, the SDK returns a path to the voice file saved on your SD card. You can ask BAZZ to upload the file to our servers, so you can send the link to the sender, and he/she can hear the voice reply. To do this:

```java
    if (MyApplication.mBazzLib != null)
    {
        String requestId = MyApplication.mBazzLib.requestUploadRecordingToServer(String recordFilePath, String descriptor);
    }
```

Parameters are:

- **recordFilePath:** the path to a voice file on your device SD
- **descriptor:** a string to represent the 'meaning' of this voice menu (you can use it in your app when you get the callback of playback finished to identify it)

Return value:

is a String holding the ID of the request - you can use it to get the response from:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setOnBazzRequestResultListener(new BazzLib.BazzRequestResultListener() {
            @Override
            public boolean onRequestResult(String requestId, String requestDescriptor, String requestResult, String requestError)
            {
            	//...
            
                return false;
            }
        });
    }
```

In the callback you will get:

- **requestId:** the id of the message you asked to send
- **requestDescriptor:** the descriptor you sent for this text
- **requestResult:** a web link to the recorded file on our servers. You can send this link to the server using 'requestSendSMS' (the second parameter).
- **requestError:** null if all ok, or an error message if an error occured













## User analytics

On some licenses, you can get access to analytics events, so that you can store on your server events and/or info related to user behaviour, etc.

### New user registration

You get details of a new user:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setOnBazzNewUserDetailsListener(new BazzLib.BazzNewUserDetailsListener() {
            @Override
            public void onNewUserDetails(sUserDetails rec)
            {
                //... you can save this info to your server.
            }
        });
    }
```

The event info object:

```java
public class sUserDetails {

    public String driverKey;
    public String DeviceCarrier;
    public String DeviceCountry;
    public String AppMode;
    public String AppTtsLanguage;
    public String Name;
    public String Phone;
    public String Email;
    public String AppVersion;
    public String DeviceModel;
    public String DeviceFirmwareVersion;
    public String AppLanguage;
    public String DeviceLocale;
    public int    DeviceGmtDiffInHours;
}
```


### Event callback

You get details events happening on the device:

```java
    if (MyApplication.mBazzLib != null)
    {
        MyApplication.mBazzLib.setOnBazzAnalyticsEventListener(new BazzLib.BazzAnalyticsEventListener() {
            @Override
            public void onAnalyticsEvent(sLogEvent eventInfo)
            {
                //... you can save this info to your server. see below description of eventInfo object
            }
        });
    }
```

The event info object:

```java
public class sLogEvent {

    String sDriverKey;		// id of the driver
    
    String sDriveId;		// id of current trip (assigned whenever BAZZ detects a new drive trip,
    						// and attached to events related to the current trip)
    						
    String sTimestamp;		// timestamp of the event
    String sActivityType;	// type of event - see below
    String sMessageId;		// if event is related to an incoming message - unique id of the message
    String sMessageType;	// type of message (SMS, WhatsApp,...)
    String sListenType;		// if BAZZ handles the messages - what user selected in the first vocal menu (stop/continue)
    String sResponseType;	// if BAZZ handles the messages - what user selected in the second vocal menu (text/callback/record/stop)
}
```

types of analytic events:

```java
    public static final String KEY_ACTIVITY_TYPE_START_DRIVE = "StartDrive";
    public static final String KEY_ACTIVITY_TYPE_END_DRIVE   = "EndDrive";

    public static final String KEY_ACTIVITY_TYPE_ALWAYS_ON        = "Always";
    public static final String KEY_ACTIVITY_TYPE_WHILE_DRIVING    = "Driving";
    public static final String KEY_ACTIVITY_TYPE_OFF              = "Off";

    public static final String KEY_ACTIVITY_TYPE_INCOMING_MESSAGE = "Receive";
    public static final String KEY_ACTIVITY_TYPE_FIRST_MENU       = "Listen";
    public static final String KEY_ACTIVITY_TYPE_RESPONSES        = "Response";

    public static final String KEY_FIRST_MENU_TYPE_STOP           = "Skip";
    public static final String KEY_FIRST_MENU_TYPE_CONTINUE       = "Listen";
    public static final String KEY_FIRST_MENU_TYPE_TOUCH          = "Touch";

    public static final String KEY_SECOND_MENU_TYPE_SMS           = "SMS";
    public static final String KEY_SECOND_MENU_TYPE_CALLBACK      = "Callback";
    public static final String KEY_SECOND_MENU_TYPE_RECORD        = "Record";
    public static final String KEY_SECOND_MENU_TYPE_REPEAT        = "Repeat";
    public static final String KEY_SECOND_MENU_TYPE_STOP          = "Stop";
    public static final String KEY_SECOND_MENU_TYPE_TOUCH         = "Touch";
    public static final String KEY_SECOND_MENU_TYPE_STOP_WHILE_LISTEN = "Stop While Listen";
```



# Thank you for using BAZZ !

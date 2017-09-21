# Intent-android
This project demonstrate the Intent mechanism in android. It also explains Intent types and primary pieces of information intent has.

**What is intent?**

It is a kind of message or information that is passed to the components. It is used to launch an activity, display a web page, send sms, send email etc. An intent is a messaging object you can use to request an action from another app component. Although intents facilitate communication between components in several ways, there are three fundamental use cases:

Starting an activity

Starting a service

Delivering a broadcast

(or) A class (Intent) describes what a caller desires to do. The caller sends this intent to Android’s intent resolver, which finds the most suitable action/activity for the intent. E.g. opening a PDF file is an intent, and the Adobe Reader is the suitable activity for this intent.

There are two types of intents in android:

Implicit Intent

Explicit Intent

**Implicit Intent:** Implicit intents do not name a specific component, but instead declare a enough general action to perform, which allows a component from another app to handle it. For example, if you want to show the user a location on a map, you can use an implicit intent to request that another capable app show a specified location on a map.

Implicit intent is used to invoke the system components. Implicit intent is used when you call system default intent like send email, send SMS, dial number.

Implicit Intent doesn't specify the component. In such case, intent provides information of available components provided by the system that is to be invoked.

**Explicit Intent:** Explicit intent is used to invoke the activity class. Explicit intent when you call you are on application activity from one to another. Explicit Intent specifies the component. In such case, intent provides the external class to be invoked.
Caution: To ensure that your app is secure, always use an explicit intent when starting a service and do not declare intent filters for your services. Using an implicit intent to start a service is a security hazard because you can't be certain what service will respond to the intent, and the user can't see which service starts.

Demonstration:-
![alt text](https://developer.android.com/images/components/intent-filters@2x.png)

When you create an implicit intent, the Android system finds the appropriate component to start by comparing the contents of the intent to the intent filters declared in the manifest file of other apps on the device. If the intent matches an intent filter, the system starts that component and delivers it the Intent object. If multiple intent filters are compatible, the system displays a dialog so the user can pick which app to use.

**The primary pieces of information in an intent are:**

**component --** The name of the component to start. Specifies an explicit name of a component class to use for the intent. You can set the component name with setComponent(), setClass(), setClassName(), or with the Intent constructor.

**action --** The general action to be performed, such as ACTION_VIEW, ACTION_EDIT, ACTION_MAIN, ACTION_SEND etc. You can specify your own actions for use by intents within your app (or for use by other apps to invoke components in your app), but you usually specify action constants defined by the Intent class or other framework classes. You can specify the action for an intent with setAction() or with an Intent constructor. (If you define your own actions, be sure to include your app's package name as a prefix).

**data --** The data to operate on, such as a person record in the contacts database, expressed as a Uri. The URI (a Uri object) that references the data to be acted on and/or the MIME type of that data. When creating an intent, it's often important to specify the type of data (its MIME type) in addition to its URI. To set only the data URI, call setData(). To set only the MIME type, call setType(). If necessary, you can set both explicitly with setDataAndType(). Caution: If you want to set both the URI and MIME type, don't call setData() and setType() because they each nullify the value of the other. Always use setDataAndType() to set both URI and MIME type.

**category --** A string containing additional information about the kind of component that should handle the intent. Any number of category descriptions can be placed in an intent, but most intents do not require a category. E.g CATEGORY_BROWSABLE, CATEGORY_LAUNCHER. You can specify a category with addCategory().

**type --** Specifies an explicit type (a MIME type) of the intent data.

**extras --** This is a Bundle of any additional information. This can be used to provide extended information to the component. Key-value pairs that carry additional information required to accomplish the requested action. You can add extra data with various putExtra() methods, each accepting two parameters: the key name and the value. You can also create a Bundle object with all the extra data, then insert the Bundle in the Intent with putExtras().

**Caution:** Do not use Parcelable or Serializable data when sending an intent that you expect another app to receive. If an app attempts to access data in a Bundle object but does not have access to the parceled or serialized class, the system raises a RuntimeException.

**Flag --** Flags are defined in the Intent class that function as metadata for the intent.

**Caution:** It's possible that a user won't have any apps that handle the implicit intent you send to startActivity(). Or, an app may be inaccessible because of profile restrictions or settings put into place by an administrator. If that happens, the call fails and your app crashes. To verify that an activity will receive the intent, call resolveActivity() on your Intent object. If the result is non-null, there is at least one app that can handle the intent and it's safe to call startActivity(). If the result is null, do not use the intent and, if possible, you should disable the feature that issues the intent. The following example shows how to verify that the intent resolves to an activity. This example doesn't use a URI, but the intent's data type is declared to specify the content carried by the extras.
// Create the text message with a string

Intent sendIntent = new Intent();

sendIntent.setAction(Intent.ACTION_SEND);

sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage);

sendIntent.setType("text/plain");

// Verify that the intent will resolve to an activity

if (sendIntent.resolveActivity(getPackageManager()) != null) {

    startActivity(sendIntent);
    
}
 
**Note:-**

If there's only one app that can handle it, that app opens immediately and is given the intent. If multiple activities accept the intent, the system displays a dialog.

Restricting access to components - Use exported attribute to "false" in Manifest.

**Forcing an app chooser :-** if multiple apps can respond to the intent and the user might want to use a different app each time, you should explicitly show a chooser dialog.

Intent sendIntent = new Intent(Intent.ACTION_SEND);

// Always use string resources for UI text.

// This says something like "Share this photo with"

String title = getResources().getString(R.string.chooser_title);

// Create intent to show the chooser dialog

Intent chooser = Intent.createChooser(sendIntent, title);

// Verify the original intent will resolve to at least one activity

if (sendIntent.resolveActivity(getPackageManager()) != null) {

    startActivity(chooser);
    
}
 
**Receiving an implicit intent:-**

To advertise which implicit intents your app can receive, declare one or more intent filters for each of your app components with an <intent-filter>element in your manifest file. Each intent filter specifies the type of intents it accepts based on the intent's action, data, and category.  The system delivers an implicit intent to your app component only if the intent can pass through one of your intent filters. You can create a filter that includes more than one instance of <action>, <data>, or <category>.
When you want to handle multiple kinds of intents, but only in specific combinations of action, data, and category type, then you need to create multiple intent filters.
An implicit intent is tested against a filter by comparing the intent to each of the three elements. To be delivered to the component, the intent must pass all three tests.
 
E.g <activity android:name="ShareActivity">
  
    <intent-filter>
    
        <action android:name="android.intent.action.SEND"/>
        
        <category android:name="android.intent.category.DEFAULT"/>
        
        <data android:mimeType="text/plain"/>
        
    </intent-filter>
    
</activity>
 
**What is the function of an intent filter? **

An intent filter is an expression in an app's manifest file that specifies the type of intents that the component would like to receive. For instance, by declaring an intent filter for an activity, you make it possible for other apps to directly start your activity with a certain kind of intent. Likewise, if you do not declare any intent filters for an activity, then it can be started only with an explicit intent.
Because every component needs to indicate which intents they can respond to, intent filters are used to filter out intents that these components are willing to receive. One or more intent filters are possible, depending on the services and activities that is going to make use of it.
 
** Pending Intent:-**

A PendingIntent object is a wrapper around an Intent object. The primary purpose of a PendingIntent is to grant permission to a foreign application to use the contained Intent as if it were executed from your app's own process.

Major use cases for a pending intent include the following:

* Declaring an intent to be executed when the user performs an action with your Notification (the Android system's NotificationManager executes the Intent).

* Declaring an intent to be executed when the user performs an action with your App Widget (the Home screen app executes the Intent).

* Declaring an intent to be executed at a specified future time (the Android system's AlarmManager executes the Intent).

Just as each Intent object is designed to be handled by a specific type of app component (either an Activity, a Service, or a BroadcastReceiver), so too must a PendingIntent be created with the same consideration. When using a pending intent, your app doesn't execute the intent with a call such as startActivity(). Instead, you must declare the intended component type when you create the PendingIntent by calling the respective creator method:

PendingIntent.getActivity() for an Intent that starts an Activity.

PendingIntent.getService() for an Intent that starts a Service.

PendingIntent.getBroadcast() for a Intent that starts an BroadcastReceiver.
 
**Intent resolution:-**

When the system receives an implicit intent to start an activity, it searches for the best activity for the intent by comparing the it to intent filters based on three aspects:

**Action.** (To specify accepted intent actions, an intent filter can declare zero or more <action> elements. To pass this filter, the action specified in the Intent must match one of the actions listed in the filter.)
  
**Data (both URI and data type)** (To specify accepted intent data, an intent filter can declare zero or more <data> elements. Each <data> element can specify a URI structure and a data type (MIME media type). Each part of the URI is a separate attribute: scheme, host, port, and path:) 
content://com.example.project:200/folder/subfolder/etc
In this URI, the scheme is content, the host is com.example.project, the port is 200, and the path is folder/subfolder/etc.
The data test compares both the URI and the MIME type in the intent to a URI and MIME type specified in the filter. The rules are as follows:
An intent that contains neither a URI nor a MIME type passes the test only if the filter does not specify any URIs or MIME types.
An intent that contains a URI but no MIME type (neither explicit nor inferable from the URI) passes the test only if its URI matches the filter's URI format and the filter likewise does not specify a MIME type.
An intent that contains a MIME type but not a URI passes the test only if the filter lists the same MIME type and does not specify a URI format.
An intent that contains both a URI and a MIME type (either explicit or inferable from the URI) passes the MIME type part of the test only if that type matches a type listed in the filter. It passes the URI part of the test either if its URI matches a URI in the filter or if it has a content: or file: URI and the filter does not specify a URI. In other words, a component is presumed to support content: and file: data if its filter lists only a MIME type.
  
**Category** (Therefore, an intent with no categories always passes this test, regardless of what categories are declared in the filter.)
Note: Android automatically applies the CATEGORY_DEFAULT category to all implicit intents passed to startActivity() and startActivityForResult(). If you want your activity to receive implicit intents, it must include a category for "android.intent.category.DEFAULT"in its intent filters, as shown in the previous <intent-filter> example.
 
**Intent matching:-**

Intents are matched against intent filters not only to discover a target component to activate, but also to discover something about the set of components on the device.  queryIntentActivities() returns a list of all activities that can perform the intent passed as an argument, and queryIntentServices() returns a similar list of services. Neither method activates the components; they just list the ones that can respond. There's a similar method, queryBroadcastReceivers(), for broadcast receivers.
 
References:-

https://developer.android.com/reference/android/content/Intent.html
https://developer.android.com/guide/components/intents-filters.html
http://www.vogella.com/tutorials/AndroidIntent/article.html
https://www.raywenderlich.com/160019/android-intents-tutorial-2
http://codetheory.in/android-intents/

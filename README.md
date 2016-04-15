# SPref

### Spref allows to manage shared preferences in a simply way ###

### Usage ###

In order to initialize the library the following must be applied

```java
public class ApplicationSample extends Application {
    private static SettingsConnector mSharedPreferences;
    
    @Override
    public void onCreate() {
        super.onCreate();
        mSharedPreferences = mSSPref.init(this).buildSettings(); //Initialize the SPref
    }
    
    public SettingsConnector getSPref() {
        return mSharedPreferences;
    }
}
```

Then the user can manage the SharedPreferences the way he wants by using e.g.
```java
 SPref.buildSettings().saveSetting("settings-key", "value");
 SPref.buildSettings().getSetting("settings-key");
```

### Default Preferences ###
It is possible to add a default resource file by providing it to the Spref 

```java
 SPref.init(this).provideDefaultResourceFile(R.raw.file).buildSettings();
```

This will merge the resource file into the shared preferences.

The following xml tags are supported:

```xml
<root>
 <value name="SETTING_STRING_NAME">SETTING_STRING_VALUE</value>
 <integer name="SETTING_INTEGER_NAME">SETTING_INTEGER_VALUE</integer>
 <float name="SETTING_FLOAT_NAME">SETTING_FLOAT_VALUE</float>
</root>
```

### Todo ###
- Allows to provide a file (not resource) in order to merge that file with shared preferences
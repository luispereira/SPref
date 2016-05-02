# SPref

### Spref allows to manage shared preferences in a simply way ###

### Usage ###

Dependency:
```groovy
 compile "com.github.luispereira:spref:0.4.0"
```

Repository:
```groovy
    repositories {
        jcenter()   //or  mavenCentral()
    }
```

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

Then the user can manage the SharedPreferences the way we want by using e.g.
```java
 ApplicationSample.getInstance().getSPref().saveSetting("settings-key", "value");
 ApplicationSample.getInstance().getSPref().getSetting("settings-key");
```

if the user do not want to provide any specific name or file then simply:

```java
 SPref.buildSettings(context).saveSetting("settings-key", "value");
 SPref.buildSettings(context).getSetting("settings-key");
```
or:

```java
 SPref.init(context).buildSettings().saveSetting("settings-key", "value");
 SPref.init(context).buildSettings().getSetting("settings-key");
```

### Change Default Preferences file name ###
It is possible to change the name of shared preferences (by default is "sp_settings") by doing on the initialization: 
```java
 SPref.init(this).name("NEW_NAME").buildSettings();
```


### Default Preferences ###
It is possible to add a default resource file by providing it to the Spref 
```java
 SPref.init(this).name("NEW_NAME").provideDefaultResourceFile(R.raw.file).buildSettings();
```
This will merge the resource file into the shared preferences. If there is already a value with the same key, the value will not be overrided.

The following xml tags are supported:

```xml
<default>
 <string name="SETTING_STRING_NAME">SETTING_STRING_VALUE</string>
 <integer name="SETTING_INTEGER_NAME">SETTING_INTEGER_VALUE</integer>
 <float name="SETTING_FLOAT_NAME">SETTING_FLOAT_VALUE</float>
</default>
```

### Todo ###
- Allows to provide a file (not resource) in order to merge that file with shared preferences

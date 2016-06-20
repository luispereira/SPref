# SPref

### SPref allows to manage shared preferences in a simply way ###

### Usage ###

Dependency:
```groovy
 compile "com.github.luispereira:spref:0.5.4"
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
It is possible to add a default resource file by providing it to the SPref 
```java
 SPref.init(this).name("NEW_NAME").provideDefaultResourceFile(R.raw.file, false).buildSettings();
```
This will merge the resource file into the shared preferences. If there is already a value with the same key, the value will not be overrided. In order to change this behaviour and override all the values the user should use the following parameters:
 
```java
  SPref.init(this).name("NEW_NAME").provideDefaultResourceFile(R.raw.file, true).buildSettings();
```

Be aware that calling this on application OnCreate() will always override the values according to the given file when the application is created.

The user can also provide a file after the initialization of the SDK by doing (the merge will not override any setting if its false, otherwise pass it true):

```java
 ApplicationSample.getInstance().getSPref().mergeSettings(FILE, false);
```


The following xml tags are supported:

```xml
<default>
 <string name="SETTING_STRING_NAME">SETTING_STRING_VALUE</string>
 <integer name="SETTING_INTEGER_NAME">SETTING_INTEGER_VALUE</integer>
 <float name="SETTING_FLOAT_NAME">SETTING_FLOAT_VALUE</float>
 <long name="SETTING_LONG_NAME">SETTING_LONG_VALUE</long>
 <boolean name="SETTING_BOOLEAN_NAME">SETTING_BOOLEAN_VALUE</boolean> <!-- (true/false) -->
</default>
```


### Encryption ###

The user can also encrypt his information that is saved on the shared preferences, to do that just use when initializing the sdk:

```java
    SPref.init(this).encrypt(com.lib.spreferences.BuildConfig.PASSWORD_KEY).buildSettings();
```

The encrypt() method configures the key to use when requesting the following methods to encrypt:

```java
    ApplicationSample.getInstance().getSPref().saveEncryptedSetting("settings-key", "value");
```

Or this to retrieve the encrypted value:

```java
    ApplicationSample.getInstance().getSPref().getEncryptedSetting("settings-key");
```

### Change Preferences Mode ###

In order to change the default mode of "MODE_PRIVATE" of the shared preferences, the following method should be called on initialization:

```java
   (...).mode(MODE).buildSettings();
```

### RxJava ###

For the RxJava version you can use:
```html
    https://github.com/luispereira/RxSPref
```

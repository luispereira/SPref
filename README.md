# SPref

### Spref allows to manage shared preferences in a simply way ###

### Usage ###

In order to initialize the library the following must be applied

```java
public class ApplicationSample extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SPref.init(this); //Initialize the SPref
        //SPref.init(this).provideDefaultResourceFile(R.raw.file); //With this way the SharedPreferences is initialized with a default resource file
    }
}
```

Then the user can manage the SharedPreferences the way he wants by using e.g.
```java
 SPref.buildSettings().saveSetting("settings-key", "value");
 SPref.buildSettings().getSetting("settings-key");
```

### Todo ###
- Allows to provide a file (not resource) in order to merge that file with sharef preferences
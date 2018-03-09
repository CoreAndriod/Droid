
# Droid
Droid is a light weight Android Library to make great app from less code.  

<b>Start</b>

<code>final Droid driod  = new Droid(AppContext);</code>

Note: This is unstable version and may recieved a huge update in the future 

# Installation 

<b>Step 1</b>: Add it in your root build.gradle at the end of repositories.
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' } // add this
		}
	}
```
<b>Step 2</b>: Add the dependencys.

```
dependencies {
	        compile 'com.github.coreandroid:Droid:v1.0-beta.0'
}
```

<b>Step 2</b>: Right click your app folder then click <code>Open Module Settings</code>
<img src="http://edio.esy.es/droid/droid_imagestep1.png">

<b>Step 3</b>: Click the add icon to add the AAR file/package</code>
<img src="http://edio.esy.es/droid/droid_imagestep2.png">

<b>Step 4</b>: Import the AAR file/package then a new will appear, then browse your AAR file</code>
<img src="http://edio.esy.es/droid/droid_imagestep3.png">

<b>Step 5</b>: Click the app folder then add the module(follow the steps from screen shot)</code>
<img src="http://edio.esy.es/droid/droid_imagestep4.png">

<b>Step 6</b>: The select the droid module and click Ok</code>
<img src="http://edio.esy.es/droid/droid_imagestep6.png">

<b>Step 7</b>: Finally import droid library to your java code, and Done! :)</code>
<img src="http://edio.esy.es/droid/droid_imagestep7.png">





## HTTP

<code>CoreHttp http = new CoreHttp(droid);</code>

Params:

<ul>

<li>REQUEST_ID- String</li>
<li>URL- String</li>
<li>POSTDATA- HashMap</li>
<li>ONSUCCESS- Runnable</li>
<li>ONERROR- Runnable</li>
</ul>

### Get Request

```
  http.$Get("GET_ORDER", "http://api.order.com/getorder", new Runnable() {
            @Override
            public void run() {
            
             droid.$httpResponse.get("GET_ORDER").$Data // get response
             
            }
        }, new Runnable() {
            @Override
            public void run() {
                
            }
  });
```

### Post Request

```
 HashMap postdata = new HashMap();
        postdata.put("Item","Apple");
        postdata.put("Note","Some note...");        
        
        http.$Post("CREATE_ORDER", "http://api.order.com/createorder",postdata, new Runnable() {
            @Override
            public void run() {

               droid.$httpResponse.get("CREATE_ORDER").$Data // get response

            }
        }, new Runnable() {
            @Override
            public void run() {

            }
        });


```

## JSON Object and JSON Array  Parser


### Parse String to JSON Object

```
var strJSON = { 
        "Category": "Fruit",
        "Notes": "Some notes..."             
}

var json = droid.$Json(strJSON)
droid.$GetString(json,"Category") //get parsed data property value

```

### Parse String to JSON Array

```
var strArray = {"Items":["Apple","Mango"]}

var json = droid.$JsonArray(strArray)
droid.$GetString(json,1) //get parsed data index value

```

<ul>
<li>$Get()- Object</li>
<li>$GetString()- String</li>
<li>$GetInt()- Integer</li>
<li>$GetDouble()- Double</li>
<li>$GetLong()- Long</li>
<li>$GetBool()- boolean</li>
</ul>

## Session

<code>CoreSession session = new CoreSession(droid);</code>

### Set Session
```
session.$Set("Key","Value");
```

### Get Session
```
session.$Get("Key");
```

### Check Session
```
if(session.$Check("Key") == session.$IsActive){

            //session is active

}
```


## File Proccessing

<code>CoreFile file = new CoreFile(droid,Environment.getExternalStorageDirectory().toString());</code>

### Create Folder Object
```
CoreFileFolder orders = file.$Folder("/orders");
```
### Write Text File
```
orders.$WriteText("Item.txt","apple",false);
```
### Read Text File
```
orders.$ReadText("Item.txt",false);
```

### Copy File
```
CoreFileFolder archive = file.$Folder("/Archive");

orders.$Copy("Item.txt",archive,"Archived_item.txt");

```

### Copy Folder
```
CoreFileFolder other = file.$Folder("/Other");

orders.$CopyDir(other);

```

### Delete File
```
orders.$Delete("Item.txt");
```

### Delete Folder
```
orders.$DeleteDir();
```

## View

<code>CoreView view = new CoreView(droid,(Button)findViewById(R.id.clickable));</code>

### GetView

This is to allow you to use coreview to any view related method.

```
view.$GetView();
```

### Event
```
view.$Event().$Click(new Event() {
          @Override
          public Boolean Run(EventResult eventResult) {
              return null;
          }
});
```

Other Events:

<ul>
<li>$Keypress</li>
<li>$Touch</li>
<li>$Focus</li>


</ul>

EventResult Properties:

<ul>
<li>$View- View</li>
<li>$HasFocus- Boolean</li>
<li>$KeyCode- Integer</li>
<li>$KeyEvent- KeyEvent</li>
<li>$MotionEvent- MotionEvent</li>

</ul>











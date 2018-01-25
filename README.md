# Droid
Droid is a light weight Android Library  

<b>Start</b>

<code>final Droid driod  = new Droid(AppContext);</code>

Note: This is unstable version

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
<li>Get()- Object</li>
<li>GetString()- String</li>
<li>GetInt()- Integer</li>
<li>GetDouble()- Double</li>
<li>GetLong()- Long</li>
<li>GetBool()- boolean</li>
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







# Droid
Droid is a light weight Android Library  

<b>Start</b>

<code>Droid driod  = new Droid(AppContext);</code>

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

            }
        }, new Runnable() {
            @Override
            public void run() {

            }
        });


```


# KITE-Amazon-Test
This test is written with __KITE-Framework__, which follows the Page Object Model architecture
for automated test.
## Test Architecture

### Pages
 Contain all of the UI element object and the function to interact with them. All automation APIs (Selenium) are
 handled in these objects.

### Steps
In these steps, you call specific functions from the pages, to perform specific task to your app, 
and move you test script along. (Status: passed/broken)

### Checks
These are the same as steps, but the purpose of them is to perform verify (assert) certain values/stats
 to determine if your test is passing or failing. (Status : passed/failed)
 

A test passes when all of the Steps are not BROKEN, and all of the Checks don't FAIL.
 
### Test Script

The test itself is a ensemble of __steps__ and __checks_.

Step | Client |
------------- |------------- 
1 | Start monitoring with __MonitoringStep__ 
2 | Open URL with __OpenURStep__ 
3 | Sign in with __SignInStep__ 
4 |  Check Availability with __CheckAvailability__
4.1 | If stock: Send telegram text
4.2 | Refresh Page
5 | Go back to 4 


## Understand the test config file
 
 To run this test, you will need to have a config file that the KITE Engine will understands.
 A sample config file is provided at  `configs/rtx.config.json`  

### Important parameters 

#### Grids
Set the address of your Selenium Hub:  
  ```
  "grids": [
     {
       "type": "local",
       "url": "http://localhost:4444/wd/hub"
     }
   ],
  ```  
Refer to the _Compilation and Run_ section to see how to set up a Selenium grid.

#### Clients
Set your browsers version and platform according to what is available on your Grid. 
This will define which browser configuration what will be used in the test. Make sure that you grid 
has enough resource to launch the test.
```
"clients": [
    {
      "browserName": "chrome",
      "version": "87",
      "platform": "WINDOWS"
    }
  ]
```

#### Payload

This is the object that contains additional data that we can use during the test. This object will
be process by the function `payloadHandling()` of the test. Here we will pass some parameters:

__url__ (The base url of the amazon product)
```
  "url": "https://www.amazon.sg/EVGA-10G-P5-3897-KR-GeForce-Technology-Backplate/dp/B08HR3Y5GQ/"
```

__User account__

You will need to provide credentials to log in to amazon, it is required as some product are region locked.

```
  "credentials": {
       "email": "xxx@gmail.com",
       "password": "xxx"
     }
```

#### Telegram infos

A notification is sent on telegram if stock is available. 
You'll need to configure a bot on it first.

Step |  |
------------- |------------- 
1. | Create bot in Telegram
1.1. | Find “BotFather” in Telegram;
1.2. | Type “/start”;
1.3. | Type “/newbot”;
1.4. | Type bot name e.g. “any_bot”;
1.5. | Type bot username e.g. “any_bot”;
1.6. | BotFather will create your bot and will send Token to access the HTTP API.
2. | Create group chat
2.1 | Add new bot to group chat
2.2 | Fetch https://api.telegram.org/bot<YourBOTToken>/getUpdates
2.3 | Look for the "chat" object: {"update_id":8393,"message":{"message_id":3,"from":{"id":7474,"first_name":"AAA"},"chat":{"id":,"title":""},"date":25497,"new_chat_participant":{"id":71,"first_name":"NAME","username":"YOUR_BOT_NAME"}}}
If it returns {"ok":true,"result":[]}, try to remove then add the bot, or to @ it.

You'll then need to provide the infos on the payload:
```
  "telegram": {
       "token": bottoken",
       "chatId": "chatId"
     }
```

## Compiling and Running

### Compiling
You'll of course need KITE to run this test.  

__Clone KITE__
```
git clone https://github.com/webrtc/KITE.git
```

To setup KITE, please follow these [instructions](https://github.com/webrtc/KITE/blob/master/README.md).   
The test script will only compile and run after KITE has been compiled using Maven, which will install all the required Maven
dependencies in your local m2 repo and the kite-jar-with-dependencies.jar file has been compiled.

During the setup for KITE, you can choose to setup the default local grid too. but the default value 
for number of chrome is 5. To have more chrome to run bigger test, you can set up your own grid.


__Set up your own grid__

Provided that you have install java, maven as in KITE's instructions.

- Step 1: Download and install Google Chrome
- Step 2: Download the chromedriver [here](https://chromedriver.chromium.org/downloads), make sure
to download the correct version.
- Step 3: Download the jar file for Selenium [here](https://selenium-release.storage.googleapis.com/3.141/selenium-server-standalone-3.141.59.jar)
- Step 4: Start the hub on localhost, port 4444:
```
java -jar PATH/TO/SELENIUM.JAR -role hub
```
- Step 5: Start the node and connect it to the hub, you can change the MAX_INSTANCES value to any value
you want, as long as your machine can handle it:
```
java -Dwebdriver.chrome.driver="PATH/TO/CHOMEDRIVER" -jar "PATH/TO/SELENIUM.JAR" -role node -hub http://localhost:4444/grid/register/ -browser browserName=chrome,version=VERSION,platform=PLATFORM,maxInstances=MAX_INSTANCES
```

Done.

### Compile

You need to finish the KITE setup and compilation before this step (grid is not needed here)

Go to the cloned folder `KITE-Amazon-Test`:

```
kite_c
```
or
```
mvn clean install
```

### Run

```
cd KITE-Amazon-Test
kite_r rtx.config.json
```

The `kite_r` is a script you have while setting up KITE, this replaces a long java command to run the test.

During the test, you will see the logs showing the step and the status of those steps.
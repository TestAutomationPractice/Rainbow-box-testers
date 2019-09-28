::-browserTimeout in seconds : number of seconds a browser session is allowed to hang while a WebDriver command is running (example: driver.get(url)). If the timeout is reached while a WebDriver command is still processing, the session will quit. Minimum value is 60. An unspecified, zero, or negative value means wait indefinitely. Default: 0, New value 420

::-timeout, -sessionTimeout in seconds : Specifies the timeout before the server automatically kills a session that hasn't had any activity in the last X seconds. The test slot will then be released for another test to use. This is typically used to take care of client crashes. Default: 1800, New value 2000
::-sessionTimeout is used in combination with -cleanUpCycle which polls the running instances every 5 seconds (kept as default hence not added here)

java -Dwebdriver.gecko.driver=drivers\geckodriver.exe -Dwebdriver.chrome.driver=drivers\chromedriver.exe -Dwebdriver.ie.driver=drivers\IEDriverServer.exe -jar selenium-server-standalone-3.11.0.jar -role node -hub http://127.0.0.1:4444/grid/register -port 5555 -browserTimeout 420 -sessionTimeout 2000


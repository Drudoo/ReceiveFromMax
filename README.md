# README

This class is used for receiving data from Max 6 to Java. 

## Known Bugs
* Can't use values higher than 65535. 65536 is 0. 65537 is 1 and so on.
* Doesn't like negative values. -1 is 65535. -2 is 65534 and so on.
* Can't use floats or doubles. Only accepts Integers from Max. 
* Only support local use. (IP: 127.0.0.1)
* Use tentative float calculation at own risk. You might not get accurate data.



## How to use
*Use the Test.java as an example*

* Create a new instance of the class with the port number as variable.
*ReceiveFromMax returnData = new ReceiveFromMax(7400);*

* Java will create the connection and wait for Max to send a new variable.
* When the variable is received, it can be accessed using getReturnData().
*returnData.getReturnData();*

## Max 6 compressed code
Paste this code into a new Max Patch to try it out. 



----------begin_max5_patcher----------

320.3ocmRErSCCCC8b5WQjOWpZFC1fa7cfPSoMlsL0kTkjNFLs+cRbaGcHDC
wEGYmW764mywLFTYOfdf+H+YNicLiwnRoBrgbFrSdntQ5IXfoaWE5f79qrcg
FLDduE66AnMAHmCURyZf+x.pVYndi1rdkCqC8.EKdnnLmKlKRG2UlhyhwyuQ
qH5rUau41Q1d0ZBF4NhK3ImV1.mYvEqGP2JzHqZHDkC2EUr1DUIoewjV40eP
.EyJlfsejHvyREOkkkB4+U+AeKp4QcEvCz.CcpVOZTQtVTDGyBAew7xR35Fz
RQuO8qNj35Nz+2EJm3BD4Pi178OMzDjpeo03sct5w+FC6R9WifB8AsQFzVyD
LhKvrQqTnY5BUo8ocr5mEmrscO57CsjDRbCs05Ro2mSoZSeJ0Qvg60i3Wlk5
1orOA7Dj3NN

-----------end_max5_patcher-----------

from hashlib import sha256 #line:1
import time #line:2
class Oscartokiclass :#line:4
    def __init__ (O00OO000O0OOO00O0 ,debugMode =False ):#line:6
        O00OO000O0OOO00O0 .clientkey =""#line:7
        O00OO000O0OOO00O0 .debugMode =debugMode #line:8
        O00OO000O0OOO00O0 .toki =""#line:9
        O00OO000O0OOO00O0 .life_time_of_toki =3 #line:10
    def setClientkey (O000OO00OOOOO0OO0 ,O000O0OO0O0O00OOO ):#line:12
        ""#line:13
        O000OO00OOOOO0OO0 .clientkey =O000O0OO0O0O00OOO #line:14
    def getClientkey (O000O0OO0000OO0OO ):#line:15
        ""#line:16
        return O000O0OO0000OO0OO .clientkey #line:17
    def setToki (O00OOO000OOO0OOO0 ,O00OO000O0OOOO000 ):#line:20
        ""#line:21
        O00OOO000OOO0OOO0 .toki =O00OO000O0OOOO000 #line:22
    def getToki (OOOOOOOOOO0O0OOOO ):#line:23
        ""#line:24
        return OOOOOOOOOO0O0OOOO .toki #line:25
    def setDebugmode (OO00OO00O0OOO0O0O ,OOO0000000O0OOO00 ):#line:28
        ""#line:29
        OO00OO00O0OOO0O0O .debugMode =OOO0000000O0OOO00 #line:30
    def getDebugmode (OOO0OOOO000OO0O00 ):#line:31
        ""#line:32
        return OOO0OOOO000OO0O00 .debugMode #line:33
    def tokiPrint (O0OOO00OOOOOO0OOO ,O0OO00OOOOOOOOOO0 ):#line:36
        ""#line:38
        print ("[ DEBUG-OSCARTOKI ] > "+str (O0OO00OOOOOOOOOO0 ))#line:39
    def getSHA (OOOOO0O0O0OO0OOO0 ,OO0O0000O0O00O0O0 ):#line:42
        return sha256 (OO0O0000O0O00O0O0 .encode ()).hexdigest ()#line:43
    def reverseString (OO0O00OOO000000OO ,OOO00O0OO0O0OOOOO ):#line:45
        return OOO00O0OO0O0OOOOO [::-1 ]#line:46
    def generateToki (O000O0O0OO0O0O00O ):#line:48
        ""#line:49
        O000O0O0OO0O0O00O .tokiPrint ("Starting Generation of the Toki.")#line:51
        _OO0OOOOOOO00O0000 =time .time ()#line:54
        _O000O00O000OO0000 =str (_OO0OOOOOOO00O0000 )#line:55
        O00O0OOOOOOOOOO00 =O000O0O0OO0O0O00O .getSHA (O000O0O0OO0O0O00O .getSHA (O000O0O0OO0O0O00O .clientkey +" - "+_O000O00O000OO0000 +" - "+O000O0O0OO0O0O00O .clientkey ))#line:58
        O00OO0OO00000O0O0 =O000O0O0OO0O0O00O .getSHA (_O000O00O000OO0000 +"Brouillage de piste")[:15 ]#line:60
        OOOOOOO000O00OO00 =O000O0O0OO0O0O00O .reverseString (_O000O00O000OO0000 )+"|"+O000O0O0OO0O0O00O .reverseString (O00O0OOOOOOOOOO00 )+"|"+O00OO0OO00000O0O0 #line:63
        O000O0O0OO0O0O00O .tokiPrint ("Toki generated: "+OOOOOOO000O00OO00 )#line:65
        O000O0O0OO0O0O00O .setToki (OOOOOOO000O00OO00 )#line:67
    def verifyToki (O0O0OOO00000OOOOO ,OO0OO00OOO00000OO ):#line:69
        ""#line:70
        O0000O0000O0OO0OO =time .time ()#line:73
        O0OO0O00OOOO0OO0O =str (O0000O0000O0OO0OO )#line:74
        O0000O0000O0OO0OO =int (O0OO0O00OOOO0OO0O [:9 ],10 )#line:75
        OOOOOO0O000O0OOOO =OO0OO00OOO00000OO .split ("|")#line:77
        OO0OOO00OOOO00O00 =OOOOOO0O000O0OOOO [0 ]#line:78
        OOOO0O000OOOOO0O0 =O0O0OOO00000OOOOO .reverseString (OO0OOO00OOOO00O00 )#line:79
        OO0OO00OO00O0OO00 =int (OOOO0O000OOOOO0O0 [:9 ],10 )#line:81
        OO0O0O0OOOO0O0OO0 =int (OOOO0O000OOOOO0O0 ,10 )#line:82
        O0O0OOO00000OOOOO .tokiPrint ("Starting Verification.")#line:84
        O0O0OOO00000OOOOO .tokiPrint ("current__time: "+str (O0000O0000O0OO0OO ))#line:85
        O0O0OOO00000OOOOO .tokiPrint ("toki__time_tocompare: "+str (OO0OO00OO00O0OO00 ))#line:86
        if (O0000O0000O0OO0OO -OO0OO00OO00O0OO00 >O0O0OOO00000OOOOO .life_time_of_toki ):#line:89
            O0O0OOO00000OOOOO .tokiPrint ("Toki is too old!")#line:91
            O0O0OOO00000OOOOO .tokiPrint ("Toki is invalid!")#line:92
            return False #line:93
        else :#line:95
            O0O0OOO00000OOOOO .tokiPrint ("Toki is in _time!")#line:96
            O0OO000OO00O0OOO0 =O0O0OOO00000OOOOO .getSHA (O0O0OOO00000OOOOO .getSHA (O0O0OOO00000OOOOO .clientkey +" - "+OO0O0O0OOOO0O0OO0 +" - "+O0O0OOO00000OOOOO .clientkey ))#line:98
            OOO0O0O0OOOO0OOO0 =O0O0OOO00000OOOOO .reverseString (O0OO000OO00O0OOO0 )#line:99
            OOO0O0O00O0OOOO00 =OOOOOO0O000O0OOOO [1 ]#line:100
            O0O0OOO00000OOOOO .tokiPrint ("Toki given: '"+OOO0O0O00O0OOOO00 +"'")#line:102
            O0O0OOO00000OOOOO .tokiPrint ("Toki reComputed: '"+OOO0O0O0OOOO0OOO0 +"'")#line:103
        if (OOO0O0O00O0OOOO00 .equals (OOO0O0O0OOOO0OOO0 )):#line:105
            O0O0OOO00000OOOOO .tokiPrint ("Toki is valid!")#line:106
            return True #line:107
        else :#line:108
            O0O0OOO00000OOOOO .tokiPrint ("Toki is invalid!")#line:109
            return False 
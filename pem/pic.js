function hex_md5(url){
	var f = {w:"a",k: "b",v: "c",1: "d",j:"e",u: "f",2:"g",i: "h",t: "i",3: "j",h: "k",s: "l",4: "m",g: "n",5: "o",r: "p",q:"q",6: "r",f:"s",p: "t",7: "u",e: "v",o: "w",8: "1",d: "2",n: "3",9: "4",c:"5",m: "6",0: "7",b: "8",l: "9",a: "0",_z2C$q:":","_z&e3B": ".",AzdH3F:"/"   };  
	var h = /(_z2C\$q|_z&e3B|AzdH3F)/g;  
	var s = /([a-w\d])/g; 
	var e = url.replace(h, function(t, e) { return f[e] });  
	return e.replace(s, function(t, e) { return f[e] });  }  
 

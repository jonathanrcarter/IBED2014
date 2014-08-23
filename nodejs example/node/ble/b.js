var bleno = require('bleno');

var uuid = '74278BDAB64445208F0C720EAF059935';		// glimworm
//var uuid = '74278BDAB64445208F0C720EAF059936';	 	// blushrr
var major = 400; // 0x0000 - 0xffff
var minor = 4001; // 0x0000 - 0xffff
var measuredPower = -59; // -128 - 127

bleno.startAdvertisingIBeacon(uuid, major, minor, measuredPower, function(err) {

	if (err) {
		console.log(err);
	} else {
		console.log("advertising");
	}

} );
//bleno.startAdvertisingIBeacon(uuid, major, minor, measuredPower, callback(error));



var noble = require('noble');
var async = require('async');

noble.on('stateChange',function(E) {
	console.log({
		ty : 'statechange',
		E : E
	});
});
noble.on('scanStart', function(E) {

	console.log({
		ty : 'scanstart',
		E : E
	});
});

var P = null;
var PF = null;

noble.on('discover', function(peripheral) {
	console.log({
		ty : 'discover',
		peripheral : peripheral,
		// chars : peripheral._noble._characteristics,
		// services : peripheral._noble._services,
		// bindings : peripheral._noble._bindings,
		// peripherals : peripheral._noble._peripherals
		P : P
	});
	// var chars = peripheral._noble._characteristics;
	// for (var i=0; i < chars.length; i++) {
	// 	var ch = chars[i];
	// 	console.log({
	// 		ty : "characteristic",
	// 		i : i,
	// 		ch : ch
	// 	});

	// }

	var bobby = "BOBBY";
	bobby = "GWB_3_95VVV";
	bobby = "GB432530301";

	if (peripheral.advertisement.localName == bobby) {
		if (PF != null) return;
		PF = "";

		noble.stopScanning(); 

		console.log(bobby+" FOUND");


		explore(peripheral);
		return;


		peripheral.on('connect', function(err) {
			console.log({
				ty : "conect_callback",
				err : err
			});

			// var serviceUUIDs = ["<ffe0>"];
			// var characteristicUUIDs = ["<ffe1>"];
			// peripheral.discoverSomeServicesAndCharacteristics(serviceUUIDs, characteristicUUIDs, function(err, services, characteristics){
			// 	console.log({
			// 		ty : "discoverCharacteristics",
			// 		err : err,
			// 		characteristics : characteristics,
			// 		services : services
			// 	});
			// });

		});
		peripheral.on('servicesDiscover', function(services) {
			console.log({
				ty : "servicesDiscover",
				services : services
			});

		});
		peripheral.on('characteristicsDiscover', function(characteristics) {
			console.log({
				ty : "characteristicsDiscover",
				characteristics : characteristics
			});

			characteristics[0].on('write', true, function(D) {
				console.log({
					ty : "WRITTEN",
					D : D
				});
			});

			var data = "AT+NAME?";
			characteristics[0].write(data, true, function(err){ 
				console.log({
					ty : "WRITE",
					err : err
				});

			});

		});



		peripheral.connect(function(err) {
			console.log({
				ty : "conectfn",
				err : err
			});
			if (P != null) return;

			P = peripheral;


			setTimeout( function() {
				console.log({
					ty : "LOOK FOR SERVICES 1"
				});
				var serviceUUIDs = ["ffe0"];
				var serviceUUIDs = [];
				var characteristicUUIDs = ["ffe1"];
				peripheral.discoverSomeServicesAndCharacteristics(serviceUUIDs, characteristicUUIDs, function(err, services, characteristics){
					console.log({
						ty : "discoverCharacteristics",
						err : err,
						characteristics : characteristics,
						services : services
					});

					characteristics[0].on('write', true, function(D) {
						console.log({
							ty : "WRITTEN",
							D : D
						});
					});

					var data = "AT+NAME?";
					characteristics[0].write(data, true, function(err){ 
						console.log({
							ty : "WRITE",
							err : err
						});

					});



				});

				setTimeout( function() {
					peripheral.disconnect();
					setTimeout( function() {
						noble.startScanning([], true); 
						P = null;
						PF = null;
					},5000);
				},5000);


			},20000);


		// 	setTimeout( function() {
		// 		console.log({
		// 			ty : "LOOK FOR SERVICES 1"
		// 		});

		// 		var serviceUUIDs = ["<ffe0>"];
		// 		console.log({
		// 			ty : "LOOK FOR SERVICES 2"
		// 		});

		// 		var characteristicUUIDs = ["<ffe1>"];

		// 		console.log({
		// 			ty : "LOOK FOR SERVICES 3"
		// 		});
		// 		P.discoverSomeServicesAndCharacteristics(serviceUUIDs, characteristicUUIDs, function(err, services, characteristics){
		// 			console.log({
		// 				ty : "discoverCharacteristics",
		// 				err : err,
		// 				characteristics : characteristics,
		// 				services : services
		// 			});
		// 		});
		// 		console.log({
		// 			ty : "LOOK FOR SERVICES 4"
		// 		});
		// 	},5000);


			// peripheral.disconnect(function(err) {
			// 	console.log({
			// 		ty : "disconnect",
			// 		err : err
			// 	});

			// })
		});
	}
	if (peripheral.advertisement.serviceUuids[0] == "ffe0") {
		console.log("ffe0 found");
	//	E.on('connect', function(P) {
	//		console.log(P);
	//	});
	//	E.connect(function(P) {
	//		console.log(P);
	//	});
	}
});


function explore(peripheral) {
  console.log('services and characteristics:');

  peripheral.on('disconnect', function() {
    process.exit(0);
  });

  peripheral.connect(function(error) {
  	setTimeout(function() {




    peripheral.discoverServices([], function(error, services) {
      var serviceIndex = 0;

      async.whilst(
        function () {
          return (serviceIndex < services.length);
        },
        function(callback) {
          var service = services[serviceIndex];
          var serviceInfo = service.uuid;

          if (service.name) {
            serviceInfo += ' (' + service.name + ')';
          }
          console.log(serviceInfo);

          service.discoverCharacteristics([], function(error, characteristics) {
            var characteristicIndex = 0;

            async.whilst(
              function () {
                return (characteristicIndex < characteristics.length);
              },
              function(callback) {
                var characteristic = characteristics[characteristicIndex];
                var characteristicInfo = '  ' + characteristic.uuid;

                if (characteristic.name) {
                  characteristicInfo += ' (' + characteristic.name + ')';
                }

                async.series([
                  function(callback) {
                    characteristic.discoverDescriptors(function(error, descriptors) {
                      async.detect(
                        descriptors,
                        function(descriptor, callback) {
                          return callback(descriptor.uuid === '2901');
                        },
                        function(userDescriptionDescriptor){
                          if (userDescriptionDescriptor) {
                            userDescriptionDescriptor.readValue(function(error, data) {
                              characteristicInfo += ' (' + data.toString() + ')';
                              callback();
                            });
                          } else {
                            callback();
                          }
                        }
                      );
                    });
                  },
                  function(callback) {
                        characteristicInfo += '\n    properties  ' + characteristic.properties.join(', ');

                    if (characteristic.properties.indexOf('writeWithoutResponse') !== -1) {
                    	console.log("WRITING");
                    	var mockData = new Buffer("AT+VERS?");

                    	characteristic.write(mockData,true,function(error, data) {
	                    	console.log("WRITTEN");
                        	if (data) {
                        	  var string = data.toString('ascii');

                        	  characteristicInfo += '\n    written value       ' + data.toString('hex') + ' | \'' + string + '\'';
                        	}
                        	callback();
                    	});
                    }

                    if (characteristic.properties.indexOf('read') !== -1) {
                      characteristic.read(function(error, data) {
                        if (data) {
                          var string = data.toString('ascii');

                          characteristicInfo += '\n    read value       ' + data.toString('hex') + ' | \'' + string + '\'';
                        }
                        callback();
                      });
                    } else {
                      callback();
                    }
                  },
                  function() {
                    console.log(characteristicInfo);
                    characteristicIndex++;
                    callback();
                  }
                ]);
              },
              function(error) {
                serviceIndex++;
                callback();
              }
            );
          });
        },
        function (err) {
          peripheral.disconnect();
        }
      );
    });
  	
  	},15000);

  });
}


noble.startScanning([], true); 








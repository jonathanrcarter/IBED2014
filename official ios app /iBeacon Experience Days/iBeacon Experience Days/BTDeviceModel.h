//
//  PersonModel.h
//  glimworm_ibeacon
//
//  Created by Jonathan Carter on 22/12/2013.
//  Copyright (c) 2013 Jonathan Carter. All rights reserved.
//

@interface BTDeviceModel : NSObject {
    NSString * uuid;
    NSNumber * major;
    NSNumber * minor;
    NSNumber * proximity;
    double dist;
    NSNumber * rssi;
    long increment;
    

}
@property(retain, readwrite) NSString * uuid;
@property(readwrite) NSNumber * major;
@property(readwrite) NSNumber * minor;
@property(readwrite) NSNumber * rssi;
@property(readwrite) NSNumber * proximity;
@property(readwrite) double dist;
@property(readwrite) long increment;

@end

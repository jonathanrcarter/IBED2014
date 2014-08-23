//
//  PersonModel.m
//  glimworm_ibeacon
//
//  Created by Jonathan Carter on 22/12/2013.
//  Copyright (c) 2013 Jonathan Carter. All rights reserved.
//

#import "BTDeviceModel.h"

@implementation BTDeviceModel

@synthesize uuid;
@synthesize minor;
@synthesize major;
@synthesize rssi;
@synthesize proximity;
@synthesize dist;




-(void) dealloc {
//    [name release];
//    [occupation release];
//    [super dealloc];
}

@end

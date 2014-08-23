//
//  FirstViewController.h
//  iBeacon Experience Days
//
//  Created by Jonathan Carter on 20/08/2014.
//  Copyright (c) 2014 Glimworm Beacons. All rights reserved.
//

#import <UIKit/UIKit.h>

@import CoreLocation;
@import CoreBluetooth;

@interface FirstViewController : UIViewController <CLLocationManagerDelegate, CBPeripheralManagerDelegate, CBCentralManagerDelegate, CBPeripheralDelegate, UIApplicationDelegate>
- (IBAction)whazam:(id)sender;
@property (weak, nonatomic) IBOutlet UIWebView *webview;

@end

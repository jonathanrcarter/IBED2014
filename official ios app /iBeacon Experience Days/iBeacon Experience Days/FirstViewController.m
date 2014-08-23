//
//  FirstViewController.m
//  iBeacon Experience Days
//
//  Created by Jonathan Carter on 20/08/2014.
//  Copyright (c) 2014 Glimworm Beacons. All rights reserved.
//

#import "FirstViewController.h"
#import "ShapeView.h"
#import "BTDeviceModel.h"

@interface FirstViewController ()
@property (nonatomic, strong) CLLocationManager *locationManager;
@property (nonatomic, strong) CLBeaconRegion *beaconRegion;
@property (nonatomic, strong) CBPeripheralManager *peripheralManager;



@end

@implementation FirstViewController
@synthesize webview;


static NSString * const kUUID = @"74278bda-b644-4520-8f0c-720eaf059935";
static NSString * const kIdentifier = @"SomeIdentifier";


- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    [self createLocationManager];
    [self createBeaconRegion];
    [self turnOnMonitoring];
    [self turnOnRanging];

    self.peripheralManager = [[CBPeripheralManager alloc] initWithDelegate:self queue:nil options:nil];
    
    
    //webview.opaque = NO;
   // webview.backgroundColor = [UIColor clearColor];
    [webview loadHTMLString:@"<html><body style='background-color: transparent;'></body></html>" baseURL:nil];

    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Common
- (void)createBeaconRegion
{
    if (self.beaconRegion)
        return;
    
    NSUUID *proximityUUID = [[NSUUID alloc] initWithUUIDString:kUUID];
    self.beaconRegion = [[CLBeaconRegion alloc] initWithProximityUUID:proximityUUID identifier:kIdentifier];
    self.beaconRegion.notifyEntryStateOnDisplay = YES;
}

- (void)createLocationManager
{
    if (!self.locationManager) {
        self.locationManager = [[CLLocationManager alloc] init];
        self.locationManager.delegate = self;
    }
}
- (void)peripheralManagerDidUpdateState:(CBPeripheralManager *)peripheralManager
{
    if (peripheralManager.state != CBPeripheralManagerStatePoweredOn) {
        NSLog(@"Peripheral manager is off.");
        return;
    }
    
    NSLog(@"Peripheral manager is on.");
}

#pragma ranging
- (void)turnOnRanging
{
    NSLog(@"Turning on ranging...");
    
    if (![CLLocationManager isRangingAvailable]) {
        NSLog(@"Couldn't turn on ranging: Ranging is not available.");
        return;
    }
    
    if (self.locationManager.rangedRegions.count > 0) {
        NSLog(@"Didn't turn on ranging: Ranging already on.");
        return;
    }
    
    [self createBeaconRegion];
    [self.locationManager startRangingBeaconsInRegion:self.beaconRegion];
    
    NSLog(@"Ranging turned on for region: %@.", self.beaconRegion);
}

- (void)stopRangingForBeacons
{
    if (self.locationManager.rangedRegions.count == 0) {
        NSLog(@"Didn't turn off ranging: Ranging already off.");
        return;
    }
    
    [self.locationManager stopRangingBeaconsInRegion:self.beaconRegion];
    
    NSLog(@"Turned off ranging.");
}

#pragma Monitoring
- (void)turnOnMonitoring
{
    NSLog(@"Turning on monitoring...");
    
    if (![CLLocationManager isMonitoringAvailableForClass:[CLBeaconRegion class]]) {
        NSLog(@"Couldn't turn on region monitoring: Region monitoring is not available for CLBeaconRegion class.");
        return;
    }
    
    [self createBeaconRegion];
    [self.locationManager startMonitoringForRegion:self.beaconRegion];
    
    NSLog(@"Monitoring turned on for region: %@.", self.beaconRegion);
}

- (void)stopMonitoringForBeacons
{
    [self.locationManager stopMonitoringForRegion:self.beaconRegion];
    
    NSLog(@"Turned off monitoring");
}


- (void)locationManager:(CLLocationManager *)manager didDetermineState:(CLRegionState)state forRegion:(CLRegion *)region
{
    NSString *stateString = nil;
    switch (state) {
        case CLRegionStateInside:
            stateString = @"inside";
            break;
        case CLRegionStateOutside:
            stateString = @"outside";
            break;
        case CLRegionStateUnknown:
            stateString = @"unknown";
            break;
    }
    NSLog(@"State changed to %@ for region %@.", stateString, region);
}

- (void)locationManager:(CLLocationManager *)manager didEnterRegion:(CLRegion *)region
{
    NSLog(@"Entered region: %@", region);
    NSString *url = [NSString stringWithFormat:@"http://jon651.glimworm.com/ibeacon/api-expdays.php?action=enter&region=%@", region];
    NSString *s = [self stringWithUrl:[NSURL URLWithString:url]];
    NSLog(@" html %@",s);

    [self sendLocalNotificationForBeaconRegion:(CLBeaconRegion *)region];
}

- (void)locationManager:(CLLocationManager *)manager didExitRegion:(CLRegion *)region
{
    NSLog(@"Exited region: %@", region);
    NSString *url = [NSString stringWithFormat:@"http://jon651.glimworm.com/ibeacon/api-expdays.php?action=exit&region=%@", region];
    NSString *s = [self stringWithUrl:[NSURL URLWithString:url]];
    NSLog(@" html %@",s);


}

NSString *St = @"";
NSMutableArray *Beacons = nil;
long inc = 0;

- (void)locationManager:(CLLocationManager *)manager
        didRangeBeacons:(NSArray *)beacons
               inRegion:(CLBeaconRegion *)region {
    inc++;

    NSLog(@"didRangeBeacons : region : %@",region);
    NSLog(@"didRangeBeacons : beacons : %@",beacons);
    NSLog(@"didRangeBeacons : inc : %ld",inc);
    
    if (Beacons == nil) {
        Beacons = [[NSMutableArray alloc]init];
    }
    
    for (int index = 0; index < [beacons count]; index++) {
        CLBeacon *b = [beacons objectAtIndex:index];
        bool found = false;
        for (int index1 = 0; index1 < [Beacons count]; index1++) {
            BTDeviceModel *b1 = [Beacons objectAtIndex:index1];
            
            if ([b.major isEqualToNumber:b1.major]) {
                if ([b.minor isEqualToNumber:b1.minor]) {
                    // this is the same beacon
                    found = true;
                    if (b.accuracy > 0) {
                        b1.dist = (b1.dist * 0.75) + (b.accuracy * 0.25);
                        b1.rssi = [NSNumber numberWithInteger:b.rssi];
                        b1.proximity = [NSNumber numberWithInt:b.proximity];
                        b1.increment = inc;
                    }
                }
            }
        }
        if (found == false) {
            BTDeviceModel * pm = [[BTDeviceModel alloc] init];
            pm.uuid = [b.proximityUUID UUIDString];
            pm.major = b.major;
            pm.minor = b.minor;
            pm.dist = b.accuracy;
            pm.rssi = [NSNumber numberWithInteger:b.rssi];
            pm.proximity = [NSNumber numberWithInt:b.proximity];
            pm.increment = inc;
            [Beacons addObject:pm];
        }
    }
    
    
    St = @"";
    for (int index = 0; index < [Beacons count]; index++) {
        BTDeviceModel *b = [Beacons objectAtIndex:index];
        NSString *BEACON = [NSString stringWithFormat:@"%@,%@,%@,%ld,%f,%@,%ld", b.uuid, b.major, b.minor, (long)b.rssi, b.dist, b.proximity, (inc - b.increment)];
        if ((inc - b.increment) < 10) {
            St =[NSString stringWithFormat:@"%@:%@", St , BEACON];
        }
    }
//    for (int index = 0; index < [beacons count]; index++) {
//        CLBeacon *b = [beacons objectAtIndex:index];
//        NSString *BEACON = [NSString stringWithFormat:@"%@,%@,%@,%ld,%f,%d", [b.proximityUUID UUIDString], b.major, b.minor, (long)b.rssi, b.accuracy, b.proximity];
//        St =[NSString stringWithFormat:@"%@:%@", St , BEACON];
//    }
    
}

#pragma mark - Local notifications
- (void)sendLocalNotificationForBeaconRegion:(CLBeaconRegion *)region
{
    UILocalNotification *notification = [UILocalNotification new];
    
    // Notification details
    notification.alertBody = [NSString stringWithFormat:@"Entered beacon region for UUID: %@",
                              region.proximityUUID.UUIDString];   // Major and minor are not available at the monitoring stage
    notification.alertAction = NSLocalizedString(@"View Details", nil);
    notification.soundName = UILocalNotificationDefaultSoundName;
    
    [[UIApplication sharedApplication] presentLocalNotificationNow:notification];
}

#pragma whazam...




- (NSString *)stringWithUrl:(NSURL *)url
{
    NSURLRequest *urlRequest = [NSURLRequest requestWithURL:url
                                                cachePolicy:NSURLRequestReturnCacheDataElseLoad
                                            timeoutInterval:30];
    // Fetch the JSON response
    NSData *urlData;
    NSURLResponse *response;
    NSError *error;
    
    // Make synchronous request
    urlData = [NSURLConnection sendSynchronousRequest:urlRequest
                                    returningResponse:&response
                                                error:&error];
    
    NSLog(@"DATA [%@]",urlData);
    
    // Construct a String around the Data from the response
    return [[NSString alloc] initWithData:urlData encoding:NSUTF8StringEncoding];
}

- (void)stopAdvertisingBeacon
{
    [self.peripheralManager stopAdvertising];
    
    NSLog(@"Turned off advertising.");
}

- (void)turnOnAdvertising
{
    if (self.peripheralManager.state != CBPeripheralManagerStatePoweredOn) {
        NSLog(@"Peripheral manager is off.");
        return;
    }
    
    time_t t;
    srand((unsigned) time(&t));
    CLBeaconRegion *region = [[CLBeaconRegion alloc] initWithProximityUUID:self.beaconRegion.proximityUUID
                                                                     major: 9001
                                                                     minor: 9002
                                                                identifier:self.beaconRegion.identifier];
    NSDictionary *beaconPeripheralData = [region peripheralDataWithMeasuredPower:nil];
    [self.peripheralManager startAdvertising:beaconPeripheralData];
    
    NSLog(@"Turning on advertising for region: %@.", region);
    
}



- (void)kazam
{

    NSString *dname = [[UIDevice currentDevice] name];
    
    NSString * unescapedQuery = [[NSString alloc] initWithFormat:@"?action=array&array=%@&dname=%@", St, dname];
    NSString * escapedQuery = [unescapedQuery stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]];
    NSString * url = [[NSString alloc] initWithFormat:@"http://jon651.glimworm.com/ibeacon/api-expdays.php%@", escapedQuery];
    
    //    NSString *url = [NSString stringWithFormat:@"http://jon651.glimworm.com/ibeacon/api.php?action=array&array=%@", St];
    NSLog(@" url %@",url);
    NSString *s = [self stringWithUrl:[NSURL URLWithString:url]];
    NSLog(@" html %@",s);
    [webview loadHTMLString:s baseURL:nil];

}

- (IBAction)whazam:(id)sender {
    
    [self performSelector:@selector(kazam) withObject:self afterDelay:1.0];
    
}
- (IBAction)startadvertising:(id)sender {
    [self turnOnAdvertising];
}

- (IBAction)stopadvertising:(id)sender {
    [self stopAdvertisingBeacon];
}
@end

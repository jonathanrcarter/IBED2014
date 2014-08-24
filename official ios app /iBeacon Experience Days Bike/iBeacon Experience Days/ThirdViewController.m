//
//  ThirdViewController.m
//  iBeacon Experience Days
//
//  Created by Jonathan Carter on 20/08/2014.
//  Copyright (c) 2014 Glimworm Beacons. All rights reserved.
//

#import "ThirdViewController.h"

@interface ThirdViewController ()

@end

@implementation ThirdViewController
@synthesize webview;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.

    [self reload];
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)reload
{
    
    int i = rand()%10000+1;
    
    NSString * url = [[NSString alloc] initWithFormat:@"http://jon651.glimworm.com/ibeacon/api-expdays.php?action=floorplan&_i=%d",i];
    
    NSString *s = [self stringWithUrl:[NSURL URLWithString:url]];
    [webview loadHTMLString:s baseURL:nil];

}

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

- (IBAction)reload:(id)sender {
    [self reload];

}
@end

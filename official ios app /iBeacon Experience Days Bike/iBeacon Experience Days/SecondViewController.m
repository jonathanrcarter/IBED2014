//
//  SecondViewController.m
//  iBeacon Experience Days
//
//  Created by Jonathan Carter on 20/08/2014.
//  Copyright (c) 2014 Glimworm Beacons. All rights reserved.
//

#import "SecondViewController.h"

@interface SecondViewController ()

@end

@implementation SecondViewController
@synthesize webview;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.

//    [webview loadHTMLString:@"<html><body>YOUR-TEXT-HERE</body></html>" baseURL:nil];

    
    NSURL *htmlFile = [NSURL fileURLWithPath:[[NSBundle mainBundle] pathForResource:@"secondview" ofType:@"html"] isDirectory:NO];
    [webview loadRequest:[NSURLRequest requestWithURL:htmlFile]];
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end

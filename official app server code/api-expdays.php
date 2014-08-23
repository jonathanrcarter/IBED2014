<?php

error_log("BEACON API");
error_log(print_r($_GET,true));
error_log(print_r($_POST,true));
error_log($HTTP_RAW_POST_DATA);


error_reporting(9);

//include_once("ibeacon.php");

class obj {
}
$action = $_GET["action"];


if ($action == "array") {

	$arr = $_GET["array"];
	$dname = $_GET["dname"];
	
	$arry = array();
	
	$h = "";
	$h2 = "";


	if (!empty($arr) && $arr) {
		$ar = explode(":",$arr);
		
		foreach ($ar as $a) {
			if ($a) {
				$b = explode(",",$a);
				if (count($b) > 4) {
					$beacon = array(
						'uuid' => $b[0],
						'major' => intval($b[1]),
						'minor' => intval($b[2]),
						'rssi' => doubleval($b[3]),
						'dist' => doubleval($b[4]),
						'prox' => intval($b[5])
					);
					array_push($arry, $beacon);
				}
			}
		}
		function cmp($a, $b) {
			if ($a["major"] == $b["major"]) {
				if ($a["dist"] == $b["dist"]) return 0;
				if ($a["dist"] < $b["dist"]) return -1;
				return 1;
			}
			if ($a["major"] > $b["major"]) return -1;
			return 1;
			
		}
		usort($arry, "cmp");
		
		foreach ($arry as $beacon) {
					
			$h2 .= "<tr><td>".$beacon["major"]."</td><td>".$beacon["minor"]."</td><td>".$beacon["dist"]."</td><td>".$beacon["prox"]."</td></tr>";
//			if (($beacon["prox"] > 0 && $beacon["prox"] < 3) || ($beacon["dist"] > 0 && $beacon["dist"] < 20)) {
			$match = true;
			if ($beacon["dist"] > 0 && $beacon["dist"] < 6) {
				if ($beacon["major"] == 900 && $beacon["minor"] == 9001) {
					$h .= "<p class='q'>You are probably near this suspicious looking ruffian!!!!!</p>";
					$h .= "<p>
					<img src='http://www.glimworm.com/wp-content/uploads/2014/02/jonathan-carter.jpg'>
					<h3>Jonathan Carter</h3>
					<h4>Co-Founder of Glimworm Beacons</h4>
					</p>";
					$h .= "<div>Major : ".$beacon["major"]."</div>";
					$h .= "<div>Minor : ".$beacon["minor"]."</div>";
				}

				else if ($beacon["major"] == 900 && $beacon["minor"] == 9011) {
					$h .= "<p class='q'>You are probably near this suspicious looking ruffian!!!!!</p>";
					$h .= "<p>
					<img src='http://www.dish2013.nl/uploads/shared/images/scaled/speaker_face_large/413'>
					<h3>Paul Manwaring</h3>
					<h4>Co-Founder of Glimworm Beacons</h4>
					</p>";
					$h .= "<div>Major : ".$beacon["major"]."</div>";
					$h .= "<div>Minor : ".$beacon["minor"]."</div>";

				}
				else if ($beacon["major"] == 900 && $beacon["minor"] == 9021) {
					$h .= "<p class='q'>You are probably near this suspicious looking ruffian!!!!!</p>";
					$h .= "<p>
					<img src='http://glimwormbeacons.com/wp-content/uploads/2014/01/website_photo_sven-120.jpg'>
					<h3>Sven-Erik Haitjema</h3>
					<h4>Co-Founder of Glimworm Beacons<br>Embedded Systems Engineer</h4>
					<p>Please visit me with questions about Beacons, Programming, and the Event :)</p>
					
					</p>";
					$h .= "<div>Major : ".$beacon["major"]."</div>";
					$h .= "<div>Minor : ".$beacon["minor"]."</div>";
				}
				else if ($beacon["major"] == 900 && $beacon["minor"] == 9031) {
					$h .= "<p class='q'>You are probably near this suspicious looking ruffian!!!!!</p>";
					$h .= "<p>
					<img src='http://www.tweetonig.nl/home/wp-content/uploads/2013/05/John-Tweetonig-2.png'>
					<h3>John Tillema</h3>
					<h4>Co-Founder of Glimworm Beacons</h4>
					</p>";
					$h .= "<div>Major : ".$beacon["major"]."</div>";
					$h .= "<div>Minor : ".$beacon["minor"]."</div>";
				}
				else if ($beacon["major"] == 900 && $beacon["minor"] == 9041) {
					$h .= "<p class='q'>You are probably near this suspicious looking ruffian!!!!!</p>";
					$h .= "<p>
					<img src='http://www.tweetonig.nl/home/wp-content/uploads/2013/05/dimer-tweetonig-2.png'>
					<h3>Dimer Schaefer</h3>
					<h4>Co-Founder of Glimworm Beacons</h4>
					</p>";
					$h .= "<div>Major : ".$beacon["major"]."</div>";
					$h .= "<div>Minor : ".$beacon["minor"]."</div>";
				}

				else if ($beacon["major"] == 900 && $beacon["minor"] == 9051) {
					$h .= "<p class='q'>You are probably near this suspicious looking ruffian!!!!!</p>";
					$h .= "<p>
					<img src='https://media.licdn.com/media/p/4/005/06f/1a2/2de0640.jpg'>
					<h3>Bas van Dijen</h3>
					<h4>Gamification consultant,<br> Event assistant</h4>
					<p>Please visit me for information about Gamification, User interface Design and interaction design.</p>
					</p>";
					$h .= "<div>Major : ".$beacon["major"]."</div>";
					$h .= "<div>Minor : ".$beacon["minor"]."</div>";
				}
				
				else if ($beacon["major"] == 900 && $beacon["minor"] == 9071) {
					$h .= "<p class='q'>You are probably near this suspicious looking ruffian!!!!!</p>";
					$h .= "<p>
					<img src='https://i1.sndcdn.com/avatars-000029953851-b31p3k-t200x200.jpg?e76cf77'>
					<h3>Wim Clasquin</h3>
					<h4>ICT consultant,<br> Event assistant</h4>
					<p>Please visit me for information about Network Architectures, VOIP.</p>
					</p>";
					$h .= "<div>Major : ".$beacon["major"]."</div>";
					$h .= "<div>Minor : ".$beacon["minor"]."</div>";
				}
				
				
				
				else if ($beacon["major"] == 900 && $beacon["minor"] == 9101) {
					$h .= "<p class='q'>You are probably near this suspicious looking ruffian!!!!!</p>";
					$h .= "<p>
					<img src='http://m.c.lnkd.licdn.com/mpr/mpr/shrink_200_200/p/3/005/067/049/01c3cf9.jpg'>
					<h3>Lassi Kurkijärvi</h3>
					<h4>Director Innovation, SanomaLab</h4>
					<h5>Sanoma Digital</h5>
<p>Lassi believes great leadership can change the world. As Director of Sanoma's Innovation Lab, his goal is to create conditions where employees can do awesome things: build new business, improve the lives of people and solve problems for a finer world. Running in-house accelerators, adapting startup methods to corporate environments by sponsoring entrepreneur teams and promoting an all-round entrepreneurial attitude are his key strengths.</p>

<p>Previously he has managed innovation programs, development teams, numerous projects and led task forces aimed at creating brilliant services. Agile methods, smart strategies and inspirational leadership are his keys to success. A specialist in project management, all things digital & service innovation, Lassi also understands the dynamics of online communities, professional and user-created content, and has strong experience in motivating teams to work towards a common goal.</p>

<p>Additionally Lassi loves giving inspirational presentations.</p>

<p>Currently Lassi's goal is to help Sanoma transform into an even more digital media & learning company as part of Sanoma Digital's Leadership Team.</p>					
					</p>";
					$h .= "<div>Major : ".$beacon["major"]."</div>";
					$h .= "<div>Minor : ".$beacon["minor"]."</div>";
				}
				else if ($beacon["major"] == 900 && $beacon["minor"] == 9111) {
					$h .= "<p class='q'>You are probably near this suspicious looking ruffian!!!!!</p>";
					$h .= "<p>
					<img src='https://media.licdn.com/media/p/2/000/1ab/17b/223c27f.jpg'>
					<h3>Victo Beerthuis </h3>
					<h4>Head of Mobile</h4>
					<h5>Sanoma Digital / Nu.nl</h5>
					</p>";
					$h .= "<div>Major : ".$beacon["major"]."</div>";
					$h .= "<div>Minor : ".$beacon["minor"]."</div>";
				}
				else if ($beacon["major"] == 900 && $beacon["minor"] == 9121) {
					$h .= "<p class='q'>You are probably near this rather elegant lady :-)</p>";
					$h .= "<p>
					<img src='https://media.licdn.com/mpr/mpr/shrink_240_240/p/2/005/045/3b3/291afee.jpg'>
					<h3>Tara </h3>
					<h4>Chief ommunity Officer</h4>
					<h5>Appsterdam</h5>
<p>Tara Ross is a strategist, producer, and consultant based in Amsterdam, Netherlands. As an American expat who has also lived and worked in Texas, the UK, and Ireland, Tara is on a mission to promote global best practices in technology, urban design, and food culture. </p>

<p>After a corporate career in interactive media, Tara moved to Amsterdam in 2011 and fell in love with the cycling lifestyle there. She is active in the tech community as Chief Community Officer of Appsterdam, an international organization that supports the app-making ecosystem. </p>

<p>Through her consultancy, Tara is working on a variety of initiatives to accelerate business innovation and startups. She is currently developing Cityshifters, an organization dedicated to promoting good urban design through media and pop culture, and she is currently launching several hackathon events.</p>

<p>Tara regularly speaks at conferences, meetups, and other events, and she is active in the startup community as a mentor and coach.</p>

<p>On www.noobtools.com she writes about the projects she's working on, and the intersection of media, technology, culture, and business.</p>
					</p>";
					$h .= "<div>Major : ".$beacon["major"]."</div>";
					$h .= "<div>Minor : ".$beacon["minor"]."</div>";
				}
				else if ($beacon["major"] == 900 && $beacon["minor"] == 9131) {
					$h .= "<p class='q'>You are probably near this suspicious looking ruffian!!!!!</p>";
					$h .= "<p>
					<h3>Martinus </h3>
					<h5>Appsterdam</h5>
					</p>";
					$h .= "<div>Major : ".$beacon["major"]."</div>";
					$h .= "<div>Minor : ".$beacon["minor"]."</div>";
				}
				else if ($beacon["major"] == 900 && $beacon["minor"] == 9141) {
					$h .= "<p class='q'>You are probably near this suspicious looking ruffian!!!!!</p>";
					$h .= "<p>
					<h3>Peter M </h3>
					<h5>Vodafone</h5>
					</p>";
					$h .= "<div>Major : ".$beacon["major"]."</div>";
					$h .= "<div>Minor : ".$beacon["minor"]."</div>";
				}

				else if ($beacon["major"] == 900 && $beacon["minor"] == 9151) {
					$h .= "<p class='q'>You are probably near this suspicious looking ruffian!!!!!</p>";
					$h .= "<p>
					<h3>FLOAT M </h3>
					</p>";
					$h .= "<div>Major : ".$beacon["major"]."</div>";
					$h .= "<div>Minor : ".$beacon["minor"]."</div>";
				}


				else if ($beacon["major"] == 900 && $beacon["minor"] == 9201) {
					$h .= "<p class='q'>You should see some Information from </p>";
					$h .= "<p>
					<h3>Europeana </h3>
					</p>";
					$h .= "<div>Major : ".$beacon["major"]."</div>";
					$h .= "<div>Minor : ".$beacon["minor"]."</div>";
				}

				else if ($beacon["major"] == 900 && $beacon["minor"] == 9301) {
					$h .= "<p class='q'>You should see some Information from </p>";
					$h .= "<p>
					<h3>Belastingdienst </h3>
					</p>";
					$h .= "<div>Major : ".$beacon["major"]."</div>";
					$h .= "<div>Minor : ".$beacon["minor"]."</div>";
				}
						
						
						
				else if ($beacon["major"] == 400) {
					$h .= "<p class='q'>You are probably in or near this room </p>";
					$h .= "<h2>Media Bar</h2>";
					$h .= "<div style='width:300px;height:270px; backgroundColor : #ffffff;overflow:hidden;'><img style='position:relative;width:700px;top:-75px;left:-250px;' src='http://jon651.glimworm.com/ibeacon/images/vondelcslayout.png'></div>";
					$h .= "<div>Major : 400</div>";
					$h .= "<div>Minors : 4001, 4002</div>";
				}
						
						
				else if ($beacon["major"] == 100) {
					$h .= "<p class='q'>You are probably in or near this room </p>";
					$h .= "<h2>Back room</h2>";
					$h .= "<div style='width:300px;height:270px; backgroundColor : #ffffff; overflow:hidden;'><img style='position:relative;width:700px;top:0px;left:-250px;' src='http://jon651.glimworm.com/ibeacon/images/vondelcslayout.png'></div>";
					$h .= "<div>Major : 100</div>";
					$h .= "<div>Minors : 1000, 1001, 1002</div>";
				}
				else if ($beacon["major"] == 200) {
					$h .= "<p class='q'>You are probably in or near this room </p>";
					$h .= "<h2>Studio A</h2>";
					$h .= "<div style='width:300px;height:270px; backgroundColor : #ffffff;overflow:hidden;'><img style='position:relative;width:700px;top:-30px; left:-100px;' src='http://jon651.glimworm.com/ibeacon/images/vondelcslayout.png'></div>";
					$h .= "<div>Major : 200</div>";
					$h .= "<div>Minors : 2001, 2002</div>";
				}
				else if ($beacon["major"] == 300) {
					$h .= "<p class='q'>You are probably in or near this room </p>";
					$h .= "<h2>Studio B</h2>";
					$h .= "<div style='width:300px;height:270px; backgroundColor : #ffffff;overflow:hidden;'><img style='position:relative;width:700px;top:-130px;left:-350px;' src='http://jon651.glimworm.com/ibeacon/images/vondelcslayout.png'></div>";
					$h .= "<div>Major : 300</div>";
					$h .= "<div>Minors : 3001, 3002</div>";

				}

				else if ($beacon["major"] == 500) {
					$h .= "<p class='q'>You are probably in or near this room </p>";
					$h .= "<h2>Franse Kamer</h2>";
					$h .= "<div style='width:300px;height:270px; backgroundColor : #ffffff;overflow:hidden;'><img style='position:relative;width:700px;top:-30px;left:-450px;' src='http://jon651.glimworm.com/ibeacon/images/vondelcslayout.png'></div>";
					$h .= "<div>Major : 500</div>";
					$h .= "<div>Minors : 5001, 5002</div>";

				}
				else if ($beacon["major"] == 600) {
					$h .= "<p class='q'>You are probably in or near this room </p>";
					$h .= "<h2>VIP AREA</h2>";
					$h .= "<img width='250' src='http://jon651.glimworm.com/ibeacon/images/vondelcs3d.jpg'>";
					$h .= "<div>Major : 600</div>";
					$h .= "<div>Minors : 6002</div>";
				}
				else {
				 $match=false;
				}
				if($match) $h .= "<hr>";


			}

		}
	}
	
	$retval = new obj();
	$retval->status = 0;
	$retval->arr = $arr;
	$retval->arry = $arry;
	$retval->dname = $dname;
	$retval->status_msg = "array processed";
	$rv = json_encode($retval, JSON_HEX_TAG | JSON_HEX_APOS | JSON_HEX_QUOT | JSON_HEX_AMP | JSON_UNESCAPED_UNICODE);

	if ($h == "") {
		$h = "<p>You poor soul, you can't be at the hackathon because we can't find any beacons</p>";
		$h .= "<img width='250' src='http://jon651.glimworm.com/ibeacon/images/vondelcs3d.jpg'>";
	}
	
	if ($h2 != "") {
		$h .= "<br><br><h3>List of detected beacons</h3><table width='100%'>
		<tr><th>Major</th><th>Minor</th><th>Distance</th><th>Proximity</th></tr>".$h2."</table>";
	}
	/*
	$h .= "<br>";
	$h .= "<h2>Studio A</h2>";
	$h .= "<div style='width:300px;height:270px; backgroundColor : #ffffff;overflow:hidden;'><img style='position:relative;width:700px;top:-30px; left:-100px;' src='http://jon651.glimworm.com/ibeacon/images/vondelcslayout.png'></div>";
	$h .= "<div>Major : 200</div>";
	$h .= "<div>Minors : 2001, 2002</div>";

	$h .= "<h2>Media Bar</h2>";
	$h .= "<div style='width:300px;height:270px; backgroundColor : #ffffff;overflow:hidden;'><img style='position:relative;width:700px;top:-75px;left:-250px;' src='http://jon651.glimworm.com/ibeacon/images/vondelcslayout.png'></div>";
	$h .= "<div>Major : 400</div>";
	$h .= "<div>Minors : 4001, 4002</div>";

	$h .= "<h2>Studio B</h2>";
	$h .= "<div style='width:300px;height:270px; backgroundColor : #ffffff;overflow:hidden;'><img style='position:relative;width:700px;top:-130px;left:-350px;' src='http://jon651.glimworm.com/ibeacon/images/vondelcslayout.png'></div>";
	$h .= "<div>Major : 300</div>";
	$h .= "<div>Minors : 3001, 3002</div>";

	$h .= "<h2>Back room</h2>";
	$h .= "<div style='width:300px;height:270px; backgroundColor : #ffffff; overflow:hidden;'><img style='position:relative;width:700px;top:0px;left:-250px;' src='http://jon651.glimworm.com/ibeacon/images/vondelcslayout.png'></div>";
	$h .= "<div>Major : 100</div>";
	$h .= "<div>Minors : 1000, 1001, 1002</div>";

	$h .= "<br>";
	*/
	
	if ($h != "") {
		print ("<html>
		<head>
		<style>
		* {
			font-family : Comfortaa, Arial;
			border : 0,
			padding : 0,
			margin : 0;
		}
		h2 {
		
		}
		img {
			border : 1px solid #dddddd;
		}
		.q {
			font-size : 2em;
			
		}
		</style>
		</head>
		<body>
		$h
		</body>
		</html>");
	} else {
		echo $rv;
	}
	exit;
}

if ($action == "floorplan2") {
		$h = "<img src='http://jon651.glimworm.com/ibeacon/images/vondelcslayout2.jpg'>";
		print ("<html>
		<head>
		</head>
		<body>
		$h
		</body>
		</html>");
		exit;

}

if ($action == "floorplan") {
	$h = "";
	$h .= "<h1>Installed Beacons</h1>";
	$h .= "<img style='margin-top:150px;' width='250' src='http://jon651.glimworm.com/ibeacon/images/vondelcs3d.jpg'>";
	$h .= "<br>";
	$h .= "<h2>Studio A</h2>";
	$h .= "<div style='width:300px;height:270px; backgroundColor : #ffffff;overflow:hidden;'><img style='position:relative;width:700px;top:-30px; left:-100px;' src='http://jon651.glimworm.com/ibeacon/images/vondelcslayout.png'></div>";
	$h .= "<div>Major : 200</div>";
	$h .= "<div>Minors : 2001, 2002</div>";

	$h .= "<h2>Media Bar</h2>";
	$h .= "<div style='width:300px;height:270px; backgroundColor : #ffffff;overflow:hidden;'><img style='position:relative;width:700px;top:-75px;left:-250px;' src='http://jon651.glimworm.com/ibeacon/images/vondelcslayout.png'></div>";
	$h .= "<div>Major : 400</div>";
	$h .= "<div>Minors : 4001, 4002</div>";

	$h .= "<h2>Studio B</h2>";
	$h .= "<div style='width:300px;height:270px; backgroundColor : #ffffff;overflow:hidden;'><img style='position:relative;width:700px;top:-130px;left:-350px;' src='http://jon651.glimworm.com/ibeacon/images/vondelcslayout.png'></div>";
	$h .= "<div>Major : 300</div>";
	$h .= "<div>Minors : 3001, 3002</div>";

	$h .= "<h2>Back room</h2>";
	$h .= "<div style='width:300px;height:270px; backgroundColor : #ffffff; overflow:hidden;'><img style='position:relative;width:700px;top:0px;left:-250px;' src='http://jon651.glimworm.com/ibeacon/images/vondelcslayout.png'></div>";
	$h .= "<div>Major : 100</div>";
	$h .= "<div>Minors : 1000, 1001, 1002</div>";
	$h .= "<img src='http://jon651.glimworm.com/ibeacon/images/vondelcslayout2.jpg'>";
	
		print ("<html>
		<head>
		</head>
		<body>
		$h
		</body>
		</html>");
		exit;


}




$retval = new obj();
$retval->status = -1;
$retval->status_msg = "action ".$action." is not an action of the api";
echo json_encode($retval, JSON_HEX_TAG | JSON_HEX_APOS | JSON_HEX_QUOT | JSON_HEX_AMP | JSON_UNESCAPED_UNICODE);




DROP TABLE IF EXISTS room;

CREATE TABLE room (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `price` int(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `room_name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

INSERT INTO room VALUES (1,500,4,'101','Single'),(2,1000,5,'201','Double'),(3,3000,5,'301','Deluxe'),
(4,1000,5,'202','Double');

DROP TABLE IF EXISTS user;


CREATE TABLE user (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

INSERT INTO user VALUES (1,'james','james@gmail.com'),(2,'Adam','adam@yahoo.com');

DROP TABLE IF EXISTS pricedetails;
CREATE TABLE pricedetails (
	`id` bigint(20) NOT NULL AUTO_INCREMENT ,
	`room_id` bigint(20) DEFAULT NULL,
	`price` int(11) DEFAULT NULL ,
	`from_date` datetime DEFAULT NULL ,
	`to_date` datetime DEFAULT NULL,
	`room_type` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`id`)
)ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;


INSERT INTO pricedetails VALUES(1, 2, 2000 , '2020-08-20 00:00:00','2020-08-20 00:00:00','Double');





CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user` (
  `id` varchar(50) NOT NULL DEFAULT '',
  `rating` int(11) DEFAULT 0,
  `location` varchar(100) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `item` (
  `id` bigint(20) NOT NULL,
  `name` varchar(250) NOT NULL,
  `currently` float DEFAULT NULL,
  `buy_price` float DEFAULT NULL,
  `first_bid` float DEFAULT NULL,
  `num_bids` int(11) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `lon` float DEFAULT NULL,
  `lat` float DEFAULT NULL,
  `started` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ends` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `description` varchar(4000) DEFAULT NULL,
  `seller_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `seller_id` (`seller_id`),
  CONSTRAINT `item_ibfk_1` FOREIGN KEY (`seller_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `item_category` (
  `item_id` bigint(20) NOT NULL,
  `category_id` int(11) NOT NULL,
  KEY `item_id` (`item_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `item_category_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE,
  CONSTRAINT `item_category_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

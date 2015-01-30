LOAD DATA LOCAL INFILE 'category.dat'
INTO TABLE `category`
  FIELDS TERMINATED BY ' |*| '
  LINES TERMINATED BY "\n"
  (id, name);

LOAD DATA LOCAL INFILE 'users.dat'
INTO TABLE `user`
  FIELDS TERMINATED BY ' |*| '
  LINES TERMINATED BY "\n"
  (id, rating, location, country);

LOAD DATA LOCAL INFILE 'items.dat'
INTO TABLE `item`
  FIELDS TERMINATED BY ' |*| '
  LINES TERMINATED BY "\n"
  (id, name, currently, buy_price,
  first_bid, num_bids, location, country,
  lon, lat, started, ends, description,
  seller_id);

LOAD DATA LOCAL INFILE 'bids.dat'
INTO TABLE `bid`
  FIELDS TERMINATED BY ' |*| '
  LINES TERMINATED BY "\n"
  (item_id, bidder_id, price, time);

LOAD DATA LOCAL INFILE 'item_category.dat'
INTO TABLE `item_category`
  FIELDS TERMINATED BY ' |*| '
  LINES TERMINATED BY "\n"
  (item_id, category_id);

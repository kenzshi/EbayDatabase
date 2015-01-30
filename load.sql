LOAD DATA LOCAL INFILE 'category.dat'
INTO TABLE `category`
  FIELDS TERMINATED BY ' |*| '
  LINES TERMINATED BY "\n"
  (id, name);

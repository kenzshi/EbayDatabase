CREATE TABLE spatial_item LIKE item;
ALTER TABLE spatial_item ENGINE=MYISAM;
INSERT INTO spatial_item SELECT * FROM item;
ALTER TABLE spatial_item ADD COLUMN pt POINT;
UPDATE spatial_item SET pt=Point(lat, lon);
ALTER TABLE spatial_item MODIFY pt POINT NOT NULL;
CREATE spatial INDEX ix_spatial_item_pt ON spatial_item(pt);

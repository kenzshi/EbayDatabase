SELECT COUNT(*) FROM user;
SELECT COUNT(*) FROM item WHERE BINARY location = 'New York';
SELECT COUNT(*) FROM (SELECT COUNT(*) FROM item_category GROUP BY item_id HAVING COUNT(category_id) = 4) ;
SELECT id FROM item WHERE ends > '"2001-12-20 00:00:01"' AND Started < "2001-12-20 00:00:00" AND num_bids > 0 ORDER BY currently DESC LIMIT 1;
SELECT COUNT(*) FROM user WHERE EXISTS(SELECT * FROM item WHERE item.seller_id = user.id) AND user.rating > 1000;
SELECT COUNT(*) FROM user WHERE EXISTS(SELECT * FROM item WHERE item.seller_id = user.id) AND EXISTS(SELECT * FROM bid WHERE bid.bidder_id = user.id);
SELECT COUNT(DISTINCT category_id) FROM bid JOIN item_category ON item_category.item_id = bid.item_id WHERE price > 100;

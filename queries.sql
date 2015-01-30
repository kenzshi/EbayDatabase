SELECT COUNT(*) FROM user;
SELECT COUNT(*) FROM item WHERE BINARY location = 'New York';

SELECT id FROM item WHERE ends > '"2001-12-20 00:00:01"' AND Started < "2001-12-20 00:00:00" AND num_bids > 0 ORDER BY currently DESC LIMIT 1;
SELECT COUNT(*) FROM user WHERE EXISTS(SELECT * FROM item WHERE item.seller_id = user.id) AND user.rating > 1000;
SELECT COUNT(*) FROM user WHERE EXISTS(SELECT * FROM item WHERE item.seller_id = user.id) AND EXISTS(SELECT * FROM bid WHERE bid.bidder_id = user.id);

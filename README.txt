Kenneth Shi
304063313

Jia Dan Duan
604022222

1.  item -> seller_id (user.id)
    item_category -> item_id (item.id)
                  -> category_id (category.id)
    bid -> bidder_id (user.id)
        -> item_id (item.id)

2. All dependencies are foreign key constraints on the table column.

3. Yes.

4. Yes.

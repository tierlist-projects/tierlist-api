ALTER TABLE tierlist_like
    ADD CONSTRAINT unique_member_id_tierlist_id UNIQUE (member_id, tierlist_id);

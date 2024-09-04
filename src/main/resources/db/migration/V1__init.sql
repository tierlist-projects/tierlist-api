CREATE TABLE category
(
    id             BIGINT AUTO_INCREMENT,
    name           VARCHAR(255),
    favorite_count INT DEFAULT 0 NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE category_favorite
(
    id          BIGINT AUTO_INCREMENT,
    category_id BIGINT,
    member_id   BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE item
(
    id          BIGINT AUTO_INCREMENT,
    name        VARCHAR(255),
    category_id BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE item_rank
(
    id          BIGINT AUTO_INCREMENT,
    `rank`      ENUM ('s','a','b','c','d','f','none'),
    image       VARCHAR(255),
    order_idx   INT NOT NULL,
    item_id     BIGINT,
    tierlist_id BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE member
(
    id            BIGINT AUTO_INCREMENT,
    email         VARCHAR(255) NOT NULL UNIQUE,
    nickname      VARCHAR(255) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    profile_image VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE tierlist
(
    id              BIGINT AUTO_INCREMENT,
    title           VARCHAR(255) NOT NULL,
    thumbnail_image VARCHAR(255),
    content         VARCHAR(255),
    is_published    BOOLEAN      NOT NULL,
    comment_count   INT          NOT NULL,
    like_count      INT          NOT NULL,
    member_id       BIGINT,
    topic_id        BIGINT,
    modified_at     TIMESTAMP(6),
    created_at      TIMESTAMP(6),
    PRIMARY KEY (id)
);

CREATE TABLE tierlist_comment
(
    created_at        TIMESTAMP(6),
    id                BIGINT AUTO_INCREMENT,
    modified_at       TIMESTAMP(6),
    parent_comment_id BIGINT,
    root_id           BIGINT,
    tierlist_id       BIGINT,
    writer_id         BIGINT,
    content           VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE tierlist_like
(
    id          BIGINT AUTO_INCREMENT,
    member_id   BIGINT,
    tierlist_id BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE topic
(
    favorite_count INT DEFAULT 0 NOT NULL,
    category_id    BIGINT,
    id             BIGINT AUTO_INCREMENT,
    name           VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE topic_favorite
(
    id        BIGINT AUTO_INCREMENT,
    member_id BIGINT,
    topic_id  BIGINT,
    PRIMARY KEY (id)
);

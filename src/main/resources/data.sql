CREATE TABLE session_info
(
    id      VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE balance
(
    user_id      VARCHAR(255) NOT NULL,
    user_nick    VARCHAR(255) NOT NULL,
    amount       BIGINT       NOT NULL,
    currency     VARCHAR(255) NOT NULL,
    denomination INT          NOT NULL,
    max_win      BIGINT       NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE debit
(
    bet_id  VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    amount BIGINT       NOT NULL,
    PRIMARY KEY (bet_id)
);


INSERT INTO session_info (id, user_id)
VALUES ('16e2ea64-5725-40a4-99cc-c5c0deb8568d', '4699292b-770f-4710-9496-ed9493aa0a6b123123');
INSERT INTO balance (user_id, user_nick, amount, currency, denomination, max_win)
VALUES ('4699292b-770f-4710-9496-ed9493aa0a6b123123', 'mike_12392', 2000, 'USD', 2, 5000);

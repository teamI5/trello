CREATE TABLE board (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       workspace_id BIGINT,
                       background_color VARCHAR(255),
                       image_url VARCHAR(255),
                       title VARCHAR(255),
                       PRIMARY KEY (id)
);

CREATE TABLE card (
                      created_at DATETIME(6),
                      deadline DATETIME(6),
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      list_id BIGINT NOT NULL,
                      modified_at DATETIME(6),
                      title VARCHAR(20) NOT NULL,
                      contents VARCHAR(100),
                      file_url VARCHAR(255),
                      PRIMARY KEY (id)
);

CREATE TABLE card_user (
                           card_id BIGINT,
                           id BIGINT NOT NULL AUTO_INCREMENT,
                           user_id BIGINT,
                           PRIMARY KEY (id)
);

CREATE TABLE comment (
                         card_id BIGINT NOT NULL,
                         created_at DATETIME(6),
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         modified_at DATETIME(6),
                         user_id BIGINT NOT NULL,
                         content TEXT CHARACTER SET utf8mb4 NOT NULL,
                         PRIMARY KEY (id)
);

CREATE TABLE lists (
                       board_id BIGINT,
                       created_at DATETIME(6),
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       modified_at DATETIME(6),
                       order_number BIGINT,
                       title VARCHAR(255),
                       PRIMARY KEY (id)
);

CREATE TABLE `user` (
                        deleted BIT DEFAULT FALSE,
                        created_at DATETIME(6),
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        modified_at DATETIME(6),
                        email VARCHAR(255),
                        password VARCHAR(255),
                        role ENUM ('ROLE_ADMIN','ROLE_USER'),
                        PRIMARY KEY (id)
);

CREATE TABLE workspace (
                           created_at DATETIME(6),
                           id BIGINT NOT NULL AUTO_INCREMENT,
                           modified_at DATETIME(6),
                           description VARCHAR(255),
                           title VARCHAR(255),
                           PRIMARY KEY (id)
);

CREATE TABLE workspace_user (
                                id BIGINT NOT NULL AUTO_INCREMENT,
                                user_id BIGINT,
                                workspace_id BIGINT,
                                role ENUM ('BOARD','READ_ONLY','WORKSPACE'),
                                PRIMARY KEY (id)
);

ALTER TABLE board
    ADD CONSTRAINT FK_board_workspace
        FOREIGN KEY (workspace_id)
            REFERENCES workspace (id);

ALTER TABLE card
    ADD CONSTRAINT FK_card_list
        FOREIGN KEY (list_id)
            REFERENCES lists (id);

ALTER TABLE card_user
    ADD CONSTRAINT FK_card_user_card
        FOREIGN KEY (card_id)
            REFERENCES card (id);

ALTER TABLE card_user
    ADD CONSTRAINT FK_card_user_user
        FOREIGN KEY (user_id)
            REFERENCES `user` (id);

ALTER TABLE comment
    ADD CONSTRAINT FK_comment_card
        FOREIGN KEY (card_id)
            REFERENCES card (id);

ALTER TABLE comment
    ADD CONSTRAINT FK_comment_user
        FOREIGN KEY (user_id)
            REFERENCES `user` (id);

ALTER TABLE lists
    ADD CONSTRAINT FK_lists_board
        FOREIGN KEY (board_id)
            REFERENCES board (id);

ALTER TABLE workspace_user
    ADD CONSTRAINT FK_workspace_user_user
        FOREIGN KEY (user_id)
            REFERENCES `user` (id);

ALTER TABLE workspace_user
    ADD CONSTRAINT FK_workspace_user_workspace
        FOREIGN KEY (workspace_id)
            REFERENCES workspace (id);

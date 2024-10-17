CREATE TABLE `file` (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        card_id BIGINT NOT NULL,
                        upload_file_name VARCHAR(255) NOT NULL,
                        original_filename VARCHAR(255) NOT NULL,
                        file_url VARCHAR(255) NOT NULL,
                        file_path VARCHAR(255) NOT NULL,
                        file_type VARCHAR(50) NOT NULL,
                        file_size BIGINT NOT NULL,
                        PRIMARY KEY (id),
                        FOREIGN KEY (card_id) REFERENCES card (id)
);


DROP TABLE IF EXISTS TBL_USER;

CREATE TABLE TBL_USER(
                         id INT AUTO_INCREMENT  PRIMARY KEY,
                         first_name VARCHAR(50) NOT NULL,
                         last_name VARCHAR(50) NOT NULL,
                         email VARCHAR(50) DEFAULT NULL
);
CREATE TABLE `user` (
  `idx` int NOT NULL,
  `id` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `password` varchar(30) NOT NULL,
  `phone_number` varchar(11) NOT NULL,
  `gender` char(1) NOT NULL,
  `age` int NOT NULL,
  `is_admin` tinyint DEFAULT '0',
  `token` VARCHAR(1000) NULL DEFAULT NULL,
  PRIMARY KEY (`idx`),
  CONSTRAINT `user_chk_1` CHECK ((`gender` in (_utf8mb4'M',_utf8mb4'F')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `board` (
  `idx` int NOT NULL AUTO_INCREMENT,
  `user_idx` int NOT NULL,
  `title` varchar(50) NOT NULL,
  `content` varchar(1000) NOT NULL,
  `written_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `is_notice` tinyint DEFAULT '0',
  `view_count` int DEFAULT '0',
  `image` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idx`),
  KEY `user_id_idx` (`user_idx`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_idx`) REFERENCES `user` (`idx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='게시판 DB';

CREATE TABLE `plan` (
  `idx` int NOT NULL AUTO_INCREMENT,
  `user_idx` int NOT NULL,
  `title` varchar(20) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date DEFAULT NULL,
  `recommened` int DEFAULT '0',
  `content` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`idx`),
  KEY `user_idx` (`user_idx`),
  CONSTRAINT `plan_ibfk_1` FOREIGN KEY (`user_idx`) REFERENCES `user` (`idx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `place` (
  `idx` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `url` varchar(200) NOT NULL,
  `latitude` varchar(100) NOT NULL,
  `longitude` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `category_code` varchar(10) NOT NULL,
  `plan_idx` int NOT NULL,
  `date_index` int NOT NULL,
  `index` int NOT NULL,
  `comment` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idx`),
  KEY `plan_id_idx` (`plan_idx`),
  CONSTRAINT `plan_idx` FOREIGN KEY (`plan_idx`) REFERENCES `plan` (`idx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='하나의 여행 계획 포함되는 장소에 대한 정보 테이블';



CREATE TABLE `comment` (
  `idx` int NOT NULL AUTO_INCREMENT,
  `user_idx` int NOT NULL,
  `board_idx` int NOT NULL,
  `content` varchar(100) NOT NULL,
  `written_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idx`),
  KEY `comment_board_idx` (`board_idx`),
  KEY `comment_user_idx` (`user_idx`),
  CONSTRAINT `board_idx` FOREIGN KEY (`board_idx`) REFERENCES `board` (`idx`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_idx` FOREIGN KEY (`user_idx`) REFERENCES `user` (`idx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='게시글의 댓글 테이블';

CREATE TABLE `qna` (
  `idx` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  `content` varchar(45) DEFAULT NULL,
  `user_idx` int DEFAULT NULL,
  PRIMARY KEY (`idx`),
  KEY `user_idx_idx` (`user_idx`),
  CONSTRAINT `qna_ibfk_1` FOREIGN KEY (`user_idx`) REFERENCES `user` (`idx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
CREATE DATABASE  IF NOT EXISTS `enjoy_trip` ;
USE `enjoy_trip`;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `password` varchar(30) NOT NULL,
  `phone_number` varchar(11) NOT NULL,
  `gender` char(1) NOT NULL,
  `age` int NOT NULL,
  `is_admin` tinyint DEFAULT '0',
  `token` varchar(1000) DEFAULT NULL,
  `image` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `user_chk_1` CHECK ((`gender` in (_utf8mb4'M',_utf8mb4'F')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `board`;

CREATE TABLE `board` (
  `idx` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL,
  `title` varchar(50) NOT NULL,
  `content` varchar(1000) NOT NULL,
  `written_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `is_notice` tinyint DEFAULT '0',
  `view_count` int DEFAULT '0',
  `image` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idx`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='게시판 DB';

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `idx` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL,
  `board_idx` int NOT NULL,
  `content` varchar(100) NOT NULL,
  `written_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idx`),
  KEY `comment_board_idx` (`board_idx`),
  KEY `comment_user_idx` (`user_id`),
  CONSTRAINT `board_idx` FOREIGN KEY (`board_idx`) REFERENCES `board` (`idx`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_idx` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='게시글의 댓글 테이블';

DROP TABLE IF EXISTS `follower`;

CREATE TABLE `follower` (
  `following` varchar(20) NOT NULL,
  `follower` varchar(20) NOT NULL,
  PRIMARY KEY (`following`,`follower`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


DROP TABLE IF EXISTS `plan`;

CREATE TABLE `plan` (
  `idx` int NOT NULL AUTO_INCREMENT,
  `title` varchar(20) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date DEFAULT NULL,
  PRIMARY KEY (`idx`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `plancomment`;

CREATE TABLE `plancomment` (
  `idx` int NOT NULL AUTO_INCREMENT,
  `plan_idx` int NOT NULL,
  `content` varchar(1000) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  PRIMARY KEY (`idx`),
  KEY `plan_comment_idx` (`plan_idx`),
  CONSTRAINT `plan_comment` FOREIGN KEY (`plan_idx`) REFERENCES `plan` (`idx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3;


DROP TABLE IF EXISTS `place`;

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
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='하나의 여행 계획 포함되는 장소에 대한 정보 테이블';

DROP TABLE IF EXISTS `region`;

CREATE TABLE `region` (
  `idx` int NOT NULL AUTO_INCREMENT,
  `cate1` varchar(45) DEFAULT NULL,
  `cate2` varchar(45) DEFAULT NULL,
  `descr` varchar(1000) DEFAULT NULL,
  `img` varchar(1000) DEFAULT NULL,
  `cate1_eng` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idx`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



LOCK TABLES `region` WRITE;
/*!40000 ALTER TABLE `region` DISABLE KEYS */;
INSERT INTO `region` VALUES (5,'서울','종로','도심 속 옛 정서를 느낄 수 있는 북촌 한옥마을과 고궁은 가볍게 산책하기 좋으며, 한복체험을 통해 이색적인 추억을 쌓을 수 있다. 젊음 가득한 대학로와 육회와 마약김밥으로 유명한 광장시장은 항상 사람들의 발길이 끊이지 않는다. 도심 속 휴식 공간인 청계천에서는 매주 크고 작은 공연 및 행사들이 열려 볼거리가 풍성하다.','https://search.pstatic.net/common?src=https%3A%2F%2Fsearch.pstatic.net%2Fcommon%2F%3Fsrc%3Dhttp%253A%252F%252Fblogfiles.naver.net%252FMjAyMzA0MjVfODMg%252FMDAxNjgyMzg2MDk4NjI3.Mq4RdtfRc0fVLliiD5I0_xI4H0b2ph1-nHzrnzO_Xpsg.bE8XRQaCAJFefGC9TyvoZ8DcIvGK9WVUL1U98CTvwWEg.JPEG.cheshires%252F1682257749431.jpg%26type%3Dsc960_832&type=f1040_576_domesearch','SEOUL'),(6,'서울','용산','다양한 세계 문화와 인종이 공존하는 서울 용산구. 주한 미군을 비롯해 여러 국적의 외국인들이 집결한 이태원은 아기자기한 경리단길과 세계음식문화거리가 있어 이색적인 분위기를 느낄 수 있다. 젊은 세대의 핫플레이스로 급부상한 한남동에는 개성 넘치는 카페와 식당들, 새로운 문화예술 아지트인 디뮤지엄이 있다. 총 33만여 국보급 유물을 소장한 국립중앙박물관에서는 다양한 전시를 제공하고 있어 문화생활을 즐기기 좋다.','https://search.pstatic.net/common?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20160921_125%2F1474442065795aF4tH_JPEG%2F176871564444565_1.jpeg&type=f210_210_domesearch','SEOUL'),(7,'서울','동대문구','전국 최대 규모의 한약재 전문 시장 서울약령시는 수천 년의 역사를 가지고 있으며 한의원, 한약방, 탕제원 등 다양한 업종이 함께 어우러져 있다. 한국 최초의 수목원인 홍릉수목원은 국내외의 여러 식물을 수집 및 관리하며, 매주 토요일과 일요일에 개방한다. 서울풍물시장을 방문하면 우리의 멋과 정서가 담긴 전통 고가구, 골동품 등을 볼 수 있어 잠시나마 추억을 선물 받을 수 있다. 노릇노릇한 거대 사이즈의 해물파전을 맛보고 싶다면 회기역 파전 골목을 방문하는 것을 추천한다.','https://search.pstatic.net/common?src=http%3A%2F%2Fthumb.photo.naver.net%2Fexphoto02%2F2009%2F11%2F22%2F287%2F%25B5%25BF%25B4%25EB%25B9%25AE1170_elmaripe.jpg&type=f1040_576_domesearch','SEOUL'),(8,'서울','강남구','트렌드의 중심 서울 강남구. 예술가의 거리로 불리는 신사동 가로수길에는 아기자기하고 트렌디한 카페, 맛집, 편집숍 등이 모여있다. 한국 패션의 유행을 선도하는 압구정 로데오거리 그리고 도산 안창호 선생의 정신을 기리는 도산 공원에서는 번화한 도심 속에서 여유를 느낄 수 있다. 서울의 대표 사찰 봉은사는 봄이 되면 홍매화가 만개하여 출사지로 유명하다. 봉은사 근처에는 전시 컨벤션의 메카인 코엑스가 있어 흥미로운 전시부터 다양한 쇼핑까지 한 번에 즐길 수 있다.','https://search.pstatic.net/common?src=https%3A%2F%2Fpostfiles.pstatic.net%2FMjAyMzA0MDRfMjMw%2FMDAxNjgwNTgyODc0Njc1.oCiXurlLyZQWmIn0_VmIxEYb13ILKAIsYR0AuLISDU8g.loRVEtnuV1s99S5-FpCnkEwMpLwYNFTWOg4KK8fQq2Ig.JPEG.huhdr%2F20230403%25EF%25BC%25BF121732.jpg%3Ftype%3Dw773&type=f1040_576_domesearch','SEOUL'),(9,'경기','수원','서울의 축소판이라도고 할 수 있는 경기도 수원시. 서울만큼이나 정치, 경제, 문화, 사회 등 다양한 부문에서 두루 발달한 도시이다. 그중에도 고고학적 가치를 지닌 수원화성은 수원의 자랑이며, 화성행궁 열차를 타고 대표 명소를 둘러보는 것도 좋다. 광교호수공원은 야경이 아름다워 밤에도 산책을 즐기는 사람들이 많으며, 수원 통닭골목은 저렴한 가격에 비해 양이 푸짐해 입소문을 타고 전국 각지에서 많은 이들이 찾아오고 있다.','https://search.pstatic.net/common?src=https%3A%2F%2Fsearch.pstatic.net%2Fcommon%2F%3Fsrc%3Dhttp%253A%252F%252Fblogfiles.naver.net%252FMjAyMzA0MjVfMyAg%252FMDAxNjgyNDIxODg3NTAz.p3YjDFUv79MGCNw7tYlq_FhyROAFde9ecGbju1oSpN8g.8dA5puV5A496VIHZ5tK2lai-Rs4Kpezgx2eMPH4ObP0g.JPEG.jsya6764%252F%25253F%252588%252598%25253F%25259B%252590%25253F%252599%252594%25253F%252584%2525B1_%2525EC%2525B2%2525A0%2525EC%2525AD%252589_%25253F%25259D%2525BC%2525EB%2525AA%25253F_3.jpg%26type%3Dsc960_832&type=f1040_576_domesearch','Gyeonggi'),(10,'경기','여주','여주를 여행할 때는 나만의 테마를 잡는 것도 하나의 방법이다. 문화 역시를 배우고 싶다면 명성황후 생가와 세종대왕릉을 중심으로 코스를 잡고, 여유와 힐링을 테마로 잡는다면 복합문화공간인 아울렛에서 저렴한 쇼핑을 즐겨보자.','https://search.pstatic.net/common?src=http%3A%2F%2Fblogfiles.naver.net%2F%2FMjAyMTA3MjZfMjQ4%2FMDAxNjI3Mjc5MDU3MjM3.0J-RXFbY14VX1HCqotITc4TrsRj5Cumf6ILc7FnYp6wg.n6roMMFmvo0bEW-wn8zPhSTnhdmYbNGCxvBKmrlNM-sg.JPEG.yeojuhangul%2F2.jpg%2F693x520&type=f1040_576_domesearch','Gyeonggi'),(11,'경기','가평','자연의 정취에 젖어들게 만드는 가평 아침고요수목원. 어디를 둘러봐도 풍경이 가득해 지루할 틈이 없다. 그중에도 가평하면 빼놓을 수 없는 쁘띠프랑스는 프랑스를 떠올리게 하는 이국적인 풍경으로 빨간 지붕이 매력적이다. 또한 매년 가을에 열리는 자라섬 국제재즈페스티벌은 석양과 음악이 어우러질 때까지 재즈의 매력에 흠뻑 취해볼 수 있다.','https://search.pstatic.net/common?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20230418_158%2F1681776985024v3HWv_JPEG%2F0518_21_%25BC%25AD%25C8%25AD%25BF%25AC.jpg&type=f210_210_domesearch','Gyeonggi'),(12,'경기','양평','남한강과 북한강이 합류하는 두물머리. 영화와 드라마에도 수 없이 등장한 명소 중에 명소이다. 새벽에 피어오르는 물안개는 신비로움을 더해주며 출사지로도 많은 이들이 찾는다. 아름다운 연못과 꽃이 가득한 세미원은 다양한 테마의 정원이 있어 연인과 가족 나들이 코스로도 좋으며, 꽃이 활짝 피는 오전에 방문하는 게 더 좋다. 풍광이 아름다운 용문사는 42m의 은행나무가 상징을 이루는 만큼 가을에는 노란 은행잎이 만발해 절경을 이룬다.','https://search.pstatic.net/common?src=https%3A%2F%2Fsearch.pstatic.net%2Fcommon%2F%3Fsrc%3Dhttp%253A%252F%252Fblogfiles.naver.net%252FMjAyMjA0MTZfNDgg%252FMDAxNjUwMDkxNzM1NDYx.a-vcOMGrURasFWloPOMCm20MRB6MZEp4HwItnsTZnBUg.xyp6Y0B2Mviv4zZAxd11-jpzpWXc-DZIbA0_KcS2vcIg.JPEG.travstory%252F20190719_%2525BE%2525E7%2525C6%2525F2%2525C0%2525DA%2525C0%2525FC%2525B0%2525C5%2525B1%2525E6_003.jpg%26type%3Dsc960_832&type=f1040_576_domesearch','Gyeonggi'),(13,'충남','천안','서울에서 1시간도 채 걸리지 않아 닿을 수 있는 천안. 편리한 교통으로 주말이면 많은 관광객들이 몰린다. 역사와 문화, 자연 모두 아우르는 독립기념관은 천안의 대표 관광지이며 천안삼거리 공원은 가족 나들이 코스로 좋다. 아라리오갤러리와 리각미술관 등 다양한 작품을 만날 수 있는 공간이 있어 갤러리 투어를 테마로 잡는 것도 하나의 팁! 병천순대와 호두과자는 꼭 맛 보아야 하는 천안 대표 주전부리!','https://search.pstatic.net/common?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150831_266%2F1441006691781VjuUq_JPEG%2FSUBMIT_1318825436850_11620331.jpg&type=f210_210_domesearch','CHUNGNAM'),(14,'충남','공주','역사가 숨 쉬는 사찰이 있는 공주. 대표적으로 계룡산에는 동학사, 갑사 등 유독 아름다운 사찰이 몰려있다. 마곡사는 세계문화유산에 잠정목록에 오를 만큼 그 가치를 인정받은 문화재이다. 백제 시대에 공주를 지키던 공산성에 올라 내려다보이는 금강의 풍경은 일품이다.','https://search.pstatic.net/common?src=https%3A%2F%2Fsearch.pstatic.net%2Fcommon%2F%3Fsrc%3Dhttp%253A%252F%252Fblogfiles.naver.net%252FMjAyMzAzMjZfMjI3%252FMDAxNjc5ODIyMjk2MTQx.mh1e-OOIA2cesU-EDHm92EEr3TUuD8kVo7l2eZzqkqYg.8cpMivhta2y7iIBo_tPkevh8YzH_--6TFwS0VXlmAmsg.JPEG.w_clouds%252FKakaoTalk_20230326_175815924_22.jpg%26type%3Dsc960_832&type=f1040_576_domesearch','CHUNGNAM'),(15,'충남','부여','백제의 역사가 잠들어 있는 부여. 백제 권역에 흩어진 문화유물을 전시하고 있는 국립부여박물관과 백제 문화 단지, 부여 정림사지는 부여에서 빼놓을 수 없는 명소이다. 백제시대의 별궁 연못이었던 궁남지에서는 매년 7월이면 서동연꽃축제가 열려 궁남지의 멋을 더해준다.','https://search.pstatic.net/common?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzA0MDVfMjI3%2FMDAxNjgwNjc3NTM3NTA2.Zu9wu0x35R3Jsj3KOtjdbrCuo1Z37xH1g2rVwDTwyyIg.w3xGQT_ZM4EjBA05tR1cSGP8VTX6c6Omj9-vdCzfLxAg.JPEG.tour_buyeo%2FCK_tc00240076844.jpg%2F5616x3744&type=f1040_576_domesearch','CHUNGNAM'),(16,'충남','태안','사계절 내내 축제를 즐길 수 있는 충남 태안. 연간 내내 즐길 수 있는 빛 축제를 비롯해 4~5월에는 튤립축제가 7~8월에는 백합꽃 축제. 9~10월에는 가을꽃축제 등 계절 색을 그대로 느낄 수 있는 곳으로 태안만한 곳이 없다. 세월을 등에 업고 만들어진 신두리 해안사구도 태안에서만 볼 수 있는 자연 경관이다.','https://search.pstatic.net/common?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150901_131%2F14410456161688ugIy_JPEG%2F157155537049760_0.jpg&type=f210_210_domesearch','CHUNGNAM');
/*!40000 ALTER TABLE `region` ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS `tripmember`;

CREATE TABLE `tripmember` (
  `plan_idx` int NOT NULL,
  `user_id` varchar(20) NOT NULL,
  PRIMARY KEY (`plan_idx`,`user_id`),
  CONSTRAINT `tripmember_ibfk_1` FOREIGN KEY (`plan_idx`) REFERENCES `plan` (`idx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



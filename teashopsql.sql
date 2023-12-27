CREATE TABLE `cart` (
  `id` int NOT NULL,
  `_id` longtext,
  `user` longtext,
  `_class` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `cart_productlist` (
  `id` int NOT NULL,
  `parent_fk` int DEFAULT NULL,
  `index` int DEFAULT NULL,
  `reference` longtext,
  PRIMARY KEY (`id`),
  KEY `s3t_cart_productlist_cart_0` (`parent_fk`),
  CONSTRAINT `s3t_cart_productlist_cart_0` FOREIGN KEY (`parent_fk`) REFERENCES `cart` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `order` (
  `id` int NOT NULL,
  `_id` longtext,
  `number` longtext,
  `isdone` bit(1) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `useraccount` longtext,
  `_class` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `order_products` (
  `id` int NOT NULL,
  `parent_fk` int DEFAULT NULL,
  `index` int DEFAULT NULL,
  `reference` longtext,
  PRIMARY KEY (`id`),
  KEY `s3t_order_products_order_0` (`parent_fk`),
  CONSTRAINT `s3t_order_products_order_0` FOREIGN KEY (`parent_fk`) REFERENCES `order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `photo` (
  `id` int NOT NULL,
  `_id` longtext,
  `link` longtext,
  `_class` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `product` (
  `id` int NOT NULL,
  `_id` longtext,
  `name` longtext,
  `pricebuy` double DEFAULT NULL,
  `pricsell` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `description` longtext,
  `isexist` bit(1) DEFAULT NULL,
  `producttype` longtext,
  `_class` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `product_photos` (
  `id` int NOT NULL,
  `parent_fk` int DEFAULT NULL,
  `index` int DEFAULT NULL,
  `reference` longtext,
  PRIMARY KEY (`id`),
  KEY `s3t_product_photos_product_0` (`parent_fk`),
  CONSTRAINT `s3t_product_photos_product_0` FOREIGN KEY (`parent_fk`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `producttype` (
  `id` int NOT NULL,
  `_id` longtext,
  `name` longtext,
  `_class` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `role` (
  `id` int NOT NULL,
  `_id` longtext,
  `name` longtext,
  `_class` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
  `id` int NOT NULL,
  `_id` longtext,
  `email` longtext,
  `password` longtext,
  `isexist` bit(1) DEFAULT NULL,
  `_class` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_roles` (
  `id` int NOT NULL,
  `parent_fk` int DEFAULT NULL,
  `index` int DEFAULT NULL,
  `reference` longtext,
  PRIMARY KEY (`id`),
  KEY `s3t_user_roles_user_0` (`parent_fk`),
  CONSTRAINT `s3t_user_roles_user_0` FOREIGN KEY (`parent_fk`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `useraccount` (
  `id` int NOT NULL,
  `_id` longtext,
  `name` longtext,
  `sername` longtext,
  `patronymic` longtext,
  `userm` longtext,
  `_class` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `userstatus` (
  `id` int NOT NULL,
  `_id` longtext,
  `name` longtext,
  `discount` double DEFAULT NULL,
  `_class` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `product` (
  `id` bigint(20) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `creation_date` datetime NOT NULL,
  `description` text NOT NULL,
  `name` varchar(255) NOT NULL,
  `category_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `product_aud` (
  `id` bigint(20) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` tinyint(4) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `description` text,
  `name` varchar(255) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `product_category` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `product_score` (
  `id` bigint(20) NOT NULL,
  `reference_date` date NOT NULL,
  `x` double NOT NULL,
  `y` double NOT NULL,
  `z` int(11) NOT NULL,
  `product_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `revinfo` (
  `rev` int(11) NOT NULL,
  `revtstmp` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sale` (
  `id` bigint(20) NOT NULL,
  `creation_date` datetime NOT NULL,
  `product_rating` int(11) NOT NULL,
  `customer_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `seller_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `seller` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK5cypb0k23bovo3rn1a5jqs6j4` (`category_id`);

ALTER TABLE `product_aud`
  ADD PRIMARY KEY (`id`,`rev`),
  ADD KEY `FK9vwllld6jlw5xys1ay911oh1x` (`rev`);

ALTER TABLE `product_category`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `product_score`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK6njiydsei1bgkpa6178l67qiw` (`product_id`);

ALTER TABLE `revinfo`
  ADD PRIMARY KEY (`rev`);

ALTER TABLE `sale`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjw88ojfoqquyd9f1obip1ar0g` (`customer_id`),
  ADD KEY `FKonrcqwf09u6spb6ty6sh11jh5` (`product_id`),
  ADD KEY `FKqo5yb2opubvktdxvya06qctjs` (`seller_id`);

ALTER TABLE `seller`
  ADD PRIMARY KEY (`id`);


ALTER TABLE `customer`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `product_category`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `product_score`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `revinfo`
  MODIFY `rev` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `sale`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `seller`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;


ALTER TABLE `product`
  ADD CONSTRAINT `FK5cypb0k23bovo3rn1a5jqs6j4` FOREIGN KEY (`category_id`) REFERENCES `product_category` (`id`);

ALTER TABLE `product_aud`
  ADD CONSTRAINT `FK9vwllld6jlw5xys1ay911oh1x` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`);

ALTER TABLE `product_score`
  ADD CONSTRAINT `FK6njiydsei1bgkpa6178l67qiw` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

ALTER TABLE `sale`
  ADD CONSTRAINT `FKjw88ojfoqquyd9f1obip1ar0g` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  ADD CONSTRAINT `FKonrcqwf09u6spb6ty6sh11jh5` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `FKqo5yb2opubvktdxvya06qctjs` FOREIGN KEY (`seller_id`) REFERENCES `seller` (`id`);
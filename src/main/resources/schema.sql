CREATE TABLE IF NOT EXISTS `USER`
(
    `id`               bigint NOT NULL AUTO_INCREMENT,
    `org_name`         varchar(255) DEFAULT NULL,
    `email`            varchar(255) DEFAULT NULL,
    `mobile`           bigint       DEFAULT NULL,
    `password`         varchar(255) DEFAULT NULL,
    `website`          varchar(255) DEFAULT NULL,
    `account_verified` bit(1) NOT NULL,
    `referred_by`          varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `PRINCIPAL_GROUP`
(
    `id`   bigint       NOT NULL AUTO_INCREMENT,
    `code` varchar(255) NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_mivgwei5gryhbnt5fsec4u3se` (`code`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `JOB_OFFER`
(
    `offer_id`                      bigint NOT NULL AUTO_INCREMENT,
    `ern`                           varchar(255) DEFAULT NULL,
    `employee_country`              varchar(255) DEFAULT NULL,
    `tin`                           varchar(255) DEFAULT NULL,
    `employer_org_name`             varchar(255) DEFAULT NULL,
    `employer_email`                varchar(255) DEFAULT NULL,
    `offer_updated_on`              DATE NOT NULL,
    `joining_date`                  DATE NOT NULL,
    `employment_type`               varchar(255) DEFAULT NULL,
    `updated_by`                    varchar(255) DEFAULT NULL,
    `enable_employment_offer_monitoring` BIT DEFAULT 0,
    `employment_offer_status`       varchar(255) DEFAULT NULL,
    PRIMARY KEY (`offer_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `SECURE_TOKEN`
(
    `id`        bigint      NOT NULL AUTO_INCREMENT,
    `expire_at` datetime(6) NOT NULL,
    `timestamp` datetime(6)  DEFAULT NULL,
    `token`     varchar(255) DEFAULT NULL,
    `user_id`   bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_1vnojtqwxyii8kinnsohdknhw` (`token`),
    KEY `FKdv1lktun3k4xuye9gb83xkv61` (`user_id`),
    CONSTRAINT `FKdv1lktun3k4xuye9gb83xkv61` FOREIGN KEY (`user_id`) REFERENCES `USER` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 20
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `USER_GROUP`
(
    `user_id`  bigint NOT NULL,
    `group_id` bigint NOT NULL,
    PRIMARY KEY (`user_id`, `group_id`),
    KEY `FKkeyy6lj4ooau5pi73ftawhvsa` (`group_id`),
    CONSTRAINT `FK1c1dsw3q36679vaiqwvtv36a6` FOREIGN KEY (`user_id`) REFERENCES `USER` (`id`),
    CONSTRAINT `FKkeyy6lj4ooau5pi73ftawhvsa` FOREIGN KEY (`group_id`) REFERENCES `PRINCIPAL_GROUP` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;





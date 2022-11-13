INSERT IGNORE INTO `safehiring`.`principal_group`(`id`,`code`,`name`)VALUES(1 ,'ADMIN','Admin Group');
INSERT IGNORE INTO `safehiring`.`principal_group`(`id`,`code`,`name`)VALUES(2 ,'EMPLOYER','Employer Group');
INSERT IGNORE INTO `safehiring`.`principal_group`(`id`,`code`,`name`)VALUES(3 ,'EMPLOYEE','Employee Group');
INSERT IGNORE INTO `safehiring`.`principal_group`(`id`,`code`,`name`)VALUES(4 ,'SUPPORT','Customer Support');

INSERT IGNORE INTO `safehiring`.`user`(`id`,`cin_number`,`company_name`,`email`,`mobile`,`password`,`website`,`account_verified`)
VALUES(1,'APQ-123-CIN-NUMBER','Safe Hiring','admin@gmail.com',123456781,'{bcrypt}$2a$10$9ZS4ZKkO3cHrwMWoXlEFd.UyQmIt4.NxW12nm9B/7PcbYcq/lLRH6','http://www.safehiring.com',true);

INSERT IGNORE INTO `safehiring`.`user`(`id`,`cin_number`,`company_name`,`email`,`mobile`,`password`,`website`,`account_verified`)
VALUES(2,'APQ-124-CIN-NUMBER','Safe Hiring','employer@gmail.com',123456782,'{bcrypt}$2a$10$9ZS4ZKkO3cHrwMWoXlEFd.UyQmIt4.NxW12nm9B/7PcbYcq/lLRH6','http://www.safehiring.com',true);

INSERT IGNORE INTO `safehiring`.`user`(`id`,`cin_number`,`company_name`,`email`,`mobile`,`password`,`website`,`account_verified`)
VALUES(3,'APQ-125-CIN-NUMBER','Safe Hiring','employee@gmail.com',123456783,'{bcrypt}$2a$10$9ZS4ZKkO3cHrwMWoXlEFd.UyQmIt4.NxW12nm9B/7PcbYcq/lLRH6','http://www.safehiring.com',true);

INSERT IGNORE INTO `safehiring`.`user`(`id`,`cin_number`,`company_name`,`email`,`mobile`,`password`,`website`,`account_verified`)
VALUES(4,'APQ-126-CIN-NUMBER','Safe Hiring','support@gmail.com',123456784,'{bcrypt}$2a$10$9ZS4ZKkO3cHrwMWoXlEFd.UyQmIt4.NxW12nm9B/7PcbYcq/lLRH6','http://www.safehiring.com',true);

INSERT IGNORE INTO `safehiring`.`user_group`(`user_id`,`group_id`)VALUES(1,1);
INSERT IGNORE INTO `safehiring`.`user_group`(`user_id`,`group_id`)VALUES(2,2);
INSERT IGNORE INTO `safehiring`.`user_group`(`user_id`,`group_id`)VALUES(3,3);
INSERT IGNORE INTO `safehiring`.`user_group`(`user_id`,`group_id`)VALUES(4,4);





CREATE USER 'admin'@'%' IDENTIFIED BY 'admin';
CREATE DATABASE IF NOT EXISTS st_and_db_mysql_database;
GRANT ALL PRIVILEGES ON * . * TO 'admin'@'%' WITH GRANT OPTION;

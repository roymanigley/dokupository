@echo off
java.exe -jar -Dspring.datasource.url=jdbc:mysql://localhost:3306/dokupository -Dspring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver -Dlogging.level.ch.ultrasoft=INFO DokuPository-1.0.jar
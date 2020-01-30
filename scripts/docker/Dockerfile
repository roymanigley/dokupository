FROM bitnami/mariadb:10.2

ENV MARIADB_ROOT_PASSWORD toor
ENV MARIADB_USER ultrasoft
ENV MARIADB_PASSWORD Ultra3052Soft
ENV MARIADB_DATABASE dokupository

COPY initial_script/*.sql /docker-entrypoint-initdb.d/

EXPOSE 3303
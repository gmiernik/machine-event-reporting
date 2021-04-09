# Preparing database

Type: PostgreSQL

## Running database on developers machine

```
podman run --name machineeventdb -e POSTGRES_PASSWORD=test123 -d -p 5432:5432 postgres
```

## SQL scripts

Scripts located in `db` folder have following convention name:

```
v<MAJOR_VER>_<MINOR_VER>_<SHORT_DESCRIPTION>.sql
```
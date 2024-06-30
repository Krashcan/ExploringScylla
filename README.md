To get the scylla nodes up and running, run the following command:
```
docker-compose up -d
```

To stop the scylla nodes, run the following command:
```
 docker-compose down
```

To check the status of the scylla nodes, run the following command:
```
docker exec -it scylla-1 /bin/bash
nodetool status --resolve-ip
```
There should be three nodes up and running, denoted by the UN (Up Normal) status.

For scylla resources and documentation, refer to the following links: [Official Resources](https://resources.scylladb.com)

# High Availability cluster of ElasticSearch


## Running

    docker-compose up --force-recreate --build

Then check [ElasticSearch node #1](http://localhost:10001/_cluster/health).

Then check [ElasticSearch node #2](http://localhost:10002/_cluster/health).

Then check [Node.js node #1](http://localhost:10011/).

Then check [Node.js node #2](http://localhost:10012/).

Then check [Nginx Frontend](http://localhost/).



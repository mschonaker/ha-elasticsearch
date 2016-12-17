# High Availability cluster of ElasticSearch


## Running

    docker-compose up --force-recreate --build

ElasticSearch Docker Image might require to execute:

    sudo sysctl -w vm.max_map_count=262144

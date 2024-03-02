#!/bin/bash

# Obtém uma lista de todos os IDs de containers
container_ids=$(docker ps -aq)

# Itera sobre cada ID de container e os remove
for id in $container_ids
do
    docker stop $id  # Para o container, se estiver em execução
    docker rm $id    # Remove o container
done

echo "Todos os containers foram removidos com sucesso."
docker rmi -f $(docker images -q)

docker run -d -p 9090:9090 cardeneta-api
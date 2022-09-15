#commands

sudo systemctl start kafka
sudo systemctl start zookeeper

/usr/local/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic Kafka_Example_json

/usr/local/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic Kafka_Example_json --from-beginning 
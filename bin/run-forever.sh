echo "run-forever.sh:: Start running the app..."

nohup java -jar target/voting-0.0.1-SNAPSHOT.jar server config/dev_config.yml 0<&- &> /tmp/app.log &

# Grep the pid of Java process and save the PID in a temp file
# Specifically look for voting jar `ps -elf | awk '/voting.*.jar/ {print $4}' | head -1`
pid=`ps -elf | awk '/java -jar/ {print $4}' | head -1`
echo $pid > /tmp/app.pid

echo "run-forever.sh:: Successfully started the app (pid:$pid)"
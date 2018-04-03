echo '--------- building jar -----------'
gradle build -x test

echo '------ generating test case ------'
python3 ./scripts/testcase_generate.py ./scripts/config.json

echo '-------- setting test env --------'
cp ./build/libs/* ./test
echo 'jar copyed to ./test'
echo '----------- testing --------------'
declare -i num_test
num_test=($(ls -l ./test/testcase | wc -l)-1)/2
echo 'number of test:' ${num_test}
cd test
jarname=$(ls | grep *.jar)
declare -i correct_cnt
correct_cnt=0
echo 'testing ' ${jarname}
num_test=num_test-1
for i in $(seq 0 ${num_test})
do
    java -jar ${jarname} ./testcase/${i}_usecase.txt
    cmp result.txt ./testcase/${i}_result_true.txt
    if [ ${?} == 0 ]; then
        correct_cnt=correct_cnt+1
    fi
done
num_test=num_test+1
echo 'test passed: ' ${correct_cnt} 'total: ' ${num_test}
cd ..
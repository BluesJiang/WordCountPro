echo '--------- building jar -----------'
gradle build -x test

echo '------ generating test case ------'
python ./scripts/testcase_generate.py ./scripts/config.json

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
    start=`python -c 'import time; print (time.time())'`
    java -jar ${jarname} ./testcase/${i}_usecase.txt
    end=`python -c 'import time; print (time.time())'`
    cmp result.txt ./testcase/${i}_result_true.txt
    if [ ${?} == 0 ]; then
        correct_cnt=correct_cnt+1
        echo 'test ' $i ' passed...time: ' `bc <<< $end-$start`
    else
        echo 'test ' $i ' failed...time: ' `bc <<< $end-$start`
    fi
    mv result.txt ./result/${i}_result.txt
done
num_test=num_test+1
echo 'test passed: ' ${correct_cnt} 'total: ' ${num_test}
cd ..
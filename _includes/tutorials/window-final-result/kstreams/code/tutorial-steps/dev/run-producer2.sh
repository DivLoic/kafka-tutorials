echo '{"id":"101","datetime":"'$(date +%FT%T.%z)'","pressure":30}' >> buffer.out.txt
echo '{"id":"101","datetime":"'$(date +%FT%T.%z)'","pressure":30}' >> buffer.out.txt
echo '{"id":"101","datetime":"'$(date +%FT%T.%z)'","pressure":30}' >> buffer.out.txt
echo '{"id":"102","datetime":"'$(date +%FT%T.%z)'","pressure":30}' >> buffer.out.txt
sleep 10
echo '{"id":"101","datetime":"'$(date -v-10S +%FT%T.%z)'","pressure":30}' >> buffer.out.txt # late of 10 sec
echo '{"id":"101","datetime":"'$(date -v-12S +%FT%T.%z)'","pressure":30}' >> buffer.out.txt # late of 12 sec
echo '{"id":"101","datetime":"'$(date -v-13S +%FT%T.%z)'","pressure":30}' >> buffer.out.txt # late of 13 sec
echo '{"id":"102","datetime":"'$(date +%FT%T.%z)'","pressure":30}' >> buffer.out.txt
sleep 20
echo '{"id":"102","datetime":"'$(date +%FT%T.%z)'","pressure":30}' >> buffer.out.txt # out of the grace period
export TZ=Asia/Tokyo
echo '{"id":"103","datetime":"'$(date +%FT%T.%z)'","pressure":30}' >> buffer.out.txt
echo '{"id":"103","datetime":"'$(date +%FT%T.%z)'","pressure":30}' >> buffer.out.txt
#!/bin/sh

(
curl -s "http://localhost:8080/v1/ad/1?a=5"; echo
curl -s "http://localhost:8080/v1/ad/2?c=5&b=2"; echo
curl -s "http://localhost:8080/v1/ad/3?b=5&c=10"; echo
curl -s "http://localhost:8080/v1/ad/4?a=5&b=3"; echo
curl -s "http://localhost:8080/v1/ad/5?b=5"; echo
curl -s "http://localhost:8080/v1/ad/6?c=5&f=4"; echo
curl -s "http://localhost:8080/v1/ad/7?a=5&c=12"; echo
curl -s "http://localhost:8080/v1/ad/8?c=2&b=4"; echo
curl -s "http://localhost:8080/v1/ad/9?a=5"; echo
curl -s "http://localhost:8080/v1/ad/10?a=1&b=0"; echo
curl -s "http://localhost:8080/v1/ad/11?a=1&c=2"; echo
) > result

diff -u result expected.result

if [ "$?" -eq 0 ]
then
  echo
  echo "Your application seems to behave correctly!"
else
  echo
  echo "There were differences from the current and the expected result. See above."
fi

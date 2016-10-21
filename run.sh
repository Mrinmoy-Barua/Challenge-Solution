#!/bin/bash

fileProduct="products.txt"
fileListing="listings.txt"
fileJSONJar="json-simple-1.1.1.jar"
if ! [ -f $fileProduct ]; then
echo "File 'products.txt' not found.";
exit;
fi

if ! [ -f $fileListing ]; then
echo "File 'listings.txt' not found.";
exit;
fi

if ! [ -f $fileJSONJar ]; then
echo "Required library file 'json-simple-1.1.1.jar' not found.";
exit;
fi

#javac -cp $fileJSONJar MatchProductWithList.java
java -cp $fileJSONJar:. MatchProductWithList

curl -X POST -F 'file=@results.txt' https://challenge-check.sortable.com/validate


make clean
make html
cp -r ./source/javadoc .
cp -r ./build/html/* .
git add .
git commit -a -m 'deploy commit all'
git push origin gh-pages


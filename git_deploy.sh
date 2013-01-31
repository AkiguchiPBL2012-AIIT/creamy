make clean
make html
cp -r ./build/html/* .
git add .
git commit -a -m 'commit all'
git push origin gh-pages


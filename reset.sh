#bin/bash
for name in $(find . -type d -name .idea);do
rm -r "$name"
done

for name in $(find . -type d -name .settings);do
rm -r "$name"
done

for name in $(find . -type d -name target);do
rm -r "$name"
done

for name in $(find . -type f -name '.project');do
rm -r "$name"
done

for name in $(find . -type f -name '.classpath');do
rm -r "$name"
done

for name in $(find . -type f -name '*.iml');do
rm -r "$name"
done

for name in $(find . -type f -name '*.log');do
rm -r "$name"
done

for name in $(find . -type f -name '*.log.*');do
rm -r "$name"
done


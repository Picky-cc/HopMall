### springboot + maven multiple modules

* clean all && install

```
	clean install -Dmaven.test.skip=true
```

* package war

```
	package -Dmaven.test.skip=true
```

* run jetty

```
	jetty:run-exploded -Dmaven.test.skip=true
```

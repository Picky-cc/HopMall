### springboot + maven multiple modules

*  all-clean-install

```
	${workspace_loc:/zufangbao-springboot-center}
	clean install -Dmaven.test.skip=true
```

* earth-jetty-run-war

```
	${workspace_loc:/earth}
	jetty:run-exploded -Dmaven.test.skip=true
```

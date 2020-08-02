# martian-robots

## To build and run

```
>git clone git@github.com:jteh/martian-robots.git
>sbt test stage
>cd target/universal/stage/bin
>./martian-robots #careful with inputs, invalid ones currently throw exceptions and app explodes
```

## TODO
* Input validations to ensure grid coordinates, orientation and instructions are valid values
* Input validations to ensure coords do not exceed 50 and instruction strings do not exceed 100


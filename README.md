# mastermind-clj

A Mastermind solver using `clojure.core.logic`

## Usage

```sh
$ lein repl
> (use 'mastermind.core)

> (solve (random-row))
[{:row [:black :red :white :red], :score [:black nil nil nil]} {:row [:black :green :blue :yellow], :score [:white nil nil nil]} {:row [:blue :white :white :blue], :score [:black :black :black :black]}]

> (solve [:red :green :blue :white])
[{:row [:black :black :green :red], :score [:white :white nil nil]} {:row [:blue :white :yellow :black], :score [:white :white nil nil]} {:row [:red :blue :white :green], :score [:black :white :white :white]} {:row [:red :green :blue :white], :score [:black :black :black :black]}]
```




### Bugs

Very often the `(solve)` crashes with the following output.

```sh
> (solve (random-row))
IndexOutOfBoundsException   clojure.lang.RT.nthFrom (RT.java:795)
```


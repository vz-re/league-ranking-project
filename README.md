# league-ranking

This is a command-line application that will calculate a ranking table for a league.

## Usage

Brief instructions on how to run the project

1. Save an input text file somewhere on your local filesystem. An example input is provided below:

```
Lions 3, Snakes 3
Tarantulas 1, FC Awesome 0
Lions 1, FC Awesome 1
Tarantulas 3, Snakes 1
Lions 4, Grouches 0
```

2. `cd` into the `league-ranking` project
3. Run `sbt run`
5. When prompted enter the file path where the input file is saved, i.e.: `./input.txt`
6. Next, you will be prompted to either save the output as a file or view it where you are running the project

   6.1. If you want to save it as a file, enter: `y`

   6.2. You will be prompted to provide a filename for your output file, enter i.e.: `output`
7. You can view the output at: `./output.txt`

### Rule Change

The league rules, which specify the points assigned for either win/lose/draw, can be changed by overwriting the environment
variables.
In step 3, change the command as follow:

- Run `WIN_POINTS=5 DRAW_POINTS=2 sbt run`


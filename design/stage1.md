# MVP  
A Wordle game with a minimal CLI interface.

## List Parsing  
A generic parser interface for reading a resource file into a `List<T>`. This
allows us to parse files containing strings, numbers, or any other data type.

Implement this for a class that reads simple resource files (data separated by
new lines).

We want an interface so we can later implement classes for user-defined files
and different formats.

## Comparator  
A generic comparator interface for comparing objects of class `T`.  
This will allow us to compare different types of data in the future.

### Result Enum  
An enum type for different comparison results.

For example, we take a string, split it into characters, compare each character
from the guess to the target, and then return a `List<Result>` that the UI can interpret.

In this example, we would have three possible results:
- `Correct`: right character in the right position  
- `Present`: right character in the wrong position  
- `Absent`: character not in the target word

## Game Class  
Holds the target word (picked randomly), the parsed word list, previous board
states, and the number of tries the player has left.

It needs to be generic so it can be extended later.

It should be able to:
1. Take the user's guess and verify that it's valid (i.e., it's in the word list)  
2. Compare the guess and the target (using the comparator), and return the result

There should also be a game interface so we can later implement classes capable
of running multiple games of different types.

## CLI Interface  
Implementation of colored output:
- Green for correct letters  
- Yellow for letters in the wrong position

# Extensions  
Not a priority, but part of the first development stage.

## Parser Extension  
Add a parser for the user's custom word list.

## CLI Extension  

### Menu  
1. Play  
2. Load word list

### Settings (Before Every Game)  
Allow the user to select which word list should be used for the game.  
Loaded word lists should be cached (remembered) to avoid repeated parsing.


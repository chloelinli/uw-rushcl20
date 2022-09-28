### Written Answers: 11/20

## `IntQueue1` Incorrect Abstraction Function
The abstraction function for `IntQueue1` ought to be:
```
AF(this) = [entries[0], entries[1], ..., entries[entries.size() - 1]]
```

## `IntQueue1` Incorrect Representation Invariant
The representation invariant for `IntQueue1` ought to be:
```
entries != null && !entries.contains(null)
```

## `IntQueue2` Incorrect Abstraction Function
The abstraction function for `IntQueue2` ought to be:
```
AF(this) = [..., entries[(i + front) % entries.length], ...]
           for front <= i < size
```

## `IntQueue2` Incorrect Representation Invariant
The representation invariant for `IntQueue2` ought to be:
```
entries != null && 0 <= front < entries.length && 0 <= size <= entries.length
```

# Rep exposure

## 3a: primitive ints
- integer primitives are immutable. This is why rep exposure is NOT possible

## 3b
- if we assume the String[] being returned is one of the class's fields, then 
rep exposure is possible since arrays are mutable.

## 3c: Private Method
- note that the method is private, so it cannot directly cause
representation exposure.

## 3d
- Strings are immutable. This is why rep exposure is NOT possible.

## 3f: Passing in a Mutable Object
- note that a list is mutable, and that if `cards` is stored in our
representation the client could modify `cards` after initialization.


### Design: 2/3

## Pathfinding
Your graph ADT should not implement any pathfinding. Clients should handle pathfinding themselves
if they want it.

- Consider whether your nodes really need to be maintained in a specific ordering.
What would be the case when you'd need to insert a node into a specific index. The graph 
itself should not maintain any sort of ordering.

- What happens in `insertNode` if the node already exists in the graph?

- What happens in `selfEdge` if the node is null?

- There are no getters, how will you access data in the `Node` objects?

### Documentation & Specification (including JavaDoc): 2/3

- Does your `addEdge` method also add the nodes if they don't exist? This should 
be made clear in the overall method comment, not just in the `spec.effects`

- All of your methods and constructors should have a general description comment in addition 
to the javadoc tags.

- Your javadoc tags are not super clear. Thing like `Graph.size()` and `Graph[index]` 
should probably just be explained in English, because this is a public specification, so 
if `Graph` is supposed to be a private field, the client should not be reading about it
in the javadoc.

### Testing (test suite quality & implementation): 1/3

- Where are your JUnit tests? You have behavior in your graph that's not supported 
by the script tests, this must be tested with JUnit tests.

- Your script tests are severly lacking in coverage. If you were unsure of where 
to start, try the 0,1,2 method.

### Code quality (code stubs/skeletons only, nothing else): 3/3

- Good job not including any private information.

### Mechanics: 3/3

#### Overall Feedback

Some issues with your design, and some major issues with your testing, but overall 
a good start.

#### More Details

None.

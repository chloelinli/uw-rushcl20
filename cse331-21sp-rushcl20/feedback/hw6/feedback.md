### Command line application: -/3
- missing main method and CLI
### Design: 1/3

### Documentation & Specification (including JavaDoc): 2/3

### Code quality (code and internal comments including RI/AF when appropriate): 2/3

### Testing (test suite quality & implementation): -/3

### Mechanics: 3/3

### Command line application extra credit:  -/3

#### Overall Feedback

Good progress on this assignment, but make sure to read my feedback below for some things to think about. The next few homework assignments build on each other, so it's important that they are each finished (this one a little less so).

#### More Details
- your parseData method will only work if each book only has one character in it (as map keys must be unique)
- your `buildGraph` returns a graph, but your doc describes it as returning a map. Also, what format must the csv be? This should be specified.
- you shouldn't need to sort your nodes before adding them to the graph.
- you should not have nodes in your graph be compound strings like it appears you are doing.
- you should have some internal comments for `findPath`
- `findPath` should not return a string. If there is a client that wants to use this method, they should not need to parse a string to extract meaningful data. Not all clients are going to want the same exact string format, so we can't rely on this.
- For next time, if you implement a class that isn't an ADT, you should add an internal comment that explicitly
says so where the ADT/RI would normally go.
- Your BFS method is complex, and should hence have some internal comments/documentation to improve its readability and understandability.
- no tests added
- findPath should take a Graph rather than a Map.
